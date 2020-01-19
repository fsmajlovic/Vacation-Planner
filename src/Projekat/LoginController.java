package Projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class LoginController {

    public VacationDAO dao;
    public CheckBox AdminCheckBox;
    public boolean CheckBox = false;
    public Connection myConn;
    public PreparedStatement getUsersStmt;
    public Label invalidLabel;
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public Label yourRNAdminLabel;
    public ArrayList<User> users;
    public User current;

    @FXML
    public void initialize(){
        dao = VacationDAO.getInstance();
        users = dao.users();
    }

    public void LoginButtonAction(ActionEvent actionEvent) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        boolean found = false;
        boolean admin = false;
        for(User u: users){
            if(u.getUsername().equals(username) && u.getPassword().equals(password)) {
                found = true;
                current = u;
                if(u.getAdminId() == 1)
                    admin = true;
            }
        }

        if(found){
            invalidLabel.setVisible(false);
            if(CheckBox){
                if(admin){
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("AdminScreen.fxml"));
                    Parent viewParent = loader.load();

                    Scene viewScene = new Scene(viewParent);

                    AdminController ctrl = loader.getController();
                    ctrl.initData();

                    ctrl.initGreetingsMsg(current.getFirstName(), current.getLastName());

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
//                        String month = getMonthName();
//                        Parent MonthParent = FXMLLoader.load(getClass().getResource(month + ".fxml"));
//                        Scene MonthScene = new Scene(MonthParent, 800, 500);
//                        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
//                        window.setScene(MonthScene);
//                        window.show();//
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("January.fxml"));
                Parent viewParent = loader.load();

                Scene viewScene = new Scene(viewParent);

                JanuaryController ctrl = new JanuaryController(current);
                ctrl.initialize(usernameTextField.getText());

                Stage window_2 = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                window_2.setScene(viewScene);
                window_2.show();
            }
        }
        else{
            invalidLabel.setVisible(true);
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
        Parent MonthParent = FXMLLoader.load(getClass().getResource("SignUpScreen.fxml"));
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
