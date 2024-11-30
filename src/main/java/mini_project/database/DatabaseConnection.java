package mini_project.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/quizapp";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static void CreateDBAndTables(Connection connection) {
        try (Statement statement = connection.createStatement()) {

            String createDB = "CREATE DATABASE IF NOT EXISTS quizapp";
            statement.executeUpdate(createDB);

            String useDB = "USE quizapp";
            statement.executeUpdate(useDB);

            String UserTable = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "firstname VARCHAR(50), "
                    + "lastname VARCHAR(50), "
                    + "email VARCHAR(100) UNIQUE, "
                    + "password VARCHAR(255), "
                    + "role VARCHAR(50)"
                    + ")";
            statement.executeUpdate(UserTable);

            String QuizTable = "CREATE TABLE IF NOT EXISTS quizzes ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "title VARCHAR(100) NOT NULL, "
                    + "objective TEXT, "
                    + "starterCode TEXT , "
                    + "maxScore INT NULL, "
                    + "attempts INT NOT NULL DEFAULT 0, "
                    + "isSubmitted BOOLEAN NOT NULL DEFAULT FALSE, "
                    + "deadLine TEXT NULL"
                    + ")";
            statement.executeUpdate(QuizTable);

            String SubmissionTable = "CREATE TABLE IF NOT EXISTS submissions ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "userId INT, "
                    + "quizId INT, "
                    + "submissionCode VARCHAR(100), "
                    + "submissionDate DATE, "
                    + "score DOUBLE, "
                    + "status BOOLEAN, "
                    + "objective TEXT, "
                    + "FOREIGN KEY (userId) REFERENCES users(id), "
                    + "FOREIGN KEY (quizId) REFERENCES quizzes(id)"
                    + ")";
            statement.executeUpdate(SubmissionTable);

            String TestCaseTable = "CREATE TABLE IF NOT EXISTS test_cases ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "quizId INT, "
                    + "input VARCHAR(100), "
                    + "output VARCHAR(100), "
                    + "FOREIGN KEY (quizId) REFERENCES quizzes(id)"
                    + ")";
            statement.executeUpdate(TestCaseTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            CreateDBAndTables(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connection;
    }

    
}
