package mini_project.controller;

import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import mini_project.model.User;


public class RegisterController {

    // Regex pattern for email validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public String validateInput(TextField firstNameField, TextField lastNameField, TextField emailField, PasswordField passwordField, ToggleGroup roleGroup) {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        RadioButton selectedRole = (RadioButton) roleGroup.getSelectedToggle();

        if (firstName.isEmpty()) {
            return "First name cannot be empty.";
        }
        if (lastName.isEmpty()) {
            return "Last name cannot be empty.";
        }
        if (email.isEmpty() || !validateEmail(email)) {
            return "Invalid email address.";
        }
        if (password.isEmpty() || password.length() < 6) {
            return "Password must be at least 6 characters.";
        }
        if (selectedRole == null) {
            return "Please select a role.";
        }
        return null; // No errors
    }

    private boolean validateEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.find();
    }

    public String registerUser(TextField firstNameField, TextField lastNameField, TextField emailField, PasswordField passwordField, ToggleGroup roleGroup) {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        RadioButton selectedRole = (RadioButton) roleGroup.getSelectedToggle();
        String role = selectedRole.getText();

        // Create user object
        User user = new User(firstName, lastName, email, password, role);

        // Call createUser method and handle the result
        int userId = UserController.CreateUser(user);

        if (userId != -1) {
            System.out.println("Registration successful!");
            // Redirect to the appropriate interface based on the role
            if ("User".equals(role)) {
                System.out.println("Redirecting to student interface...");
                // Load the student interface
                return "Student Interface"; // Placeholder for actual interface loading code
            } else if ("Admin".equals(role)) {
                System.out.println("Redirecting to professor interface...");
                // Load the professor interface
                return "Professor Interface"; // Placeholder for actual interface loading code
            }
        } else {
            return "Failed to create user.";
        }
        return null;
    }
}
