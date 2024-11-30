package mini_project.view.StudentInterface;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import mini_project.controller.QuizController;
import mini_project.model.Quiz;
import mini_project.model.User;
import mini_project.view.StudentInterface.Components.createQuizCard;

import java.util.List;

public class QuizAppInterface  {
    private static TilePane quizCards;

    public static void StudentInterface(User studentP, StackPane main, Scene scene) {
        User student = studentP;

        // Root Pane
        VBox root = new VBox();
        root.setPadding(new Insets(20));
        root.setSpacing(20);
        root.setStyle("-fx-background-color: #E8F4FC;-fx-font-size: 1.2em; ");

        // Bind root size to main container
        root.prefWidthProperty().bind(main.widthProperty());
        root.prefHeightProperty().bind(main.heightProperty());

        // Top Bar
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(10);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CCCCCC; -fx-border-width: 0 0 1 0;-fx-font-size: 1.5em;");

        Label welcomeLabel = new Label("Welcome back, " + studentP.getFirstname() + " " + studentP.getLastname());
        welcomeLabel.setFont(Font.font("Arial", 24)); // Set larger font for better visibility

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Circle profileIcon = new Circle(25, Paint.valueOf("#CCCCCC"));
        profileIcon.setStroke(Color.BLACK);
        profileIcon.setStrokeWidth(2);

        topBar.getChildren().addAll(welcomeLabel, spacer, profileIcon);

        // Tab Buttons
        HBox tabBar = new HBox();
        tabBar.setPadding(new Insets(10));
        tabBar.setSpacing(10);
        tabBar.setAlignment(Pos.CENTER_LEFT);

        ToggleButton allQuizzes = new ToggleButton("All Quizzes");
        ToggleButton completed = new ToggleButton("Completed");
        ToggleButton missed = new ToggleButton("Missed");
        ToggleButton pending = new ToggleButton("Pending");

        allQuizzes.setSelected(true);
        setButtonStyle(allQuizzes, completed, missed, pending);

        allQuizzes.setOnAction(event -> {
            setButtonStyle(allQuizzes, completed, missed, pending);
            filterQuizzes("ALL");
        });

        completed.setOnAction(event -> {
            setButtonStyle(completed, allQuizzes, missed, pending);
            filterQuizzes("COMPLETED");
        });

        missed.setOnAction(event -> {
            setButtonStyle(missed, allQuizzes, completed, pending);
            filterQuizzes("MISSED");
        });

        pending.setOnAction(event -> {
            setButtonStyle(pending, allQuizzes, completed, missed);
            filterQuizzes("PENDING");
        });

        tabBar.getChildren().addAll(allQuizzes, completed, missed, pending);

        // Quiz Cards
        quizCards = new TilePane();
        quizCards.setPadding(new Insets(20));
        quizCards.setHgap(20);
        quizCards.setVgap(20);
        quizCards.setAlignment(Pos.TOP_CENTER);

        // Bind quizCards size to root
        quizCards.prefWidthProperty().bind(root.widthProperty().subtract(40));
        quizCards.prefHeightProperty().bind(root.heightProperty().multiply(0.8));

        // Load all quizzes by default
        filterQuizzes("ALL");

        root.getChildren().addAll(topBar, tabBar, quizCards);

        // Apply CSS
//        scene.getStylesheets().add(QuizAppInterface.class.getResource("/mini_project/styles/StudentInterface.css").toExternalForm());

        // Add the root pane to the main container
        main.getChildren().setAll(root);
    }



    private static void filterQuizzes(String category) {
        quizCards.getChildren().clear();


        List<Quiz> quizzes = QuizController.fetchQuizzesFromDatabase(category);


        for (Quiz quiz : quizzes) {
            VBox card = createQuizCard.CreateQuizCard("Quiz :"+quiz.getId(), quiz.getTitle());

            // Add click listener to each card
            card.setOnMouseClicked(event -> openQuizExercise(quiz));
            quizCards.getChildren().add(card);
        }
    }

    // function dial styling color dial tob bar buttons
    private static void setButtonStyle(ToggleButton selectedButton, ToggleButton... otherButtons) {
        // buttons lwla nrj3oha active
        selectedButton.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white; -fx-background-radius: 5em;");

        // khrin aykouno default color
        for (ToggleButton button : otherButtons) {
            button.setStyle("-fx-background-color: #D3D3D3; -fx-text-fill: black; -fx-background-radius: 5em;");
        }
    }

    // Open the quiz exercise interface
    private static void openQuizExercise(Quiz quiz) {
        Stage exerciseStage = new Stage();
        Quiz quizDetails = QuizController.getQuizDetailsById(quiz.getId());
        QuizExerciseAdjustedInterface exerciseInterface = new QuizExerciseAdjustedInterface();
        exerciseInterface.setQuizDetails(quizDetails); // Pass quiz details
        try {
            exerciseInterface.start(exerciseStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        launch(args);
//    }
}
