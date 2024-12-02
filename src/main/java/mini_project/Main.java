package mini_project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import mini_project.view.EntryPage;


public class Main extends Application{

    private StackPane content;

    @Override
    public void start(@SuppressWarnings("exports") Stage stage){
        // init content
        content = new StackPane();
        /*-------------------------------COMMUN STUFF BETWEEN ALL SCREENS ----------------------------------- */
        // app logo
        Image icon = new Image(getClass().getResourceAsStream("resources/CodeQuestLogo.png"));
        stage.getIcons().add(icon);
        Scene scene = new Scene(content, 800, 600, Color.AZURE);

        //  Create background image
        Image backgroundImage = new Image(getClass().getResourceAsStream("resources/CodeQuest_background.png"));
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        content.setBackground(new Background(background));

        stage.setScene(scene);
        stage.setTitle("Quiz App");  
        stage.setMaximized(true);      
        stage.show();
        EntryPage.EntryScreen(content, scene);


    }

    public static void main(String[] args) {
        launch(args);
    }
}
