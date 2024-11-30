package mini_project.view.Professor;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import mini_project.controller.CreateQuizInterfaceController;
import mini_project.controller.LoginController;
import mini_project.controller.QuizController;
import mini_project.model.Quiz;
import mini_project.model.TestCase;
import mini_project.model.User;
import mini_project.view.LoginPage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class QuizDetails {
    private static User loggedInUser; // Stocker l'utilisateur connecté
    
        public void setLoggedInUser(User user) {
            this.loggedInUser = user; // Définir l'utilisateur connecté
        }
        
        private static  int lastQuizId = -1; 
             public static void showQuizDetails(int quizId, Stage currentStage) {
        BorderPane mainLayout = new BorderPane();
        // Maximiser la fenêtre
        currentStage.setMaximized(true); 
        // Récupérer les détails du quiz à partir de la base de données
        Quiz quiz = QuizController.getQuizDetailsById(quizId);
        lastQuizId = quizId;  
    
        ImageView arrowImage = new ImageView(new Image("file:D:/quiz_app/java-quiz-app-studentInterface/java-quiz-app-studentInterface/src/main/java/mini_project/resources/arrow.png"));
        arrowImage.setFitWidth(15); // Largeur de la flèche
        arrowImage.setFitHeight(15); // Hauteur de la flèche
        arrowImage.setPickOnBounds(true);
        arrowImage.setOnMouseClicked(e -> {
           
            QuizView quizApp = new QuizView();
            quizApp.setLoggedInUser(loggedInUser);
            quizApp.start(currentStage);
           
        });
            
                
                Label appTitle = new Label("Quiz App");
                appTitle.setStyle("-fx-font-size: 30; -fx-font-weight: bold;");
        
                HBox titleBox = new HBox(5,arrowImage,appTitle);
                titleBox.setPadding(new Insets(10));
                titleBox.setAlignment(Pos.CENTER_LEFT);
        
                // Zone du texte "Welcome back"
                HBox header = new HBox();
                header.setPadding(new Insets(5,10,10,10));
                header.setStyle("-fx-background-color: #FCFCFC; -fx-border-color: black; -fx-border-radius: 10; -fx-background-radius: 10;");
                header.setAlignment(Pos.CENTER_LEFT);
        
                // Vérifier si l'utilisateur est défini
           String lastName = (loggedInUser != null) ? loggedInUser.getLastname() : "Unknown";
       Label welcomeLabel = new Label("Welcome back, M. " + lastName);
            welcomeLabel.setFont(new Font("Arial", 12));
            welcomeLabel.setStyle("-fx-text-fill: black; -fx-font-size: 18");
    
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
    
            Button logoutButton = new Button("Log Out");
            logoutButton.setStyle("-fx-background-color: #FF6F61; -fx-text-fill: white; -fx-font-weight: bold;-fx-font-size: 18;");
            logoutButton.setOnAction(e -> {
                 // Appeler la méthode de déconnexion et rediriger vers l'écran de connexion
            currentStage.close();
            try {
                LoginPage loginPage = new LoginPage();
                loginPage.LoginScreen(new StackPane(), new Scene(new VBox(), 800, 600));
            } catch (Exception ex) {
                ex.printStackTrace();
            } // Fermer la fenêtre actuelle
                        });
   header.getChildren().addAll(welcomeLabel, spacer, logoutButton);
     VBox topContainer = new VBox(10, titleBox, header);
     topContainer.setPadding(new Insets(10));
     // Conteneur central divisé en deux parties
    HBox bodyContainer = new HBox(10);
    bodyContainer.setPadding(new Insets(10));
        // Section gauche (3/4 de l'espace)
        VBox leftSection = new VBox(20);
            leftSection.setPadding(new Insets(20));
            leftSection.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-radius: 15; -fx-background-radius: 15;");
        TextField titleField = new TextField();
            titleField.setPromptText("Quiz title");
            titleField.setStyle("-fx-border-radius: 8; -fx-background-radius: 8; ");
            TextArea objectiveField = new TextArea();
            objectiveField.setPromptText("Description or objective of this programming problem");
            objectiveField.setStyle("-fx-border-radius: 8; -fx-background-radius: 8;");
        // DatePicker pour la sélection de la date
         DatePicker datePicker = new DatePicker();
            datePicker.setPromptText("Select Date");
        TextField deadlineField = new TextField();
            deadlineField.setPromptText("Deadline of submission");
            deadlineField.setStyle("-fx-border-radius: 8; -fx-background-radius: 8;");
         ImageView warningIcon = new ImageView(new Image("file:java-quiz-app-studentInterface/java-quiz-app-studentInterface/src/main/java/mini_project/resources/remove.png"));
            warningIcon.setFitWidth(20);
            warningIcon.setFitHeight(20);
            warningIcon.setVisible(false); 
           datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            QuizController.checkDeadline(datePicker, deadlineField, warningIcon);
            });
            deadlineField.textProperty().addListener((observable, oldValue, newValue) -> {
                QuizController.checkDeadline(datePicker, deadlineField, warningIcon);
            });
            // Layout principal pour la date et l'heure
            HBox deadlineContainer = new HBox(10, datePicker, deadlineField, warningIcon);
            deadlineContainer.setAlignment(Pos.CENTER_LEFT);            
            TextField maxScoreField = new TextField();
            maxScoreField.setPromptText("Grade or points of this quiz");
            maxScoreField.setStyle("-fx-border-radius: 8; -fx-background-radius: 8;");            
            TextArea starterCodeField = new TextArea();
            starterCodeField.setPromptText("Provide any starter code for students...");
            starterCodeField.setStyle("-fx-border-radius: 8; -fx-background-radius: 8;");
             // Remplir les champs avec les données du quiz
            titleField.setText(quiz.getTitle());
            objectiveField.setText(quiz.getObjective());
            // Récupérer la date depuis quiz
           LocalDate quizDate = quiz.getDate();
            datePicker.setValue(quizDate);
            deadlineField.setText(quiz.getDeadLine());
            maxScoreField.setText(String.valueOf(quiz.getMaxScore()));
            starterCodeField.setText(quiz.getStarterCode());  
        // Boutons en bas
            Button cancelButton = new Button("Remove");
            Button createButton = new Button("Modify");
            createButton.setStyle("-fx-background-color: #FBB13C; -fx-text-fill: white; -fx-font-weight: bold;-fx-border-radius: 15; -fx-background-radius: 15;-fx-font-size: 16;");
            cancelButton.setStyle(" -fx-font-weight: bold;-fx-border-radius: 15; -fx-background-radius: 15;-fx-font-size: 16;");
            createButton.setPrefSize(150, 50); // Largeur de 150px et hauteur de 50px
            cancelButton.setPrefSize(150, 50);
        // HBox pour les boutons alignés à droite
        HBox buttonBox1 = new HBox(10, cancelButton, createButton);
            buttonBox1.setAlignment(Pos.CENTER_RIGHT); 
            buttonBox1.setPadding(new Insets(5, 10, 1, 10));
        leftSection.getChildren().addAll(
            CreateQuizInterfaceController.createLabeledField("Title", titleField),
            CreateQuizInterfaceController.createLabeledField("Objective", objectiveField),
            CreateQuizInterfaceController.createLabeledField("Deadline", deadlineContainer),
            CreateQuizInterfaceController.createLabeledField("Max Score", maxScoreField),
            CreateQuizInterfaceController.createLabeledField("Starter Code", starterCodeField),
            buttonBox1 
    );                                                   
        // Récupérer les test cases associés à ce quiz
        List<TestCase> testCases = QuizController.getTestCasesByQuizId(quizId);
        VBox testCaseContainer = new VBox(10);
        testCaseContainer.setPadding(new Insets(10));
        testCaseContainer.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-radius: 15; -fx-background-radius: 15;");
        if (!testCases.isEmpty()) {
        // Premier test case
        TestCase firstTestCase = testCases.get(0);
        VBox defaultTestCaseBox = CreateQuizInterfaceController.createTestCaseBox(firstTestCase);
        testCaseContainer.getChildren().add(defaultTestCaseBox);
        for (int i = 1; i < testCases.size(); i++) {
        TestCase testCase = testCases.get(i);
        VBox testCaseBox = CreateQuizInterfaceController.createTestCaseBox(testCase);
        testCaseContainer.getChildren().add(testCaseBox);
     }
}
        VBox rightSection = new VBox(20);
        rightSection.setPadding(new Insets(20));
        rightSection.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-radius: 15; -fx-background-radius: 15;");
        ScrollPane rightScrollPane = new ScrollPane(rightSection);    
        // Bouton "Add Test Case"
           Button addTestCaseButton = new Button("Add Test Case");
          addTestCaseButton.setStyle("-fx-background-color: #FBB13C; -fx-text-fill: white; -fx-font-weight: bold;-fx-border-radius: 15; -fx-background-radius: 15;-fx-font-size: 18");
          rightSection.getChildren().addAll(addTestCaseButton, testCaseContainer);
          bodyContainer.getChildren().addAll(leftSection, rightSection,rightScrollPane);                                         
