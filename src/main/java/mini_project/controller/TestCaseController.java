package mini_project.controller;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mini_project.database.DatabaseConnection;
import mini_project.model.TestCase;

public class TestCaseController {

    

  

      public static List<TestCase> getTestCasesByQuizId(int quizId) {
        String query = "SELECT * FROM test_cases WHERE quizId = ?";
        List<TestCase> testCases = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quizId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TestCase testCase = new TestCase(
                    resultSet.getInt("id"),
                    resultSet.getInt("quizId"),
                    resultSet.getString("input"),
                    resultSet.getString("output")
                );
                testCases.add(testCase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return testCases;
    }
    
 
    
    public static void updateTestCaseInDatabase(int id, String input, String output) throws SQLException {
        String query = "UPDATE test_cases SET input = ?, output = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, input);  // Définit l'entrée
            statement.setString(2, output); // Définit la sortie
            statement.setInt(3, id);        // Définit l'ID du test case
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected == 0) {
                System.out.println("Aucune ligne n'a été mise à jour pour l'ID " + id);
            } else {
                System.out.println("Test case avec ID " + id + " mis à jour avec succès.");
            }
        }
    }
    
    
    public static void deleteTestCaseFromDatabase(int id) throws SQLException {
        String query = "DELETE FROM test_cases WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
    
    // Méthode pour supprimer tous les test cases associés à un quiz
public static void deleteTestCasesByQuizId(int quizId) throws SQLException {
    String query = "DELETE FROM test_cases WHERE quizId = ?";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, quizId);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println(rowsAffected + " test cases supprimés.");
        } else {
            System.out.println("Aucun test case trouvé pour ce quiz.");
        }
    }
}

 // Méthode pour ajouter un test case à la base de données
 public static void addTestCaseToDatabase(int quizId, String input, String output) throws SQLException {
    String insertTestCaseQuery = "INSERT INTO test_cases (quizId, input, output) VALUES (?, ?, ?)";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(insertTestCaseQuery)) {
        preparedStatement.setInt(1, quizId);
        preparedStatement.setString(2, input);
        preparedStatement.setString(3, output);
        int rowsAffected = preparedStatement.executeUpdate();

        // Vérifier si l'insertion a réussi
        if (rowsAffected > 0) {
            System.out.println("Test case ajouté avec succès !");
        } else {
            System.out.println("Échec de l'ajout du test case.");
        }
    }
}
}
