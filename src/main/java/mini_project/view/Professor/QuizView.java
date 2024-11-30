package mini_project.view.Professor;

import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import mini_project.controller.QuizController;

import mini_project.model.User;
import mini_project.view.LoginPage;

public class QuizView extends Application {
    
    private User loggedInUser; // Stocker l'utilisateur connecté

    public void setLoggedInUser(User user) {
        this.loggedInUser = user; // Définir l'utilisateur connecté
    }
// Créer une instance de CreateQuizInterface
CreateQuizInterface createQuizInterface = new CreateQuizInterface();

// Créer une instance de CreateQuizInterface
QuizDetails quizDetails = new QuizDetails();


   
    @Override
    public void start(Stage primaryStage) {
       // Passer l'utilisateur connecté à CreateQuizInterface
createQuizInterface.setLoggedInUser(loggedInUser);
quizDetails.setLoggedInUser(loggedInUser);
   // Maximiser la fenêtre dès l'ouverture de la scène principale
   primaryStage.setMaximized(true);
        // Main Layout: Background light sky blue
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #EEFEFF;"); // Sky blue background

        Label appTitle = new Label("Quiz App");
        appTitle.setStyle("-fx-font-size: 30; -fx-font-weight: bold;");
        

        // Top Section: Header
        VBox topContainer = new VBox(10,appTitle);
        topContainer.setPadding(new Insets(15, 15, 10, 15));

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
        welcomeLabel.setFont(new Font("Arial", 18));
        welcomeLabel.setStyle("-fx-text-fill: black;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button logoutButton = new Button("Log Out");
        logoutButton.setStyle("-fx-background-color: #FF6F61; -fx-text-fill: white; -fx-font-weight: bold;-fx-font-size: 18;");
        logoutButton.setOnAction(e -> {
             primaryStage.close();
            try {
                LoginPage loginPage = new LoginPage();
                loginPage.LoginScreen(new StackPane(), new Scene(new VBox(), 800, 600));
            } catch (Exception ex) {
                ex.printStackTrace();
            } // Fermer la fenêtre actuelle
        });
        header.getChildren().addAll(welcomeLabel, spacer, logoutButton);
        topContainer.getChildren().add(header);
       
        mainLayout.setTop(topContainer);


        // Center Section: Split into two parts (75% and 25%)
        HBox centerContent = new HBox();
        centerContent.setPadding(new Insets(20));
        centerContent.setSpacing(20);

        // Left Section: Quiz Area (75%)
        VBox quizSection = new VBox(20);
        quizSection.setPadding(new Insets(20));
        quizSection.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-radius: 15; -fx-background-radius: 15;");

        //quizSection.setPrefWidth(1000); // 75% width

        // Buttons for "Quizzes" and "Statistics" + Create Button
        HBox quizButtonsBar = new HBox(10);
        quizButtonsBar.setAlignment(Pos.CENTER_LEFT);

      
      
        

        Region flexibleSpacer = new Region();
        HBox.setHgrow(flexibleSpacer, Priority.ALWAYS);

        Button createButton = new Button("Create");
        createButton.setStyle("-fx-background-color: #FFBC5E; -fx-text-fill: black; -fx-border-radius: 10; -fx-background-radius: 10;");
        createButton.setPrefSize(100, 40);

        quizButtonsBar.getChildren().addAll( flexibleSpacer, createButton);

        VBox leftSection = new VBox();
        leftSection.setSpacing(10);
        leftSection.setPadding(new Insets(20));
        leftSection.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-radius: 15; -fx-background-radius: 15;");
        
        // Partie dynamique (changera avec les boutons)
        VBox dynamicContent = new VBox();
        dynamicContent.setPadding(new Insets(10));
        dynamicContent.setSpacing(10);
        
        // Ajoutez les boutons fixes et la partie dynamique dans la section gauche
        leftSection.getChildren().addAll(quizButtonsBar, dynamicContent);
        


        // TP Buttons (Aligned horizontally with scrolling)
        FlowPane tpButtonsPane = new FlowPane();
        tpButtonsPane.setStyle("-fx-background-radius: 10;");
        tpButtonsPane.setPadding(new Insets(10));
        tpButtonsPane.setHgap(10); // Horizontal spacing
        tpButtonsPane.setVgap(10); // Vertical spacing
        tpButtonsPane.setPrefWrapLength(700);
         // Maximum width before wrapping

         // Partie dynamique : Remplace les boutons par un TabPane
TabPane tabPane = new TabPane();
tabPane.setStyle("-fx-tab-min-width: 100; -fx-tab-min-height: 30;");

// Onglet Quizzes
Tab quizzesTab = new Tab("Quizzes");
quizzesTab.setClosable(false);
quizzesTab.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
ScrollPane quizScrollPane = new ScrollPane(tpButtonsPane);
quizScrollPane.setFitToWidth(true);
quizScrollPane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-radius: 15;");
quizzesTab.setContent(quizScrollPane);

// Onglet Statistics
Tab statisticsTab = new Tab("Statistics");
statisticsTab.setClosable(false);
statisticsTab.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
FlowPane statisticsPane = new FlowPane();
statisticsPane.setPadding(new Insets(10));
statisticsPane.setHgap(10);
statisticsPane.setVgap(10);
ScrollPane statisticsScrollPane = new ScrollPane(statisticsPane);
statisticsScrollPane.setFitToWidth(true);
statisticsScrollPane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-radius: 15;");
statisticsTab.setContent(statisticsScrollPane);

// Ajouter les onglets au TabPane
tabPane.getTabs().addAll(quizzesTab, statisticsTab);

// Créer un VBox pour contenir le bouton Create et le TabPane
VBox quizContentContainer = new VBox(10); // Espacement de 10 entre les éléments
quizContentContainer.setPadding(new Insets(10)); // Ajout de marges internes

// HBox pour aligner le bouton Create à droite
HBox createButtonContainer = new HBox();
createButtonContainer.setAlignment(Pos.CENTER_RIGHT); // Aligner le bouton à droite
createButtonContainer.getChildren().add(createButton); // Ajouter le bouton Create

// Ajouter le bouton Create et le TabPane au VBox
quizContentContainer.getChildren().addAll(createButtonContainer, tabPane);

// Ajouter le VBox au quizSection
quizSection.getChildren().clear(); // Vider la section pour un nouveau contenu
quizSection.getChildren().add(quizContentContainer);

        
// Get the list of quizzes from the database
List<String> quizzes = QuizController.getQuizList(); 

// Create buttons for each quiz dynamically
int quizNumber = 1; // To display "Quiz 1", "Quiz 2", etc.
for (String quizTitle : quizzes) {
    // Set button text with "Quiz X" and title on separate lines
    String buttonText = "Quiz " + quizNumber + "\n" + quizTitle;
    Button tpButton = new Button(buttonText);
    tpButton.setPrefSize(120, 80); // Adjust size as needed
    tpButton.setStyle("-fx-background-color: #D9D9D9; -fx-text-fill: black; -fx-text-alignment: center; -fx-font-size: 16px;"
            + "-fx-effect: dropshadow(gaussian, #EEFEFF, 10, 0.5, 0, 0);");
  

    // Ajouter un gestionnaire d'événements pour chaque bouton
    tpButton.setOnAction(e -> {
         // Récupérer l'ID du quiz à partir de la base de données (vous pouvez utiliser le titre pour obtenir l'ID)
    int quizId = QuizController.getQuizIdByTitle(quizTitle);
    // Appeler la méthode pour afficher les détails du quiz et remplir les champs
    QuizDetails.showQuizDetails(quizId, primaryStage);
   
   
    });
    tpButtonsPane.getChildren().add(tpButton);
    quizNumber++; // Increment the quiz number
}

        // Add FlowPane inside a ScrollPane
        ScrollPane scrollPane = new ScrollPane(tpButtonsPane);
        scrollPane.setFitToWidth(true); // Allow the ScrollPane to fit the width of its content
        scrollPane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-radius: 15;-fx-background-radius: 15;-fx-padding: 4;"); // Styling

        // Add everything to the quiz section
        quizSection.getChildren().addAll(quizButtonsBar, quizScrollPane);

        // Right Section: Student List (25%)
        VBox studentSection = new VBox(20);
        studentSection.setPadding(new Insets(20));
        studentSection.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-radius: 15; -fx-background-radius: 15;");
        //studentSection.setPrefWidth(250); // 25% width

        Label studentListTitle = new Label("Student List");
        studentListTitle.setFont(new Font("Arial", 18));
        studentListTitle.setStyle("-fx-color: black;  -fx-font-weight: bold;");

        
        // List of Students with rounded borders
        VBox studentList = new VBox(10);
        studentList.setPadding(new Insets(10));



        // Obtenez la liste des étudiants depuis la base de données
        List<String> students = QuizController.getStudentList();

        // Ajoutez chaque étudiant comme un label dans la VBox
        for (String student : students) {
            Label studentLabel = new Label(student);
            studentLabel.setStyle("-fx-background-color: #D9D9D9; -fx-text-fill: black; -fx-padding: 10; -fx-border-radius: 10; -fx-background-radius: 10;-fx-font-size: 16px;");
            studentLabel.setPrefWidth(160);
            studentList.getChildren().add(studentLabel);
        }

        studentSection.getChildren().addAll(studentListTitle, studentList);


        // Add both sections to the center layout
        centerContent.getChildren().addAll(quizSection, studentSection);
        mainLayout.setCenter(centerContent);
        mainLayout.setStyle("-fx-pref-width: 100%; -fx-pref-height: 100%;-fx-background-color: #EEFEFF;");
       
        // Event handler to open Create Quiz interface
        createButton.setOnAction(e -> {
            CreateQuizInterface createQuizInterface = new CreateQuizInterface();
            createQuizInterface.start(primaryStage);
        });

        

         // Ajuster les proportions : 75% pour leftSection, 25% pour rightSection
         HBox.setHgrow(quizSection, Priority.ALWAYS);
         HBox.setHgrow(studentSection, Priority.NEVER);
         quizSection.setPrefWidth(750);  // Largeur suggérée si nécessaire
         studentSection.setPrefWidth(250); 
            // Maximiser la fenêtre
    primaryStage.setMaximized(true);
    mainLayout.setPadding(new Insets(0, 20, 40, 0)); 
        // Scene and Stage Setup
        Scene scene = new Scene(mainLayout,  primaryStage.getWidth(), primaryStage.getHeight()); // Adjusted size for better layout
        primaryStage.setTitle("Quiz App");
        primaryStage.setScene(scene);
        primaryStage.show();
    
}

}