package Projekat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
            Stage window;
            Scene scene1, scene2;
            @Override
        public void start(Stage primaryStage) throws Exception{
            Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
            primaryStage.setTitle("Vacation Planner");
            primaryStage.getIcons().add(new Image("AppIcon.png"));
            primaryStage.setScene(new Scene(root, 500, 300));
            primaryStage.setResizable(false);
            primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
