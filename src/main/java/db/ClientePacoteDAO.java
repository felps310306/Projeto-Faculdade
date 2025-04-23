package db;

import model.Cliente;
import model.PacoteViagem;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientePacoteDAO {
    private Connection conexao;

    public ClientePacoteDAO() {
        try {
            conexao = ConexaoBD.conectar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void associarClientePacote(int clienteId, int pacoteId) {
        String sql = "INSERT INTO clientes_pacotes (cliente_id, pacote_id) VALUES (?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, clienteId);
            ps.setInt(2, pacoteId);
            ps.executeUpdate();
            System.out.println("Cliente associado ao pacote com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PacoteViagem> listarPacotesPorCliente(int clienteId) {
        List<PacoteViagem> pacotes = new ArrayList<>();
        String sql = "SELECT p.* FROM pacotes p " +
                "JOIN clientes_pacotes cp ON p.id = cp.pacote_id " +
                "WHERE cp.cliente_id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, clienteId);
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
                pacotes.add(pacote);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacotes;
    }
}
