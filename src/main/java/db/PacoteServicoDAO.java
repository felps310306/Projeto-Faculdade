package db;

import model.PacoteViagem;
import model.ServicoAdicional;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacoteServicoDAO {
    private Connection conexao;

    public PacoteServicoDAO() {
        try {
            conexao = ConexaoBD.conectar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para associar um pacote a um serviço adicional
    public void associarPacoteServico(int pacoteId, int servicoId) {
        String sql = "INSERT INTO pacotes_servicos (pacote_id, servico_id) VALUES (?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, pacoteId);
            ps.setInt(2, servicoId);
            ps.executeUpdate();
            System.out.println("Pacote associado ao serviço adicional com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para listar os serviços adicionais de um pacote
    public List<ServicoAdicional> listarServicosPorPacote(int pacoteId) {
        List<ServicoAdicional> servicos = new ArrayList<>();
        String sql = "SELECT s.* FROM servicos_adicionais s " +
                "JOIN pacotes_servicos ps ON s.id = ps.servico_id " +
                "WHERE ps.pacote_id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, pacoteId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ServicoAdicional servico = new ServicoAdicional(
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getFloat("preco")
                );
                servicos.add(servico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servicos;
    }
}
