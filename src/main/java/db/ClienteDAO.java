package db;

import model.Cliente;
import model.PacoteViagem;
import model.PacotesEServicosCliente;
import model.ServicoAdicional;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private Connection conexao;

    public ClienteDAO() {
        try {
            conexao = ConexaoBD.conectar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deletarClientePorIdentificacao(String identificacao, String tipoIdentificacao) {
        String sql = "";

        if (tipoIdentificacao.equalsIgnoreCase("CPF")) {
            sql = "DELETE FROM clientes WHERE cpf = ?";
        } else if (tipoIdentificacao.equalsIgnoreCase("Passaporte")) {
            sql = "DELETE FROM clientes WHERE passaporte = ?";
        }

        try (Connection conn = ConexaoBD.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, identificacao);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void inserirCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, cpf, idade, telefone, endereco, tipo_cliente, passaporte) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setInt(3, cliente.getIdade());
            ps.setString(4, cliente.getTelefone());
            ps.setString(5, cliente.getEndereco());

            String tipoCliente = cliente.getTipoCliente();
            if (tipoCliente == null) {
                tipoCliente = "nacional";
            }
            ps.setString(6, tipoCliente);

            if (tipoCliente.equals("estrangeiro")) {
                ps.setString(7, cliente.getPassaporte());
            } else {
                ps.setNull(7, Types.VARCHAR);
            }

            ps.executeUpdate();
            System.out.println("Cliente inserido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void associarClientePacote(String identificacao, int pacoteId) {
        String sql = "SELECT id FROM clientes WHERE cpf = ? OR passaporte = ? LIMIT 1";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, identificacao);
            ps.setString(2, identificacao);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int clienteId = rs.getInt("id");

                String associarSQL = "INSERT INTO clientes_pacotes (cliente_id, pacote_id) VALUES (?, ?)";
                try (PreparedStatement psAssoc = conexao.prepareStatement(associarSQL)) {
                    psAssoc.setInt(1, clienteId);
                    psAssoc.setInt(2, pacoteId);
                    psAssoc.executeUpdate();
                    System.out.println("Cliente associado ao pacote com sucesso!");
                }
            } else {
                System.out.println("Cliente não encontrado com o CPF ou passaporte fornecido.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para associar um serviço adicional ao cliente
    public void associarServicoAoCliente(Cliente cliente, ServicoAdicional servico) {
        String sql = "SELECT id FROM clientes WHERE cpf = ? OR passaporte = ? LIMIT 1";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, cliente.getCpf());
            ps.setString(2, cliente.getCpf());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int clienteId = rs.getInt("id");

                // Associa o cliente ao serviço adicional
                String associarSQL = "INSERT INTO clientes_servicos (cliente_id, servico_id) VALUES (?, ?)";
                try (PreparedStatement psAssoc = conexao.prepareStatement(associarSQL)) {
                    psAssoc.setInt(1, clienteId);
                    psAssoc.setInt(2, servico.getId());
                    psAssoc.executeUpdate();
                    System.out.println("Serviço associado ao cliente com sucesso!");
                }
            } else {
                System.out.println("Cliente não encontrado com o CPF ou passaporte fornecido.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PacotesEServicosCliente listarPacotesClientePorCpf(String identificacao) {
        List<PacoteViagem> pacotes = new ArrayList<>();
        List<ServicoAdicional> servicos = new ArrayList<>();

        String sqlPacotes = "SELECT p.* FROM pacotes p "
                + "JOIN clientes_pacotes cp ON p.id = cp.pacote_id "
                + "JOIN clientes c ON c.id = cp.cliente_id "
                + "WHERE c.cpf = ? OR c.passaporte = ?";

        String sqlServicos = "SELECT s.* FROM servicos_adicionais s "
                + "JOIN clientes_servicos cs ON s.id = cs.servico_id "
                + "JOIN clientes c ON c.id = cs.cliente_id "
                + "WHERE c.cpf = ? OR c.passaporte = ?";

        try (PreparedStatement psPacotes = conexao.prepareStatement(sqlPacotes);
             PreparedStatement psServicos = conexao.prepareStatement(sqlServicos)) {

            psPacotes.setString(1, identificacao);
            psPacotes.setString(2, identificacao);
            ResultSet rsPacotes = psPacotes.executeQuery();

            while (rsPacotes.next()) {
                PacoteViagem pacote = new PacoteViagem(
                        rsPacotes.getString("nome"),
                        rsPacotes.getString("destino"),
                        rsPacotes.getInt("duracao"),
                        rsPacotes.getString("tipo"),
                        rsPacotes.getFloat("preco"),
                        rsPacotes.getString("detalhes")
                );
                pacote.setId(rsPacotes.getInt("id"));
                pacotes.add(pacote);
            }

            psServicos.setString(1, identificacao);
            psServicos.setString(2, identificacao);
            ResultSet rsServicos = psServicos.executeQuery();

            while (rsServicos.next()) {
                ServicoAdicional servico = new ServicoAdicional(
                        rsServicos.getString("nome"),
                        rsServicos.getString("descricao"),
                        rsServicos.getFloat("preco")
                );
                servico.setId(rsServicos.getInt("id"));
                servicos.add(servico);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new PacotesEServicosCliente(pacotes, servicos);
    }


    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                int idade = rs.getInt("idade");
                String telefone = rs.getString("telefone");
                String endereco = rs.getString("endereco");
                String tipoCliente = rs.getString("tipo_cliente");
                String passaporte = rs.getString("passaporte");

                Cliente cliente = new Cliente(nome, cpf, tipoCliente.equals("nacional") ? null : passaporte, idade, telefone, endereco, tipoCliente);
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public void deletarCliente(String cpf) {
        String sql = "DELETE FROM clientes WHERE cpf = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, cpf);
            ps.executeUpdate();
            System.out.println("Cliente excluído com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cliente buscarClientePorCpfOuPassaporte(String identificacao) {
        String sql = "SELECT * FROM cliente WHERE cpf = ? OR passaporte = ? LIMIT 1";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, identificacao);
            ps.setString(2, identificacao);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    int idade = rs.getInt("idade");
                    String telefone = rs.getString("telefone");
                    String endereco = rs.getString("endereco");
                    String tipoCliente = rs.getString("tipo_cliente");
                    String passaporte = rs.getString("passaporte");
                    int id = rs.getInt("id"); // Pegar o ID também!

                    Cliente cliente = new Cliente(nome, cpf, tipoCliente.equals("nacional") ? null : passaporte, idade, telefone, endereco, tipoCliente);
                    cliente.setId(id); // Definir o ID no objeto Cliente
                    return cliente;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por identificação: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Retorna null se não encontrar
    }

}
