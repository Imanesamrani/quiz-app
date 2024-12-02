package mini_project.view.Professor;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


import mini_project.controller.LoginController;
import mini_project.controller.QuizController;
import mini_project.controller.TestCaseController;
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


public class QuizDetails {
    private static User loggedInUser; // Stocker l'utilisateur connecté
    
        public void setLoggedInUser(User user) {
            this.loggedInUser = user; // Définir l'utilisateur connecté
        }
        
        private static  int lastQuizId = -1; 
        public static void showQuizDetails(int quizId, StackPane root, Scene scene) {
            BorderPane mainLayout = new BorderPane();
            
        
        
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
                quizApp.showQuizScreen(root, scene); // Retour à la vue précédente
            });
        
            Label appTitle = new Label("Quiz App");
            appTitle.setStyle("-fx-font-size: 30; -fx-font-weight: bold;");
        
            HBox titleBox = new HBox(5, arrowImage, appTitle);
            titleBox.setPadding(new Insets(10));
            titleBox.setAlignment(Pos.CENTER_LEFT);
        
            // Zone du texte "Welcome back"
            HBox header = new HBox();
            header.setPadding(new Insets(5, 10, 10, 10));
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
                root.getChildren().clear();
                try {
                    LoginPage loginPage = new LoginPage();
                    loginPage.LoginScreen(root, scene);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            header.getChildren().addAll(welcomeLabel, spacer, logoutButton);
            VBox topContainer = new VBox(10, titleBox, header);
            topContainer.setPadding(new Insets(10));
        
            // Conteneur central divisé en deux parties
            HBox bodyContainer = new HBox(10);
            bodyContainer.setPadding(new Insets(10));
        
            // Contenu de la section gauche (3/4 de l'espace)
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
            CreateQuizInterface.createLabeledField("Title", titleField),
            CreateQuizInterface.createLabeledField("Objective", objectiveField),
            CreateQuizInterface.createLabeledField("Deadline", deadlineContainer),
            CreateQuizInterface.createLabeledField("Max Score", maxScoreField),
            CreateQuizInterface.createLabeledField("Starter Code", starterCodeField),
            buttonBox1 
    );                                                   
        // Récupérer les test cases associés à ce quiz
        List<TestCase> testCases = TestCaseController.getTestCasesByQuizId(quizId);
        VBox testCaseContainer = new VBox(10);
        testCaseContainer.setPadding(new Insets(10));
        testCaseContainer.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-radius: 15; -fx-background-radius: 15;");
        if (!testCases.isEmpty()) {
        // Premier test case
        TestCase firstTestCase = testCases.get(0);
        VBox defaultTestCaseBox = createTestCaseBox(firstTestCase);
        testCaseContainer.getChildren().add(defaultTestCaseBox);
        for (int i = 1; i < testCases.size(); i++) {
        TestCase testCase = testCases.get(i);
        VBox testCaseBox = createTestCaseBox(testCase);
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
          //bodyContainer.getChildren().addAll(leftSection, rightSection,rightScrollPane);                                         
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
        TestCaseController.addTestCaseToDatabase(lastQuizId, inputField.getText(), outputField.getText());
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
    TestCaseController.deleteTestCasesByQuizId(lastQuizId);
    QuizController.deleteQuizFromDatabase(lastQuizId);
    // Aller au scene QuizApp
      QuizView quizApp = new QuizView();  
      quizApp.showQuizScreen(root, scene);           
    } catch (SQLException ex) {
       System.out.println("Erreur lors de la suppression du quiz ou des test cases : " + ex.getMessage());
       ex.printStackTrace();
    }
});
bodyContainer.getChildren().clear();  
bodyContainer.getChildren().addAll(leftSection, rightSection, rightScrollPane);

            mainLayout.setTop(topContainer);
            mainLayout.setCenter(bodyContainer);
            leftSection.prefWidthProperty().bind(scene.widthProperty().multiply(0.75)); // 75% de la largeur
            rightSection.prefWidthProperty().bind(scene.widthProperty().multiply(0.25));
        
            rightScrollPane.setFitToWidth(true); 
            rightScrollPane.setFitToHeight(true); 
            rightScrollPane.setPadding(new Insets(10)); 
            // Mise à jour du contenu dans le StackPane
            root.getChildren().clear();
            root.getChildren().add(mainLayout);

        
        }
           
        public static VBox createTestCaseBox(TestCase testCase) {
            VBox testCaseBox = new VBox(15);
            testCaseBox.setPadding(new Insets(15));
            testCaseBox.setStyle("-fx-border-color: black; -fx-border-radius: 10; -fx-background-color: #FFFFFF; -fx-padding: 10;");
                     
            TextField inputField = new TextField();
            inputField.setPromptText("Input");
            inputField.setText(testCase.getInput()); // Remplir le champ avec l'input du test case
                 
            TextField outputField = new TextField();
            outputField.setPromptText("Output");
            outputField.setText(testCase.getOutput()); // Remplir le champ avec l'output du test case
                 
            HBox buttonBox = new HBox(10);
            buttonBox.setAlignment(Pos.CENTER_RIGHT);
                     
            
            Button modifyButton = new Button("Modify");
            modifyButton.setStyle("-fx-background-color: #7FB77E; -fx-text-fill: white;");
            // Gestionnaire d'événements pour le bouton "Modify"
            modifyButton.setOnAction(e -> {
             try {
            // Récupérer l'ID du test case actuel
            int testCaseId = testCase.getId(); // Le test case actuel
            String currentInput = testCase.getInput(); 
            String currentOutput = testCase.getOutput(); 
            // si les neauvelle valeurs ont iddentiques aux anciennes valeurs
            if (currentInput.equals(inputField.getText()) && currentOutput.equals(outputField.getText())) {
            System.out.println("Les nouvelles valeurs sont identiques aux valeurs actuelles. Aucune mise à jour nécessaire.");
            return; // Rien ne change
            }
            TestCaseController.updateTestCaseInDatabase(testCaseId, inputField.getText(), outputField.getText());
                     } catch (SQLException e1) {
            System.out.println("Erreur lors de la mise à jour du test case : " + e1.getMessage());
                         e1.printStackTrace();
                     }
                 }); 
            // Button remove 
            Button removeButton = new Button("Remove");
            removeButton.setStyle("-fx-background-color: #FF6F61; -fx-text-fill: white;");
            // Gestionnaire d'événements pour le bouton "Remove"
            removeButton.setOnAction(e -> {
             try {
            // Récupérer l'ID du test case à supprimer
            int testCaseId = testCase.getId();
            TestCaseController.deleteTestCaseFromDatabase(testCaseId);                    
            testCaseBox.getChildren().clear(); // Enlever tout le contenu du VBox
            System.out.println("Test case avec ID " + testCaseId + " supprimé.");
                         } catch (SQLException e1) {
            System.out.println("Erreur lors de la suppression du test case : " + e1.getMessage());
                             e1.printStackTrace();
                         }
                     });
            buttonBox.getChildren().addAll(modifyButton,removeButton);
            testCaseBox.getChildren().addAll(inputField, outputField, buttonBox);
            return testCaseBox;
    }
}
