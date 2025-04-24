package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    private static final String URL = "jdbc:mysql://localhost:3306/agencia_viagens";
    private static final String USUARIO = "root";
    private static final String SENHA = "felipedeus13";

    // Método para estabelecer a conexão com o banco
    public static Connection conectar() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            System.out.println("❌ Erro ao tentar se conectar ao banco: " + e.getMessage());
            throw e; // Relançando a exceção para ser tratada em outros lugares se necessário
        }
    }

    // Método para testar a conexão
    public static boolean testarConexao() {
        try (Connection conn = conectar()) {
            System.out.println("✅ Conexão com o banco estabelecida com sucesso!");
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Erro ao conectar ao banco: " + e.getMessage());
            return false;
        }
    }
}
