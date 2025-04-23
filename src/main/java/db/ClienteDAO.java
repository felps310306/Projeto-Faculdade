package db;
package org.example.util;

import org.example.model.Cliente;
import java.sql.*;
import java.util.ArrayList;

public class ClienteDAO {

    public void salvar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (nome, cpf, idade, numero_telefone, endereco) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setInt(3, cliente.getIdade());
            stmt.setString(4, String.valueOf(cliente.getNumeroTelefone()));
            stmt.setString(5, cliente.getEndereco());
            stmt.executeUpdate();
        }
    }

    public ArrayList<Cliente> listar() throws SQLException {
        ArrayList<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection conn = Conexao.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getInt("idade"),
                        Integer.parseInt(rs.getString("numero_telefone")),
                        rs.getString("endereco")
                );
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    public boolean deletarPorCpf(String cpf) throws SQLException {
        String sql = "DELETE FROM clientes WHERE cpf = ?";
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            return stmt.executeUpdate() > 0;
        }
    }
}

