package db;

import model.PacoteViagem;
import model.Cliente;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacoteDAO {

    public void salvar(PacoteViagem pacote) {
        String sql = "INSERT INTO pacotes (nome, preco, duracao, tipo, detalhes, destino) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pacote.getNome());
            stmt.setFloat(2, pacote.getPreco());
            stmt.setInt(3, pacote.getDuracao());
            stmt.setString(4, pacote.getTipo());
            stmt.setString(5, pacote.getDetalhes());
            stmt.setString(6, pacote.getDestino());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PacoteViagem> listar() {
        List<PacoteViagem> pacotes = new ArrayList<>();
        String sql = "SELECT * FROM pacotes";

        try (Connection conn = ConexaoBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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

    public List<Cliente> listarClientesPacote(int pacoteId) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT c.id, c.nome, c.cpf, c.passaporte, c.idade, c.telefone, c.endereco, c.tipo_cliente, c.email " +
                "FROM clientes c " +
                "JOIN clientes_pacotes cp ON c.id = cp.cliente_id " +
                "WHERE cp.pacote_id = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pacoteId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("passaporte"),
                        rs.getInt("idade"),
                        rs.getString("telefone"),
                        rs.getString("endereco"),
                        rs.getString("tipo_cliente"),
                        rs.getString("email")
                );
                cliente.setId(rs.getInt("id"));
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    public PacoteViagem buscarPorId(int id) {
        String sql = "SELECT * FROM pacotes WHERE id = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PacoteViagem pacote = new PacoteViagem(
                            rs.getString("nome"),
                            rs.getString("destino"),
                            rs.getInt("duracao"),
                            rs.getString("tipo"),
                            rs.getFloat("preco"),
                            rs.getString("detalhes")
                    );
                    pacote.setId(rs.getInt("id"));
                    return pacote;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pacote por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
