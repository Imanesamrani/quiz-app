package mini_project.controller;




import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mini_project.model.TestCase;

public class CreateQuizInterfaceController {

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
        QuizController.updateTestCaseInDatabase(testCaseId, inputField.getText(), outputField.getText());
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
        QuizController.deleteTestCaseFromDatabase(testCaseId);                    
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