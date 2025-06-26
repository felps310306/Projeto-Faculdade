package db;

import model.ServicoAdicional;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoAdicionalDAO {

    // Salva um novo serviço adicional no banco
    public void salvar(ServicoAdicional servico) {
        String sql = "INSERT INTO servicos_adicionais (nome, descricao, preco) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, servico.getNome());
            stmt.setString(2, servico.getDescricao());
            stmt.setFloat(3, servico.getPreco());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retorna todos os serviços cadastrados
    public List<ServicoAdicional> listarTodos() {
        List<ServicoAdicional> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicos_adicionais";

        try (Connection conn = ConexaoBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ServicoAdicional servico = new ServicoAdicional();
                servico.setId(rs.getInt("id"));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setPreco(rs.getFloat("preco"));

                lista.add(servico);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Busca um serviço pelo ID
    public ServicoAdicional buscarPorId(int id) {
        String sql = "SELECT * FROM servicos_adicionais WHERE id = ?";
        ServicoAdicional servico = null;

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                servico = new ServicoAdicional();
                servico.setId(rs.getInt("id"));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setPreco(rs.getFloat("preco"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servico;
    }

    // Deleta um serviço pelo ID
    public void deletarPorId(int id) {
        String sql = "DELETE FROM servicos_adicionais WHERE id = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Relaciona um serviço a um pacote
    public void associarServicoAoPacote(int pacoteId, int servicoId) {
        String sql = "INSERT INTO pacotes_servicos (pacote_id, servico_id) VALUES (?, ?)";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pacoteId);
            stmt.setInt(2, servicoId);
            stmt.executeUpdate();

            System.out.println("Serviço associado ao pacote com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lista os serviços associados a um pacote específico
    public List<ServicoAdicional> listarServicosPorPacote(int pacoteId) {
        List<ServicoAdicional> lista = new ArrayList<>();
        String sql = "SELECT sa.* FROM servicos_adicionais sa " +
                "JOIN pacotes_servicos ps ON sa.id = ps.servico_id " +
                "WHERE ps.pacote_id = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pacoteId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoAdicional servico = new ServicoAdicional();
                servico.setId(rs.getInt("id"));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setPreco(rs.getFloat("preco"));
                lista.add(servico);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
