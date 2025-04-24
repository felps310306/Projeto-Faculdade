package db;

import model.Cliente;
import model.PacoteViagem;
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
        // Identificação pode ser CPF ou Passaporte
        String sql = "SELECT id FROM clientes WHERE cpf = ? OR passaporte = ? LIMIT 1";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            // Verificando se o identificador é um CPF ou passaporte
            ps.setString(1, identificacao);
            ps.setString(2, identificacao);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int clienteId = rs.getInt("id");

                // Agora associamos o cliente ao pacote
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

    // Método para listar pacotes de um cliente usando CPF
    public List<PacoteViagem> listarPacotesClientePorCpf(String cpf) {
        List<PacoteViagem> pacotes = new ArrayList<>();
        String sql = "SELECT p.* FROM pacotes p "
                + "JOIN clientes_pacotes cp ON p.id = cp.pacote_id "
                + "JOIN clientes c ON c.id = cp.cliente_id "
                + "WHERE c.cpf = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PacoteViagem pacote = new PacoteViagem(
                        rs.getString("nome"),
                        rs.getString("destino"),
                        rs.getInt("duracao"),
                        rs.getString("tipo"),
                        rs.getFloat("preco"),
                        rs.getString("detalhes")
                );
                pacote.setId(rs.getInt("id"));
                pacotes.add(pacote);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pacotes;
    }

    // Método para listar todos os clientes
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

    // Método para excluir um cliente pelo CPF
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
}
