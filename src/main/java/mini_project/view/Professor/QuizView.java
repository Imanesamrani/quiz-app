package mini_project.view.Professor;

import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import mini_project.controller.QuizController;
import mini_project.model.User;
import mini_project.view.LoginPage;

public class QuizView {

    private User loggedInUser; // Stocker l'utilisateur connecté

    public void setLoggedInUser(User user) {
        this.loggedInUser = user; // Définir l'utilisateur connecté
    }

    // Créer une instance de CreateQuizInterface
    CreateQuizInterface createQuizInterface = new CreateQuizInterface();

    // Créer une instance de QuizDetails
    QuizDetails quizDetails = new QuizDetails();

    public void showQuizScreen(StackPane main, Scene scene) {
        // Passer l'utilisateur connecté à CreateQuizInterface
        createQuizInterface.setLoggedInUser(loggedInUser);
        quizDetails.setLoggedInUser(loggedInUser);

        // Main Layout: Background light sky blue
        BorderPane mainLayout = new BorderPane();
        Label appTitle = new Label("Quiz App");
        appTitle.setStyle("-fx-font-size: 30; -fx-font-weight: bold;");

        // Top Section: Header
        VBox topContainer = new VBox(10, appTitle);
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
            main.getScene().getWindow().hide();
            try {
                LoginPage loginPage = new LoginPage();
                loginPage.LoginScreen(new StackPane(), new Scene(new VBox(), 800, 600));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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

        HBox quizButtonsBar = new HBox(10);
        quizButtonsBar.setAlignment(Pos.CENTER_LEFT);

        Region flexibleSpacer = new Region();
        HBox.setHgrow(flexibleSpacer, Priority.ALWAYS);

        Button createButton = new Button("Create");
        createButton.setStyle("-fx-background-color: #FBB13C; -fx-text-fill: white; -fx-font-weight: bold;-fx-border-radius: 15; -fx-background-radius: 15;-fx-font-size: 18;");
        createButton.setPrefSize(100, 40);

        quizButtonsBar.getChildren().addAll(flexibleSpacer, createButton);

        VBox leftSection = new VBox();
        leftSection.setSpacing(10);
        leftSection.setPadding(new Insets(20));
        leftSection.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-radius: 15;");
        
        VBox dynamicContent = new VBox();
        dynamicContent.setPadding(new Insets(10));
        dynamicContent.setSpacing(10);

        leftSection.getChildren().addAll(quizButtonsBar, dynamicContent);

        // TP Buttons (Aligned horizontally with scrolling)
        FlowPane tpButtonsPane = new FlowPane();
        tpButtonsPane.setStyle("-fx-background-radius: 10;");
        tpButtonsPane.setPadding(new Insets(10));
        tpButtonsPane.setHgap(10);
        tpButtonsPane.setVgap(10);
        tpButtonsPane.setPrefWrapLength(700);

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
        VBox statisticsContent = new VBox(20);
        statisticsContent.setPadding(new Insets(20));
        statisticsContent.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-radius: 15;");

        // Conteneur horizontal pour les statistiques générales
HBox generalStatsContainer = new HBox(20); // Espacement de 20 entre les éléments
generalStatsContainer.setAlignment(Pos.CENTER);
generalStatsContainer.setPadding(new Insets(10));

// Style commun pour les boîtes de statistiques
String statBoxStyle = "-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 10; " +
                      "-fx-background-color: #F0F0F0; -fx-padding: 15;";
// Obtenir le nombre total d'étudiants depuis la base de données
int totalStudents = QuizController.getTotalStudents();
// Statistique : Total Students
VBox totalStudentsBox = new VBox();
totalStudentsBox.setAlignment(Pos.CENTER);
totalStudentsBox.setStyle(statBoxStyle);
Label totalStudentsLabel = new Label("Total Students");
totalStudentsLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
Label totalStudentsValue = new Label(String.valueOf(totalStudents));
totalStudentsValue.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #0078D7;");
totalStudentsBox.getChildren().addAll(totalStudentsLabel, totalStudentsValue);

// Obtenir le nombre total de quizzes depuis la base de données
int totalQuizzes = QuizController.getTotalQuizzes();
// Statistique : Total Quizzes
VBox totalQuizzesBox = new VBox();
totalQuizzesBox.setAlignment(Pos.CENTER);
totalQuizzesBox.setStyle(statBoxStyle);
Label totalQuizzesLabel = new Label("Total Quizzes");
totalQuizzesLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
Label totalQuizzesValue = new Label(String.valueOf(totalQuizzes));
totalQuizzesValue.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #0078D7;");
totalQuizzesBox.getChildren().addAll(totalQuizzesLabel, totalQuizzesValue);

// Obtenir le score moyen depuis la base de données
double averageScore = QuizController.getAverageScore();
// Statistique : Average Score
VBox averageScoreBox = new VBox();
averageScoreBox.setAlignment(Pos.CENTER);
averageScoreBox.setStyle(statBoxStyle);
Label averageScoreLabel = new Label("Average Score");
averageScoreLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
Label averageScoreValue = new Label(String.format("%.2f%%", averageScore));
averageScoreValue.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #0078D7;");
averageScoreBox.getChildren().addAll(averageScoreLabel, averageScoreValue);

// Ajouter les boîtes au conteneur horizontal
generalStatsContainer.getChildren().addAll(totalStudentsBox, totalQuizzesBox, averageScoreBox);

// Ajouter le titre pour les meilleurs étudiants
Label topStudentsTitle = new Label("Top Performing Students");
topStudentsTitle.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-underline: true;");

// Table pour les meilleurs étudiants
TableView<Student> topStudentsTable = new TableView<>();
topStudentsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
topStudentsTable.setStyle("-fx-border-color: black; -fx-border-radius: 10;");

// Colonne : Name
TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
nameColumn.setStyle("-fx-alignment: CENTER;");

// Colonne : Average Score
TableColumn<Student, String> scoreColumn = new TableColumn<>("Average Score");
scoreColumn.setCellValueFactory(new PropertyValueFactory<>("averageScore"));
scoreColumn.setStyle("-fx-alignment: CENTER;");

// Colonne : Quizzes Taken
TableColumn<Student, Integer> quizzesColumn = new TableColumn<>("Quizzes Taken");
quizzesColumn.setCellValueFactory(new PropertyValueFactory<>("quizzesTaken"));
quizzesColumn.setStyle("-fx-alignment: CENTER;");

// Ajouter les colonnes à la table
topStudentsTable.getColumns().addAll(nameColumn, scoreColumn, quizzesColumn);

// Récupérer les listes nécessaires depuis la base de données
List<String> studentNames = QuizController.getStudentList(); // Noms des étudiants
Map<String, Integer> quizzesTakenMap = QuizController.getQuizzesTaken(); // Quizzes pris
Map<String, Double> averageScoreMap = QuizController.getAverageScoreByStudent(); // Moyenne des scores

// Créer une liste observable pour le tableau
ObservableList<Student> studentsData = FXCollections.observableArrayList();

// Remplir les données pour chaque étudiant
for (String studentName : studentNames) {
    int quizzesTaken = quizzesTakenMap.getOrDefault(studentName, 0);
    double averageScore1 = averageScoreMap.getOrDefault(studentName, 0.0);
    String formattedScore = String.format("%.2f%%", averageScore1); 
    studentsData.add(new Student(studentName, formattedScore, quizzesTaken));
}

studentsData.sort((student1, student2) -> {

    String score1 = student1.getAverageScore().replace("%", "").replace(",", ".");
    String score2 = student2.getAverageScore().replace("%", "").replace(",", ".");

    double numericScore1 = Double.parseDouble(score1);
    double numericScore2 = Double.parseDouble(score2);

   
    return Double.compare(numericScore2, numericScore1);
});


// Ajouter les données au TableView
topStudentsTable.setItems(studentsData);



// Ajouter les éléments au conteneur principal des statistiques
statisticsContent.getChildren().addAll(
    generalStatsContainer,
    topStudentsTitle,
    topStudentsTable
);

// Ajouter le conteneur principal au ScrollPane
ScrollPane statisticsScrollPane = new ScrollPane(statisticsContent);
statisticsScrollPane.setFitToWidth(true);
statisticsScrollPane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-radius: 15;");
statisticsTab.setContent(statisticsScrollPane);

FlowPane statisticsPane = new FlowPane();
statisticsPane.setPadding(new Insets(10));
statisticsPane.setHgap(10);
statisticsPane.setVgap(10);
        // Ajouter les onglets au TabPane
        tabPane.getTabs().addAll(quizzesTab, statisticsTab);

        // Créer un VBox pour contenir le bouton Create et le TabPane
        VBox quizContentContainer = new VBox(10);
        quizContentContainer.setPadding(new Insets(10)); 
        HBox createButtonContainer = new HBox();
        createButtonContainer.setAlignment(Pos.CENTER_RIGHT);
        createButtonContainer.getChildren().add(createButton);

        quizContentContainer.getChildren().addAll(createButtonContainer, tabPane);
        quizSection.getChildren().clear();
        quizSection.getChildren().add(quizContentContainer);

        // Récupérer les quiz depuis la base de données
        List<String> quizzes = QuizController.getQuizList(); 
        int quizNumber = 1;
        for (String quizTitle : quizzes) {
            // Set button text with "Quiz X" and title on separate lines
            String buttonText = "Quiz " + quizNumber + "\n" + quizTitle;
            Button tpButton = new Button(buttonText);
            tpButton.setPrefSize(120, 80); 
            tpButton.setStyle(statBoxStyle);
          
            tpButton.setOnAction(e -> {
                try {
                    String currentQuizTitle = quizTitle;
                    int quizId = QuizController.getQuizIdByTitle(currentQuizTitle);
                    if (quizId != -1) {
                        QuizDetails.showQuizDetails(quizId, main, scene);
                    } else {
                        System.out.println("Quiz ID invalide");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();  // Affiche l'exception s'il y en a une
                }
            });
            System.out.println("Adding button for: " + quizTitle);
            tpButtonsPane.getChildren().add(tpButton);
            
            quizNumber++; // Increment the quiz number
        }

        ScrollPane scrollPane = new ScrollPane(tpButtonsPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-radius: 15;");

        quizSection.getChildren().addAll(quizButtonsBar, quizScrollPane);

        // Right Section: Student List (25%)
        VBox studentSection = new VBox(20);
        studentSection.setPadding(new Insets(20));
        studentSection.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-radius: 15;");
        
        Label studentListTitle = new Label("Student List");
        studentListTitle.setFont(new Font("Arial", 18));
        studentListTitle.setStyle("-fx-font-weight: bold;");

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

       ScrollPane scrollPaneStudet = new ScrollPane(studentList);
       //scrollPaneStudet.setFitToWidth(true); 
       scrollPaneStudet.setStyle("-fx-background-color: white; -fx-border-color: white; -fx-border-radius: 5;-fx-background-radius: 5;"); 
       studentSection.getChildren().addAll(studentListTitle, studentList,scrollPaneStudet);


       // Add both sections to the center layout
       centerContent.getChildren().addAll(quizSection, studentSection);
       mainLayout.setCenter(centerContent);
       mainLayout.setStyle("-fx-pref-width: 100%; -fx-pref-height: 100%;");
      

  // Event handler to open Create Quiz interface
  createButton.setOnAction(e -> {
    
    CreateQuizInterface createQuizInterface = new CreateQuizInterface();
    createQuizInterface.createQuizInterface(main, scene);
});

   quizSection.prefWidthProperty().bind(scene.widthProperty().multiply(0.75)); // 75% de la largeur
studentSection.prefWidthProperty().bind(scene.widthProperty().multiply(0.25));

        main.getChildren().clear();
        main.getChildren().add(mainLayout);
    }
}
