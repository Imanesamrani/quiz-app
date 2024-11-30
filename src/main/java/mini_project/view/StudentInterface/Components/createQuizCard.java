package mini_project.view.StudentInterface.Components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class createQuizCard {

    // Static method to create a Quiz Card
    public static VBox CreateQuizCard(String title, String description) {
        VBox card = new VBox();
        card.setPadding(new Insets(10));
        card.setSpacing(5);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(200, 150);
        card.setStyle("-fx-background-color: #E0E0E0; -fx-border-color: #B0B0B0; -fx-border-radius: 10; -fx-background-radius: 5;");

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", 16));
        titleLabel.setStyle("-fx-font-weight: bold;");

        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("Arial", 12));
        descLabel.setTextFill(Color.GRAY);

        card.getChildren().addAll(titleLabel, descLabel);

        return card; // Return the constructed VBox
    }
}
