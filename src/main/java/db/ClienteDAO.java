package db;

import model.Cliente;
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

    // Método para inserir um novo cliente no banco de dados
    public void inserirCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, cpf, idade, telefone, endereco, tipo_cliente, passaporte) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setInt(3, cliente.getIdade());
            ps.setString(4, cliente.getTelefone());
            ps.setString(5, cliente.getEndereco());
            ps.setString(6, cliente.getTipoCliente());

            if (cliente.getTipoCliente().equals("estrangeiro")) {
                ps.setString(7, cliente.getPassaporte()); // Para estrangeiros, insere o passaporte
            } else {
                ps.setNull(7, Types.VARCHAR); // Para nacionais, o passaporte é nulo
            }

            ps.executeUpdate();
            System.out.println("Cliente inserido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

                // Verifica se o cliente é nacional ou estrangeiro para instanciar corretamente
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
