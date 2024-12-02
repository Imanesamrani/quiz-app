package mini_project.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import mini_project.database.DatabaseConnection;
import mini_project.model.Quiz;
import mini_project.model.TestCase;


public class QuizController  {
    
  // Méthode pour récupérer l'ID d'un quiz à partir de son titre
    public static int getQuizIdByTitle(String quizTitle) {
        String query = "SELECT id FROM quizzes WHERE title = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, quizTitle);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // Retourne -1 si le quiz n'est pas trouvé
    }
    
    public static Quiz getQuizDetailsById(int quizId) {
    String query = "SELECT * FROM quizzes WHERE id = ?";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, quizId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            // Récupérer les test cases liés au quiz
            List<TestCase> testCases = TestCaseController.getTestCasesByQuizId(quizId);

            // Récupérer la valeur de la deadline (date et heure)
            String deadline = resultSet.getString("deadLine");

            // Séparer la date et l'heure
            String[] deadlineParts = deadline.split(",");
            String datePart = deadlineParts[0];  // Partie date (YYYY-MM-DD)
            String timePart = deadlineParts.length > 1 ? deadlineParts[1] : "";  // Partie heure (HH:mm)

            // Créer un objet LocalDate à partir de la date et affecter à datePicker
            LocalDate date = LocalDate.parse(datePart);

            // Créer l'objet Quiz avec les données récupérées
            return new Quiz(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getString("objective"),
                testCases, // Inclure la liste des test cases
                resultSet.getInt("maxScore"),
                date, // Affecter la date au DatePicker
                timePart, // Affecter l'heure au deadlineField
                resultSet.getString("starterCode")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    
    // Méthode pour supprimer un quiz
public static void deleteQuizFromDatabase(int quizId) throws SQLException {
    String query = "DELETE FROM quizzes WHERE id = ?";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, quizId);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Quiz avec ID " + quizId + " supprimé.");
        } else {
            System.out.println("Aucun quiz trouvé avec cet ID.");
        }
    }
}


    public static List<String> getQuizList() {
        List<String> quizList = new ArrayList<>();
        String query = "SELECT title FROM quizzes"; // Get all quiz titles
    
        try (Connection connection = DatabaseConnection.getConnection(); 
             Statement statement = connection.createStatement(); 
             ResultSet resultSet = statement.executeQuery(query)) {
    
            while (resultSet.next()) {
                String quizTitle = resultSet.getString("title"); // Get the quiz title
                quizList.add(quizTitle);  // Add the quiz title to the list
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return quizList;
    }

    public static List<String> getStudentList() {
        List<String> studentList = new ArrayList<>();
        String query = "SELECT firstname, lastname FROM users WHERE role = 'student'"; // Filtrer par le rôle étudiant

        try (Connection connection = DatabaseConnection.getConnection(); 
             Statement statement = connection.createStatement(); 
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String studentName = resultSet.getString("firstname") + " " + resultSet.getString("lastname");
                studentList.add(studentName);  // Ajouter le nom complet à la liste
               
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;
    }

    public static void checkDeadline(DatePicker datePicker, TextField timeField, ImageView warningIcon) {
        try {
            // Si la date ou l'heure sont vides, ne rien faire
            if (datePicker.getValue() == null || timeField.getText().isEmpty()) {
                warningIcon.setVisible(false);
                return;
            }

            // Obtenir la date et l'heure sélectionnées
            LocalDate selectedDate = datePicker.getValue();
            String timeInput = timeField.getText();

            // Convertir l'heure en LocalTime (format "HH:mm")
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime selectedTime = LocalTime.parse(timeInput, formatter);

            // Combiner la date et l'heure en LocalDateTime
            LocalDateTime selectedDeadline = LocalDateTime.of(selectedDate, selectedTime);

            // Comparer avec la date et l'heure actuelles
            LocalDateTime currentDateTime = LocalDateTime.now();
            if (selectedDeadline.isBefore(currentDateTime)) {
                // Si la date et l'heure du deadline sont dans le passé, afficher l'alerte (icône rouge)
                warningIcon.setVisible(true);
            } else {
                // Si le deadline est valide, réinitialiser l'apparence
                warningIcon.setVisible(false);
            }
        } catch (DateTimeParseException e) {
            // Si le format de l'heure est incorrect, afficher un message d'erreur
            warningIcon.setVisible(true);
        }
    }
    public static  int addQuizToDatabase(String title, String objective, int maxScore, DatePicker datePicker, TextField timeField, String starterCode) throws SQLException {
        // Récupérer la date et l'heure
        String date = datePicker.getValue() != null ? datePicker.getValue().toString() : ""; // Récupérer la date sélectionnée (format yyyy-mm-dd)
        String time = timeField.getText().trim(); // Récupérer l'heure entrée (format HH:mm)
    
        // Vérifier si l'heure est bien formatée (facultatif, selon votre besoin)
        if (!time.matches("\\d{2}:\\d{2}")) {
            throw new IllegalArgumentException("L'heure doit être au format HH:mm.");
        }
    
        // Concaténer la date et l'heure avec une virgule
        String deadline = date + "," + time;
    
        String insertQuizQuery = "INSERT INTO quizzes (title, objective, maxScore, deadLine, starterCode) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuizQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, objective);
            preparedStatement.setInt(3, maxScore);
            preparedStatement.setString(4, deadline);  // Passer la chaîne concaténée date,heure
            preparedStatement.setString(5, starterCode);
            
            int rowsAffected = preparedStatement.executeUpdate();
    
            // Vérifier si l'insertion a réussi
            if (rowsAffected > 0) {
                // Récupérer le quizId généré
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int quizId = generatedKeys.getInt(1);  // Retourner le quizId
                        System.out.println("Insertion réussie ! Quiz ID : " + quizId);
    
                        // Réinitialiser les champs après insertion
                        title = "";
                        objective = "";
                        maxScore = 0;
                        deadline = "";
                        starterCode = "";
    
                        return quizId;
                    } else {
                        throw new SQLException("Failed to obtain quizId.");
                    }
                }
            } else {
                throw new SQLException("Failed to insert quiz.");
            }
        }
    }
    public static void updateQuizInDatabase(int quizId, String title, String objective, int maxScore, DatePicker datePicker, TextField deadlineField, String starterCode) throws SQLException {
        // Récupérer la date du DatePicker et l'heure du TextField
        LocalDate selectedDate = datePicker.getValue();
        String selectedTime = deadlineField.getText(); // L'heure dans le format 24h (ex : "14:30")
    
        // Vérifier si la date et l'heure sont valides
        if (selectedDate != null && !selectedTime.isEmpty()) {
            // Combiner la date et l'heure dans le format "yyyy-MM-dd HH:mm"
            String deadline = selectedDate.toString() + "," + selectedTime;
    
            // Requête SQL pour mettre à jour le quiz avec la nouvelle deadline
            String updateQuizQuery = "UPDATE quizzes SET title = ?, objective = ?, maxScore = ?, deadLine = ?, starterCode = ? WHERE id = ?";
    
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(updateQuizQuery)) {
    
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, objective);
                preparedStatement.setInt(3, maxScore);
                preparedStatement.setString(4, deadline); // Mettre à jour avec la nouvelle deadline
                preparedStatement.setString(5, starterCode);
                preparedStatement.setInt(6, quizId);
    
                int rowsAffected = preparedStatement.executeUpdate();
    
                if (rowsAffected > 0) {
System.out.println("Quiz mis à jour avec succès !");
        } else {
 System.out.println("Échec de la mise à jour du quiz.");
            }
        }
    } else {
System.out.println("Erreur : Date ou heure invalide.");
}
} 

     // Fetch quizzes based on category from database
    public static List<Quiz> fetchQuizzesFromDatabase(String category) {
        List<Quiz> quizzes = new ArrayList<>();
        String query = "SELECT * FROM quizzes";

        // Modify query based on the category
        switch (category) {
            case "COMPLETED":
                query += " WHERE isSubmitted = TRUE";
                break;
            case "MISSED":
                query += " WHERE deadline < CURDATE() AND isSubmitted = FALSE";
                break;
            case "PENDING":
                query += " WHERE deadline >= CURDATE() AND isSubmitted = FALSE";
                break;
            default:
                // Default to fetching all quizzes
                break;
        }

        try (Connection connection = mini_project.database.DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String objective = resultSet.getString("objective");
                String starterCode = resultSet.getString("starterCode");
                int maxScore = resultSet.getInt("maxScore");
                int attempts = resultSet.getInt("attempts");
                boolean isSubmitted = resultSet.getBoolean("isSubmitted");
                String deadLine = resultSet.getString("deadLine");

                quizzes.add(new Quiz(id, title, objective, starterCode, new ArrayList<>(), maxScore, attempts, isSubmitted, deadLine));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return quizzes;
    }


  
    
public static int getTotalStudents() {
    int totalStudents = 0;
    String query = "SELECT COUNT(*) AS total_students FROM users WHERE role = 'student'";
    
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery()) {
         
        if (resultSet.next()) {
            totalStudents = resultSet.getInt("total_students");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return totalStudents;
}

public static int getTotalQuizzes() {
    int totalQuizzes = 0;
    String query = "SELECT COUNT(*) AS total_quizzes FROM quizzes";
    
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery()) {
         
        if (resultSet.next()) {
            totalQuizzes = resultSet.getInt("total_quizzes");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return totalQuizzes;
}

public static double getAverageScore() {
    double averageScore = 0.0;
    String query = "SELECT AVG(score) AS average_score FROM submissions WHERE score IS NOT NULL";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery()) {

        if (resultSet.next()) {
            averageScore = resultSet.getDouble("average_score");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return averageScore;
}

public static Map<String, Integer> getQuizzesTaken() {
    Map<String, Integer> quizzesTakenMap = new HashMap<>();
    String query = "SELECT u.firstname, u.lastname, COUNT(s.id) AS quizzesTaken " +
                   "FROM users u " +
                   "LEFT JOIN submissions s ON u.id = s.userId " +
                   "WHERE u.role = 'student' " +
                   "GROUP BY u.id";

    try (Connection connection = DatabaseConnection.getConnection(); 
         Statement statement = connection.createStatement(); 
         ResultSet resultSet = statement.executeQuery(query)) {

        while (resultSet.next()) {
            String name = resultSet.getString("firstname") + " " + resultSet.getString("lastname");
            int quizzesTaken = resultSet.getInt("quizzesTaken");
            quizzesTakenMap.put(name, quizzesTaken);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return quizzesTakenMap;
}

public static Map<String, Double> getAverageScoreByStudent() {
    Map<String, Double> averageScoreMap = new HashMap<>();
    String query = "SELECT u.firstname, u.lastname, AVG(s.score) AS averageScore " +
                   "FROM users u " +
                   "LEFT JOIN submissions s ON u.id = s.userId " +
                   "WHERE u.role = 'student' AND s.score IS NOT NULL " +
                   "GROUP BY u.id";

    try (Connection connection = DatabaseConnection.getConnection(); 
         Statement statement = connection.createStatement(); 
         ResultSet resultSet = statement.executeQuery(query)) {

        while (resultSet.next()) {
            String name = resultSet.getString("firstname") + " " + resultSet.getString("lastname");
            double averageScore = resultSet.getDouble("averageScore");
            averageScoreMap.put(name, averageScore);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return averageScoreMap;
}


}