// Gestion de l'ajout de test cases
addTestCaseButton.setOnAction(e -> {
    VBox testCaseBox = new VBox(15);
    testCaseBox.setPadding(new Insets(15));
    testCaseBox.setStyle("-fx-border-color: black; -fx-border-radius: 10; -fx-background-color: #FFFFFF; -fx-padding: 10;");  
    TextField inputField = new TextField();
    inputField.setPromptText("Input");
    TextField outputField = new TextField();
    outputField.setPromptText("Output");   
    Button addButton = new Button("Add");
     Button removeButton = new Button("Remove");
     addButton.setStyle("-fx-background-color: #7FB77E; -fx-text-fill: white;");
    removeButton.setStyle("-fx-background-color: #FF6F61; -fx-text-fill: white;");  
    HBox buttonBox = new HBox(10, addButton, removeButton);
     buttonBox.setAlignment(Pos.CENTER_RIGHT); 
    // Gestion de la suppression du conteneur
     removeButton.setOnAction(removeEvent -> testCaseContainer.getChildren().remove(testCaseBox));  
 // Gestion de l'ajout dans la base de données
     addButton.setOnAction(addEvent -> {
        if (lastQuizId != -1) {
        try {
        QuizController.addTestCaseToDatabase(lastQuizId, inputField.getText(), outputField.getText());
        inputField.setText("");
        outputField.setText("");
        } catch (SQLException ex) {
            ex.printStackTrace();
       }
       }
});  
            testCaseBox.getChildren().addAll(inputField, outputField, buttonBox);
            testCaseContainer.getChildren().add(testCaseBox);
});                                                                                                                                                                
createButton.setOnAction(e -> {
    try {
    // Convertir maxScore en entier et mettre à jour le quiz
        int maxScore = Integer.parseInt(maxScoreField.getText());
        QuizController.updateQuizInDatabase(lastQuizId, titleField.getText(), objectiveField.getText(),
         maxScore,datePicker, deadlineField, starterCodeField.getText());
      
    } catch (NumberFormatException ex) {
         System.out.println("Veuillez entrer un nombre valide pour le score maximal.");
        ex.printStackTrace();
    } catch (SQLException ex) {
    System.out.println("Erreur SQL lors de la mise à jour du quiz.");
     ex.printStackTrace();
    }
});             
cancelButton.setOnAction(e -> {
   try {
    // Supprimer d'abord les test cases associés à ce quiz
    QuizController.deleteTestCasesByQuizId(lastQuizId);
    QuizController.deleteQuizFromDatabase(lastQuizId);
    // Aller au scene QuizApp
      QuizView quizApp = new QuizView();  
      quizApp.start(currentStage);            
    } catch (SQLException ex) {
       System.out.println("Erreur lors de la suppression du quiz ou des test cases : " + ex.getMessage());
       ex.printStackTrace();
    }
});
    mainLayout.setTop(topContainer);
    mainLayout.setCenter(bodyContainer);
    mainLayout.setStyle("-fx-background-color: #EEFEFF;-fx-pref-width: 100%; -fx-pref-height: 100%;");                                                 
    // Ajuster les proportions : 75% pour leftSection, 25% pour rightSection
      HBox.setHgrow(leftSection, Priority.ALWAYS);
      HBox.setHgrow(rightSection, Priority.NEVER);
      leftSection.prefWidthProperty().bind(currentStage.widthProperty().multiply(0.75)); // 75% de la largeur
      rightSection.prefWidthProperty().bind(currentStage.widthProperty().multiply(0.25)); // 25% de la largeur                                
      rightScrollPane.setFitToWidth(true); 
      rightScrollPane.setFitToHeight(true); 
      rightScrollPane.setPadding(new Insets(10));                                                                                                                                                                                         
      mainLayout.setPadding(new Insets(0, 20, 70, 0)); 
      Scene newScene = new Scene(mainLayout,  currentStage.getWidth(), currentStage.getHeight()); // Taille de la scène 
      currentStage.setScene(newScene); // Changer la scène actuelle pour la nouvelle scène
}
                                                                          
                                                     
                                                                                                                    

           
}
