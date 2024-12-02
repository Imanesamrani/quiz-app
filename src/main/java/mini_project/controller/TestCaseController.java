package mini_project.controller;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import mini_project.database.DatabaseConnection;
import mini_project.model.TestCase;

public class TestCaseController {

    // Create
    public static final void createTestCase(TestCase testCase) {
        String sql = "INSERT INTO test_cases (quizId, input, output) VALUES (?,?,?)";
        try (
            
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            
            statement.setInt(1, testCase.getQuizId());
            statement.setString(2, testCase.getInput());
            statement.setString(3, testCase.getOutput());

            // Execute the insert query
            statement.executeUpdate();
            System.out.println("Test case created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to create test case");
        }
    }

    
    public static final TestCase getTestCase(int quizId) {
        String sql = "SELECT * FROM test_cases WHERE quizId = ?";
        try (
            // Establish database connection
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, quizId);

            // Execute the select query
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Return the found test case
                return new TestCase(
                    resultSet.getInt("id"),
                    resultSet.getInt("quizId"),
                    resultSet.getString("input"),
                    resultSet.getString("output")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to retrieve test case");
        }
        return null; // Return null if not found
    }

    // Update
    public static final void updateTestCase(TestCase testCase) {
        String sql = "UPDATE test_cases SET input = ?, output = ? WHERE quizId = ?";
        try (
            // Establish database connection
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            // Set parameters for the SQL statement
            statement.setString(1, testCase.getInput());
            statement.setString(2, testCase.getOutput());
            statement.setInt(3, testCase.getQuizId());

            // Execute the update query
            statement.executeUpdate();
            System.out.println("Test case updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update test case");
        }
    }

    // Delete
    public static final void deleteTestCase(int quizId) {
        String sql = "DELETE FROM test_cases WHERE quizId = ?";
        try (
            // Establish database connection
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            // Set the quizId for deletion
            statement.setInt(1, quizId);

            // Execute the delete query
            statement.executeUpdate();
            System.out.println("Test case deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to delete test case");
        }
    }
}
