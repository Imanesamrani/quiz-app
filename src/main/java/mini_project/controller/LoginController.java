package mini_project.controller;

import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import java.util.regex.Pattern;

import mini_project.database.DatabaseConnection;
import mini_project.model.User;
import mini_project.view.LoginPage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;

public class LoginController {

    // Regex pattern for email validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public String validateInput(TextField emailField, PasswordField passwordField) {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        // Stricter email validation
        if (email.isEmpty() || !validateEmail(email)) {
            return "Invalid email address.";
        }
        if (password.isEmpty() || password.length() < 6) {
            return "Password must be at least 6 characters.";
        }
        return null; // No errors
    }

    private boolean validateEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.find();
    }

    public User loginUser(String email, String password) {
        User user = UserController.login(email, password);
        if (user != null) {
            System.out.println("Login successful!");
            return user;
        } else {
            System.out.println("User not found or invalid password.");
            return null;
        }
    }
 // Méthode pour authentifier un utilisateur et retourner ses informations
    public static User authenticateUser(String email, String password) {
        User user = null;
        String query = "SELECT id, lastname, firstname, role FROM users WHERE email = ? AND password = ?";
    
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
    
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
    
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String lastName = resultSet.getString("lastname");
                String firstName = resultSet.getString("firstname");
                String role = resultSet.getString("role");
    
                // Construire l'objet User
                user = new User(id, firstName, lastName, email, role);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return user;
}

 // Methodes Pour réccuper last name de l'utilisateur
public static String getLastName(int userId) {
    String lastName = null;
    String query = "SELECT lastname FROM users WHERE id = ?";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, userId); // userId est l'identifiant de l'utilisateur connecté
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            lastName = resultSet.getString("lastname");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lastName;
}
  
public static void logoutUser(StackPane main, Scene scene) {
    System.out.println("Utilisateur déconnecté !");
    
    // Rediriger vers l'écran de connexion
    try {
        LoginPage.LoginScreen(main, scene);
    } catch (Exception ex) {
        ex.printStackTrace();
        System.out.println("Erreur lors de la redirection vers l'écran de connexion.");
    }
}
    
}
