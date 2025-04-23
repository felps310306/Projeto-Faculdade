package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class BDUtils {

    private static final String URL = "jdbc:mysql://localhost:3306/agencia_viagens"; // Ajuste conforme o nome do seu BD
    private static final String USER = "root"; // Seu usuário do MySQL
    private static final String PASSWORD = "felipedeus13"; // Sua senha do MySQL

    // Método para abrir a conexão com o banco de dados
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        return conn;
    }

    // Método para fechar a conexão com o banco
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    // Método para fechar o Statement
    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar o Statement: " + e.getMessage());
            }
        }
    }
}
