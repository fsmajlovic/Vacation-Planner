package Projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

public class LoginController {

    public CheckBox AdminCheckBox;
    public boolean CheckBox = false;
    public PasswordField password;
    public TextField username;

    public void LoginButtonAction(ActionEvent actionEvent) throws IOException {
        if(CheckBox){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AdminScreen.fxml"));
            Parent viewParent = loader.load();

            Scene viewScene = new Scene(viewParent);

            AdminController ctrl = loader.getController();
            ctrl.initData();
            ctrl.initGreetingsMsg(username.getText());

            Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(viewScene);
            window.show();

        }
        else{
            String month = getMonthName();

            Parent MonthParent = FXMLLoader.load(getClass().getResource(month + ".fxml"));
            Scene MonthScene = new Scene(MonthParent, 800, 500);
            Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(MonthScene);
            window.show();
        }

    }

    public void AdminCheckBoxOnAction(ActionEvent actionEvent) {
        if(CheckBox == false)
            CheckBox = true;
        else if(CheckBox == true)
            CheckBox = false;
    }

    public String getMonthName(){
        String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};
        Calendar cal = Calendar.getInstance();
        return monthName[cal.get(Calendar.MONTH)];
    }


    public void SignUpOnAction(ActionEvent actionEvent) throws IOException {
        Parent MonthParent = FXMLLoader.load(getClass().getResource("SignUpScreen" + ".fxml"));
        Scene MonthScene = new Scene(MonthParent, 368, 493);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(MonthScene);
        window.show();
    }
}
