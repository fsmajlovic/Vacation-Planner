package Projekat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
            Stage window;
            Scene scene1, scene2;
            @Override
        public void start(Stage primaryStage) throws Exception{
            Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
            primaryStage.setTitle("Vacation Planner");
            primaryStage.getIcons().add(new Image("Assets/AppIcon.png"));
            primaryStage.setScene(new Scene(root, 800, 494));
            primaryStage.setResizable(false);
            primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
