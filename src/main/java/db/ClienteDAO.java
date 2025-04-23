package db;

import model.Cliente;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void salvar(Cliente cliente) {
        if (!cliente.isValido()) {
            System.out.println("Erro: Cliente inválido. CPF ou passaporte não informados corretamente.");
            return;
        }

        String sql = "INSERT INTO clientes (nome, cpf, passaporte, idade, numeroTelefone, endereco, tipo_cliente) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getPassaporte());
            stmt.setInt(4, cliente.getIdade());
            stmt.setString(5, cliente.getTelefone());
            stmt.setString(6, cliente.getEndereco());
            stmt.setString(7, cliente.getTipoCliente());

            stmt.executeUpdate();
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = ConexaoBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("passaporte"),
                        rs.getInt("idade"),
                        rs.getString("numeroTelefone"),
                        rs.getString("endereco"),
                        rs.getString("tipo_cliente")
                );
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public Cliente buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM clientes WHERE cpf = ?";
        Cliente cliente = null;

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente(
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("passaporte"),
                            rs.getInt("idade"),
                            rs.getString("numeroTelefone"),
                            rs.getString("endereco"),
                            rs.getString("tipo_cliente")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }

    public void atualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nome = ?, idade = ?, numeroTelefone = ?, endereco = ?, tipo_cliente = ? WHERE cpf = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setInt(2, cliente.getIdade());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getTipoCliente());
            stmt.setString(6, cliente.getCpf());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletar(String cpf) {
        String sql = "DELETE FROM clientes WHERE cpf = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
