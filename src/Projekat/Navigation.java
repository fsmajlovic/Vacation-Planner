package Projekat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation implements MonthInterface {

    @Override
    public void nextMonth(String month, TextField toField, User current){
        try {
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(month));
        FebruaryController februaryController = new FebruaryController(current);
        loader.setController(februaryController);
        root = loader.load();
        stage.setTitle("Vacation Planner");
        stage.getIcons().add(new Image("Assets/AppIcon.png"));
        stage.setScene(new Scene(root, 800, 500));
        stage.setResizable(false);
        stage.show();
        Stage stg = (Stage) toField.getScene().getWindow();
        stg.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void previousMonth(String month, TextField toField, User current) {
        try {
            Stage stage = new Stage();
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(month));
            FebruaryController februaryController = new FebruaryController(current);
            loader.setController(februaryController);
            root = loader.load();
            stage.setTitle("Vacation Planner");
            stage.getIcons().add(new Image("Assets/AppIcon.png"));
            stage.setScene(new Scene(root, 800, 500));
            stage.setResizable(false);
            stage.show();
            Stage stg = (Stage) toField.getScene().getWindow();
            stg.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void statusInfo(User current) {
        try {
            Stage stage = new Stage();
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RequestsStatus.fxml"));
            RequestsStatusController requestsStatusController = new RequestsStatusController(current.getId());
            loader.setController(requestsStatusController);
            root = loader.load();
            stage.getIcons().add(new Image("Assets/AppIcon.png"));
            stage.setTitle("Status");
            stage.setScene(new Scene(root, 800,500));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
