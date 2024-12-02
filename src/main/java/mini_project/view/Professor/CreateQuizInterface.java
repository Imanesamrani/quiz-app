package mini_project.view.Professor;


import mini_project.controller.QuizController;
import mini_project.controller.TestCaseController;
import mini_project.model.User;
import mini_project.view.LoginPage;

import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class CreateQuizInterface {
    private int lastQuizId = -1;
    private static User loggedInUser; // Stocker l'utilisateur connecté

    // Méthode pour définir l'utilisateur connecté
    @SuppressWarnings("static-access")
    public void setLoggedInUser(User user) {
        this.loggedInUser = user; // Définir l'utilisateur connecté
    }

    public void createQuizInterface(StackPane stackPane, Scene scene) {


        ImageView arrowImage = new ImageView(new Image("file:D:/quiz_app/java-quiz-app-studentInterface/java-quiz-app-studentInterface/src/main/java/mini_project/resources/arrow.png"));
        arrowImage.setFitWidth(15); // Largeur de la flèche
        arrowImage.setFitHeight(15); // Hauteur de la flèche
        arrowImage.setPickOnBounds(true);
        arrowImage.setOnMouseClicked(e -> {
            QuizView quizApp = new QuizView();
            quizApp.setLoggedInUser(loggedInUser);
            quizApp.showQuizScreen(stackPane, scene);
        });

        // Titre principal "Quiz App"
        Label appTitle = new Label("Quiz App");
        appTitle.setStyle("-fx-font-size: 30; -fx-font-weight: bold;");

        HBox titleBox = new HBox(5, arrowImage, appTitle);
        titleBox.setPadding(new Insets(10));
        titleBox.setAlignment(Pos.CENTER_LEFT);

        // Zone du texte "Welcome back"
        HBox header = new HBox();
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: #FCFCFC; -fx-border-color: black; -fx-border-radius: 10; -fx-background-radius: 10;");
        header.setAlignment(Pos.CENTER_LEFT);

        // Vérifiez si `loggedInUser` est non null
        String lastName = (loggedInUser != null && loggedInUser.getLastname() != null)
                ? loggedInUser.getLastname()
                : "Unknown";

        // Afficher le message de bienvenue
        Label welcomeLabel = new Label("Welcome back, M. " + lastName);
        welcomeLabel.setFont(new Font("Arial", 12));
        welcomeLabel.setStyle("-fx-text-fill: black;-fx-font-size: 18;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button logoutButton = new Button("Log Out");
        logoutButton.setStyle("-fx-background-color: #FF6F61; -fx-text-fill: white; -fx-font-weight: bold;-fx-font-size: 18;");
        logoutButton.setOnAction(e -> {
            // Appeler la méthode de déconnexion et rediriger vers l'écran de connexion
            stackPane.getChildren().clear();  // Effacer la scène actuelle
            try {
                LoginPage loginPage = new LoginPage();
                loginPage.LoginScreen(stackPane, scene);
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

        // Section gauche (3/4 de l'espace)
        VBox leftSection = new VBox(10);
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
        deadlineField.setPromptText("Deadline of submission(HH:mm)");
        deadlineField.setStyle("-fx-border-radius: 8; -fx-background-radius: 8;");
        ImageView warningIcon = new ImageView(new Image("file:java-quiz-app-studentInterface/java-quiz-app-studentInterface/src/main/java/mini_project/resources/remove.png"));
        warningIcon.setFitWidth(15);
        warningIcon.setFitHeight(15);
        warningIcon.setVisible(false);
        // Gestionnaire d'événements pour vérifier la date et l'heure
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

        // Boutons en bas
        Button cancelButton = new Button("Cancel");
        Button createButton = new Button("Create");
        createButton.setStyle("-fx-background-color: #FBB13C; -fx-text-fill: white; -fx-font-weight: bold;-fx-border-radius: 15; -fx-background-radius: 15;-fx-font-size: 18;");
        cancelButton.setStyle(" -fx-font-weight: bold;-fx-border-radius: 15; -fx-background-radius: 15;-fx-font-size: 18;");
        createButton.setPrefSize(150, 50); // Largeur de 150px et hauteur de 50px
        cancelButton.setPrefSize(150, 50);
        // HBox pour les boutons alignés à droite
        HBox buttonBox1 = new HBox(10, cancelButton, createButton);
        buttonBox1.setAlignment(Pos.CENTER_RIGHT); // Alignement à droite
        buttonBox1.setPadding(new Insets(10, 0, 0, 0));
        leftSection.getChildren().addAll(
               createLabeledField("Title", titleField),
              createLabeledField("Objective", objectiveField),
                createLabeledField("Deadline", deadlineContainer),
             createLabeledField("Max Score", maxScoreField),
                createLabeledField("Starter Code", starterCodeField),
                buttonBox1
        );

        // Section droite (1/4 de l'espace)
        VBox rightSection = new VBox(20);
        rightSection.setPadding(new Insets(20));
        rightSection.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-radius: 15; -fx-background-radius: 15;");
        rightSection.setPrefWidth(scene.getWidth() * 0.25); 
        ScrollPane rightScrollPane = new ScrollPane(rightSection);
        // Bouton "Add Test Case"
        Button addTestCaseButton = new Button("Add Test Case");
        addTestCaseButton.setStyle("-fx-background-color: #FBB13C; -fx-text-fill: white; -fx-font-weight: bold;-fx-border-radius: 15; -fx-background-radius: 15;-fx-font-size: 18;");
        // Conteneur pour les test cases
        VBox testCaseContainer = new VBox(10);
        testCaseContainer.setPadding(new Insets(10));
        testCaseContainer.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-radius: 15; -fx-background-radius: 15;");
        
        // Ajouter un conteneur de test dans la section droite
         // Ajouter un conteneur de test case par défaut
     VBox defaultTestCaseBox = new VBox(15);
    defaultTestCaseBox.setPadding(new Insets(15));
    defaultTestCaseBox.setStyle("-fx-border-color: black; -fx-border-radius: 10; -fx-background-color: #FFFFFF; -fx-padding: 10;");
        // Champs de texte pour le conteneur par défaut
        TextField defaultInputField = new TextField();
           defaultInputField.setPromptText("Input");
          TextField defaultOutputField = new TextField();
          defaultOutputField.setPromptText("Output");
     // Boutons "Add" et "Remove" pour le conteneur par défaut
        Button defaultAddButton = new Button("Add");
        Button defaultRemoveButton = new Button("Remove");
        defaultAddButton.setStyle("-fx-background-color: #7FB77E; -fx-text-fill: white;");
        defaultRemoveButton.setStyle("-fx-background-color: #FF6F61; -fx-text-fill: white;");
        // Conteneur horizontal pour les boutons du test case par défaut
        HBox defaultButtonBox = new HBox(10, defaultAddButton, defaultRemoveButton);
        defaultButtonBox.setAlignment(Pos.CENTER_RIGHT);
         defaultTestCaseBox.getChildren().addAll(defaultInputField, defaultOutputField, defaultButtonBox);
         testCaseContainer.getChildren().add(defaultTestCaseBox);

cancelButton.setOnAction(e -> {
    titleField.setText(""); 
    objectiveField.setText("");  
    deadlineField.setText("");  
    maxScoreField.setText(""); 
    starterCodeField.setText("");  
});

// Gestionnaire d'événements pour le bouton "Add Test Case"
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
addButton.setOnAction(addEvent -> {
        if (lastQuizId != -1) {
            try {
                TestCaseController.addTestCaseToDatabase(lastQuizId, inputField.getText(), outputField.getText());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    });
    testCaseBox.getChildren().addAll(inputField, outputField, buttonBox);
    testCaseContainer.getChildren().add(testCaseBox);
});
    defaultRemoveButton.setOnAction(removeEvent -> testCaseContainer.getChildren().remove(defaultTestCaseBox));
// Gestion de l'ajout dans la base de données pour le conteneur par défaut
    defaultAddButton.setOnAction(addEvent -> {
    if (lastQuizId != -1) {
        try {
            TestCaseController.addTestCaseToDatabase(lastQuizId, defaultInputField.getText(), defaultOutputField.getText());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
});
        
        
        rightSection.getChildren().addAll(addTestCaseButton, testCaseContainer);


        // Conteneur central
       
        bodyContainer.getChildren().addAll(leftSection, rightScrollPane);
        // Gestion de l'ajout du quiz à la base de données lors de la création
createButton.setOnAction(e -> {
    try {
        lastQuizId = QuizController.addQuizToDatabase(titleField.getText(), 
        objectiveField.getText(),Integer.parseInt(maxScoreField.getText()), datePicker,deadlineField,starterCodeField.getText());
        titleField.setText(""); 
        objectiveField.setText("");  
        deadlineField.setText("");  
        maxScoreField.setText(""); 
        starterCodeField.setText("");  
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
});
leftSection.prefWidthProperty().bind(scene.widthProperty().multiply(0.75)); // 75% de la largeur
rightSection.prefWidthProperty().bind(scene.widthProperty().multiply(0.25));
rightScrollPane.setFitToWidth(true); 
rightScrollPane.setFitToHeight(true); 
rightScrollPane.setPadding(new Insets(10));
VBox rootContainer = new VBox(topContainer, bodyContainer);
        stackPane.getChildren().setAll(rootContainer);  // Utiliser StackPane pour afficher le contenu
        stackPane.setPadding(new Insets(0, 0, 10, 0)); 
    }
//Pour le deadline 
    public static Node createLabeledField(String label, HBox deadlineContainer) {
    Label labelNode = new Label(label);
    labelNode.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
    // Créer un conteneur HBox pour l'élément de formulaire et le label
    HBox labeledField = new HBox(10, labelNode, deadlineContainer);
    labeledField.setAlignment(Pos.CENTER_LEFT);
    labeledField.setStyle("-fx-padding: 5;");
    return labeledField;
}
public static VBox createLabeledField(String label, Control field) {
        Label lbl = new Label(label);
        lbl.setStyle("-fx-font-weight: bold;-fx-font-size: 18");
        VBox box = new VBox(5, lbl, field);
        return box;
    }

    
}
