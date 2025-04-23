package db;

import model.PacoteViagem;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacoteDao {

    public void salvar(PacoteViagem pacote) {
        String sql = "INSERT INTO pacotes (nome, preco, duracao, tipo, detalhes, destino) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, pacote.getNome());
                stmt.setFloat(2, pacote.getPreco());
                stmt.setInt(3, pacote.getDuracao());
                stmt.setString(4, pacote.getTipo());
                stmt.setString(5, pacote.getDetalhes()); // aqui o nome certo
                stmt.setString(6, pacote.getDestino());

                stmt.executeUpdate();
            }
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
                        rs.getString("detalhes") // aqui também
                );
                pacote.setId(rs.getInt("id")); // caso queira usar depois
                pacotes.add(pacote);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pacotes;
    }
}
