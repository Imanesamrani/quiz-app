package mini_project.controller;

import java.sql.*;

import mini_project.database.DatabaseConnection;
import mini_project.model.Submission; // Assuming Submission is your model class

public class SubmissionController {

    // Create a new submission
    public static final void createSubmission(Submission submission) {
        String sql = "INSERT INTO submissions (userId, quizId, submissionCode, submissionDate, score, status) VALUES (?,?,?,?,?,?)";
        try (
            // Establish database connection
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            // Set parameters for the SQL statement
            statement.setInt(1, submission.getUserId());
            statement.setInt(2, submission.getQuizId());
            statement.setString(3, submission.getSubmissionCode());
            statement.setDate(4, null);
            statement.setInt(5, submission.getScore());
            statement.setBoolean(6, submission.getStatus());

            // Execute the insert query
            statement.executeUpdate();
            System.out.println("Submission created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to create submission");
        }
    }

    // Read a submission by userId and quizId
    public static final Submission getSubmission(int userId, int quizId) {
        String sql = "SELECT * FROM submissions WHERE userId = ? AND quizId = ?";
        try (
            // Establish database connection
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, userId);
            statement.setInt(2, quizId);

            // Execute the select query
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Return the found submission
                return new Submission(
                    resultSet.getInt("id"),
                    resultSet.getInt("userId"),
                    resultSet.getInt("quizId"),
                    resultSet.getString("submissionCode"),
                    resultSet.getDate("submissionDate"),
                    resultSet.getInt("score"),
                    resultSet.getBoolean("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to retrieve submission");
        }
        return null; // Return null if not found
    }

    // Update a submission
    public static final void updateSubmission(Submission submission) {
        String sql = "UPDATE submissions SET submissionCode = ?, submissionDate = ?, score = ?, status = ? WHERE userId = ? AND quizId = ?";
        try (
            // Establish database connection
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            // Set parameters for the SQL statement
            statement.setString(1, submission.getSubmissionCode());
            statement.setDate(2, new java.sql.Date(submission.getSubmissionDate().getTime()));
            statement.setInt(3, submission.getScore());
            statement.setBoolean(4, submission.getStatus());
            statement.setInt(5, submission.getUserId());
            statement.setInt(6, submission.getQuizId());

            // Execute the update query
            statement.executeUpdate();
            System.out.println("Submission updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update submission");
        }
    }

    // Delete a submission by userId and quizId
    public static final void deleteSubmission(int userId, int quizId) {
        String sql = "DELETE FROM submissions WHERE userId = ? AND quizId = ?";
        try (
            // Establish database connection
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            // Set parameters for the SQL statement
            statement.setInt(1, userId);
            statement.setInt(2, quizId);

            // Execute the delete query
            statement.executeUpdate();
            System.out.println("Submission deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to delete submission");
        }
    }
}
