package Projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
import java.util.Objects;

public class LoginController {

    public CheckBox AdminCheckBox;
    public boolean CheckBox = false;
    public Connection myConn;
    public PreparedStatement getUsersStmt;
    public Label invalidLabel;
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public Label yourRNAdminLabel;


    public void LoginButtonAction(ActionEvent actionEvent) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        try {
            myConn = DriverManager.getConnection("jdbc:sqlite:VPdatabase.db");
            getUsersStmt = myConn.prepareStatement("select * from users");
            ResultSet rs = getUsersStmt.executeQuery();
            String usernamedb, passworddb;
            while(rs.next()){
                usernamedb = rs.getString("username");
                passworddb = rs.getString("password");
                int admin_ID = rs.getInt("admin_id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                if(usernamedb.equals(username) && passworddb.equals(password)){
                    invalidLabel.setVisible(false);
                    if(CheckBox){
                        if(admin_ID == 1){
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("AdminScreen.fxml"));
                            Parent viewParent = loader.load();

                            Scene viewScene = new Scene(viewParent);

                            AdminController ctrl = loader.getController();
                            ctrl.initData();

                            ctrl.initGreetingsMsg(first_name, last_name);

                            Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                            window.setScene(viewScene);
                            window.show();
                        }
                        else{
                            yourRNAdminLabel.setVisible(true);
                            return;
                        }
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
                else{
                    invalidLabel.setVisible(true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void AdminCheckBoxOnAction(ActionEvent actionEvent) {
        if(CheckBox == false)
            CheckBox = true;
        else if(CheckBox == true)
            CheckBox = false;
        yourRNAdminLabel.setVisible(false);
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
        invalidLabel.setVisible(false);
        yourRNAdminLabel.setVisible(false);
        Parent MonthParent = FXMLLoader.load(getClass().getResource("SignUpScreen" + ".fxml"));
        Scene MonthScene = new Scene(MonthParent, 368, 493);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(MonthScene);
        window.show();
    }

    public void usernameOnMouseClicked(MouseEvent mouseEvent) {
        if(invalidLabel.isVisible()){
            usernameTextField.setText("");
            passwordTextField.setText("");
            invalidLabel.setVisible(false);
        }
        yourRNAdminLabel.setVisible(false);
    }

    public void passwordOnMouseClicked(MouseEvent mouseEvent) {
        if(invalidLabel.isVisible()){
            usernameTextField.setText("");
            passwordTextField.setText("");
            invalidLabel.setVisible(false);
        }
        yourRNAdminLabel.setVisible(false);
    }
}
