package mini_project.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class EntryPage {

    public static void EntryScreen(StackPane main, Scene scene) {
        // Logo setup
        Image logoImage = new Image(EntryPage.class.getResourceAsStream("/mini_project/resources/CodeQuestLogo.png"));
        ImageView logoView = new ImageView(logoImage);
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(550);

        StackPane logoPane = new StackPane(logoView);
        logoPane.setMinWidth(400);
        logoPane.setPrefWidth(400);

        // Text content setup
        CustomLabel titleLabel = new CustomLabel("Welcome to Code Quest", "title");
        CustomLabel descriptionLabel = new CustomLabel(" Your companion for mastering skills through interactive \n coding quizzes.", "description");
        CustomLabel quizzesLabel = new CustomLabel("\n\t•  View Your Quizzes", "details-bold");
        CustomLabel quizzesDescriptionLabel = new CustomLabel("\t  to see assignments and topics prepared by your \n\t  instructors.", "details");
        CustomLabel exercisesLabel = new CustomLabel("\n\t•  Complete Exercises", "details-bold");
        CustomLabel exercisesDescriptionLabel = new CustomLabel("\t  to demonstrate your knowledge and improve your \n\t  skills.", "details");

        Button getStartedButton = new Button("Get Started");
        getStartedButton.getStyleClass().add("bubbly-button");

        VBox.setMargin(getStartedButton, new Insets(70, 0, 0, 50));

        VBox textBox = new VBox(15);
        textBox.setAlignment(Pos.CENTER_LEFT);
        textBox.getChildren().addAll(titleLabel, descriptionLabel, quizzesLabel, quizzesDescriptionLabel, exercisesLabel, exercisesDescriptionLabel, getStartedButton);
        textBox.setMinWidth(400);
        textBox.setPrefWidth(400);

        StackPane textPane = new StackPane(textBox);
        textPane.setMinWidth(400);
        textPane.setPrefWidth(400);
        textPane.setTranslateY(-30);

        HBox hbox = new HBox(logoPane, textPane);
        HBox.setHgrow(logoPane, Priority.ALWAYS);
        HBox.setHgrow(textPane, Priority.ALWAYS);

        // Create a color overlay
        StackPane overlayPane = new StackPane();
        overlayPane.setBackground(new Background(new BackgroundFill(Color.web("#EEFEFF", 0.7), CornerRadii.EMPTY, Insets.EMPTY)));
        overlayPane.getChildren().add(hbox);

        // Create root pane
        StackPane root = new StackPane();
        root.getChildren().add(overlayPane);

        // Add the scene to the main StackPane
        main.getChildren().setAll(root);

        // Apply CSS stylesheet
        scene.getStylesheets().add(EntryPage.class.getResource("/mini_project/styles/EntryPage.css").toExternalForm());

        // Set action for the "Get Started" button
        getStartedButton.setOnAction(e -> {
            try {
                LoginPage.LoginScreen(main, scene);
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
