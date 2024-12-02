package mini_project.view;

import java.sql.SQLException;

import mini_project.controller.LoginController;
import mini_project.controller.QuizController;
import mini_project.controller.UserController;
import mini_project.database.DatabaseConnection;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mini_project.model.User;
import mini_project.view.Professor.QuizView;
import mini_project.view.StudentInterface.QuizAppInterface;
import javafx.scene.Node;       


public class LoginPage {

    public static final void LoginScreen(StackPane main, Scene scene) {


        // Create login form elements
        CustomLabel titleLabel = new CustomLabel("Login to CodeQuest", "title");
        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false); // Initially hidden

        CustomLabel emailLabel = new CustomLabel("Email", "input-label");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.getStyleClass().add("input-field");
        emailField.setMinWidth(400);

        CustomLabel passwordLabel = new CustomLabel("Password", "input-label");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.getStyleClass().add("input-field");
        passwordField.setMinWidth(400);

        HBox emailBox = new HBox(10, emailLabel, emailField);
        emailBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(emailLabel, new Insets(50, 10, 0, 50));
        HBox.setMargin(emailField, new Insets(50, 0, 0, 0));

        HBox passwordBox = new HBox(10, passwordLabel, passwordField);
        passwordBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(passwordLabel, new Insets(0, 10, 0, 50));

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("bubbly-button");
        loginButton.setTranslateY(20);

        CustomLabel registerLabel = new CustomLabel("You don't have an account? ", "register-label");
        Label registerLink = new Label("Register");
        registerLink.getStyleClass().add("register-link");

        HBox registerBox = new HBox(registerLabel, registerLink);
        registerBox.setAlignment(Pos.CENTER);
        registerBox.setTranslateY(20);

        VBox loginBox = new VBox(20);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.getChildren().addAll(titleLabel, errorLabel, emailBox, passwordBox, loginButton, registerBox);

        // Container with white background and border
        StackPane container = new StackPane(loginBox);
        container.setPadding(new Insets(50));
        container.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(20), Insets.EMPTY))); // Set CornerRadii to 20
        container.setBorder(new Border(new BorderStroke(Color.web("#D9D9D9"), BorderStrokeStyle.SOLID, new CornerRadii(20), BorderWidths.DEFAULT))); // Set CornerRadii to 20
        container.setMaxWidth(800);
        container.setMaxHeight(450);

        // Create a color overlay
        StackPane overlayPane = new StackPane();
        overlayPane.getChildren().add(container);

        // Create root pane
        StackPane root = new StackPane();
        root.getChildren().add(overlayPane);

        // Apply CSS stylesheet
        scene.getStylesheets().add(LoginPage.class.getResource("/mini_project/styles/AuthPages.css").toExternalForm());

        // Add scene to the main pane
        main.getChildren().setAll(root);

        // Handle login button click
        loginButton.setOnAction(e -> {
            LoginController controller = new LoginController();
            String validationMessage = controller.validateInput(emailField, passwordField);

            if (validationMessage == null) {
                errorLabel.setVisible(false);
                //successLabel.setVisible(false);
                System.out.println("Validation successful!");

                // Call login method and handle the result
                User loggedInUser = controller.loginUser(emailField.getText(), passwordField.getText());

                if (loggedInUser != null) {
                    String role = loggedInUser.getRole();
                    //successLabel.setText("Login successful!");
                    //successLabel.setVisible(true);
                    if ("Student".equals(role)) {
                        System.out.println("Redirecting to student interface...");
                        QuizAppInterface.StudentInterface(loggedInUser,main,scene);
                    } else if ("Professor".equals(role)) {
                        System.out.println("Redirecting to professor interface...");
               
     // Si vous voulez ouvrir une nouvelle fenêtre (stage)
     QuizView quizController = new QuizView();
     quizController.setLoggedInUser(loggedInUser); 
     emailField.setText("");
     passwordField.setText("");
     
     try {
        quizController.showQuizScreen(main, scene); // Lancer dans une nouvelle fenêtre
    } catch (Exception ex) {
        ex.printStackTrace();
    }
   
                    }
                } else {
                    errorLabel.setText("Login failed. Invalid email or password.");
                    errorLabel.setVisible(true);
                }

                // Establish the database connection
                try {
                    DatabaseConnection.getConnection();
                    System.out.println("Database connected and initialized successfully.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println("Database connection failed.");
                }
            } else {
                //successLabel.setVisible(false);
                errorLabel.setText(validationMessage);
                errorLabel.setVisible(true);
            }
        });


        // Handle register link click
        registerLink.setOnMouseClicked(e -> {
            try {
                RegisterPage.RegisterScreen(main, scene);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
     

   
    }

    

    // CustomLabel class
    public static class CustomLabel extends Label {
        public CustomLabel(String text, String styleClass) {
            super(text);
            this.getStyleClass().add(styleClass);
        }
    }
}