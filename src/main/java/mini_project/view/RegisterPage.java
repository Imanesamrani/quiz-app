package mini_project.view;

import mini_project.controller.RegisterController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import mini_project.view.StudentInterface.QuizAppInterface;

public class RegisterPage {

    public static void RegisterScreen(StackPane main, Scene scene) {
        // Title label
        CustomLabel titleLabel = new CustomLabel("Register to CodeQuest", "title");

        // First Name
        CustomLabel firstNameLabel = new CustomLabel("First Name", "input-label");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        firstNameField.getStyleClass().add("input-field");
        firstNameField.setMinWidth(400);

        HBox firstNameBox = new HBox(10, firstNameLabel, firstNameField);
        firstNameBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(firstNameLabel, new Insets(30, 10, 0, 50));
        HBox.setMargin(firstNameField, new Insets(30, 0, 0, 0));

        // Last Name
        CustomLabel lastNameLabel = new CustomLabel("Last Name", "input-label");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        lastNameField.getStyleClass().add("input-field");
        lastNameField.setMinWidth(400);

        HBox lastNameBox = new HBox(10, lastNameLabel, lastNameField);
        lastNameBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(lastNameLabel, new Insets(0, 10, 0, 50));

        // Email
        CustomLabel emailLabel = new CustomLabel("Email", "input-label");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.getStyleClass().add("input-field");
        emailField.setMinWidth(400);
        
        HBox emailBox = new HBox(10, emailLabel, emailField);
        emailBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(emailLabel, new Insets(0, 10, 0, 50));
        HBox.setMargin(emailField, new Insets(0, 0, 0, 0));

        // Password
        CustomLabel passwordLabel = new CustomLabel("Password", "input-label");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.getStyleClass().add("input-field");
        passwordField.setMinWidth(400);
        
        HBox passwordBox = new HBox(10, passwordLabel, passwordField);
        passwordBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(passwordLabel, new Insets(0, 10, 0, 50));
        HBox.setMargin(passwordField, new Insets(0, 0, 0, 0));

        // Role Selection
        CustomLabel roleLabel = new CustomLabel("Role", "input-label");
        ToggleGroup roleGroup = new ToggleGroup();
        RadioButton userRadio = new RadioButton("Student");
        RadioButton adminRadio = new RadioButton("Professor");
        userRadio.setToggleGroup(roleGroup);
        adminRadio.setToggleGroup(roleGroup);
        userRadio.getStyleClass().add("radio-button");
        adminRadio.getStyleClass().add("radio-button");
        
        HBox roleBox = new HBox(20, userRadio, adminRadio);
        roleBox.setAlignment(Pos.CENTER);

        HBox roleLabelBox = new HBox(10, roleLabel, roleBox);
        roleLabelBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(roleLabel, new Insets(0, 10, 0, 50));

        // Error Label
        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);

        // Register Button
        Button registerButton = new Button("Register");
        registerButton.getStyleClass().add("bubbly-button");
        registerButton.setTranslateY(20);

        // Login Label
        CustomLabel loginLabel = new CustomLabel("Already have an account? ", "register-label");
        Label loginLink = new Label("Login");
        loginLink.getStyleClass().add("register-link");
        
        HBox loginBox = new HBox(loginLabel, loginLink);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setTranslateY(20);
        
        VBox registerBox = new VBox(20);
        registerBox.setAlignment(Pos.CENTER);
        registerBox.getChildren().addAll(titleLabel,errorLabel, firstNameBox, lastNameBox, emailBox, passwordBox, roleLabelBox, registerButton, loginBox);

        // Container with white background and border
        StackPane container = new StackPane(registerBox);
        container.setPadding(new Insets(50));
        container.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(20), Insets.EMPTY))); // Set CornerRadii to 20
        container.setBorder(new Border(new BorderStroke(Color.web("#D9D9D9"), BorderStrokeStyle.SOLID, new CornerRadii(20), BorderWidths.DEFAULT))); // Set CornerRadii to 20
        container.setMaxWidth(800);
        container.setMaxHeight(600);


        loginLink.setOnMouseClicked(e -> {
          try {
              LoginPage.LoginScreen(main, scene);
          } catch (Exception ex) {
              ex.printStackTrace();
          }
      });

        // Set action for Register button
        RegisterController controller = new RegisterController();
        registerButton.setOnAction(e -> {
            String validationMessage = controller.validateInput(firstNameField, lastNameField, emailField, passwordField, roleGroup);
            if (validationMessage == null) {
                errorLabel.setVisible(false);
                System.out.println("Validation successful!");

                String registerResult = controller.registerUser(firstNameField, lastNameField, emailField, passwordField, roleGroup);
                if ("Student Interface".equals(registerResult)) {
                    System.out.println("Redirecting to student interface...");
                } else if ("Professor Interface".equals(registerResult)) {
                    System.out.println("Redirecting to professor interface...");
                    // Load professor interface here
                } else {
                    errorLabel.setText(registerResult);
                    errorLabel.setVisible(true);
                }
            } else {
                errorLabel.setText(validationMessage);
                errorLabel.setVisible(true);
            }
        });

        // Add the group to the main StackPane
        main.getChildren().setAll(container);

        // Apply CSS stylesheet
        scene.getStylesheets().add(RegisterPage.class.getResource("/mini_project/styles/AuthPages.css").toExternalForm());
    }

    // CustomLabel class
    public static class CustomLabel extends Label {
        public CustomLabel(String text, String styleClass) {
            super(text);
            this.getStyleClass().add(styleClass);
        }
    }
}