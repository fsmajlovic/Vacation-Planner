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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public ArrayList<Request> requests;
    public User current;
    //public ImageView ImageLogin;

    @FXML
    public void initialize(){
        dao = VacationDAO.getInstance();
        users = dao.users();
        requests = dao.requests();
//        ImageLogin.setImage(new Image(getClass().getClassLoader().getResource("loginpic1.png").toString(),
//                400,200,false,false));
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
                    Stage stage = new Stage();
                    Parent root = null;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminScreen.fxml"));
                    users = dao.users();
                    requests = dao.requests();
                    AdminController adminController = new AdminController(current, users, requests);
                    loader.setController(adminController);
                    root = loader.load();
                    stage.setTitle("Administator profile");
                    stage.setScene(new Scene(root, 800,500));
                    stage.setResizable(false);
                    stage.show();
                    usernameTextField.setText("");
                    passwordTextField.setText("");
                }
                else{
                    yourRNAdminLabel.setVisible(true);
                    return;
                }
            }
            else{
                Stage stage = new Stage();
                Parent root = null;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("January.fxml"));
                JanuaryController januaryController = new JanuaryController(current);
                loader.setController(januaryController);
                root = loader.load();
                stage.setTitle("January");
                stage.setScene(new Scene(root, 800,500));
                stage.setResizable(false);
                stage.show();

                stage.setOnHiding(data -> {
                    Request req = januaryController.getRequest();
                    if(req != null){
                        dao.addNewRequest(req);
                    }
                });
            }
            usernameTextField.setText("");
            passwordTextField.setText("");
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
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpScreen.fxml"));
        SignUpController signUpController = new SignUpController();
        loader.setController(signUpController);
        root = loader.load();
        stage.setTitle("SignUp");
        stage.setScene(new Scene(root, 368,493));
        stage.setResizable(false);
        Stage s = (Stage) passwordTextField.getScene().getWindow();
        s.close();
        stage.show();
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
