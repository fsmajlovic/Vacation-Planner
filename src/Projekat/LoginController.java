package Projekat;

import Projekat.Months.JanuaryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

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
    public ImageView backgroundImgView;


    @FXML
    public void initialize(){
        dao = VacationDAO.getInstance();
        users = dao.users();
        requests = dao.requests();
        Image image = new Image("Assets/background.gif");
        backgroundImgView.setImage(image);
    }

    public void LoginButtonAction(ActionEvent actionEvent) throws IOException, NoSuchAlgorithmException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String hashPassword = MD5Hash(password);
        boolean found = false;
        boolean admin = false;
        for(User u: users){
            if(u.getUsername().equals(username) && u.getPassword().equals(hashPassword)) {
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
                    stage.setTitle("Vacation Planner");
                    stage.getIcons().add(new Image("Assets/AppIcon.png"));
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
                boolean isApproved = dao.isApproved(current);
                Stage stage = new Stage();
                Parent root = null;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Months/January.fxml"));
                JanuaryController januaryController = new JanuaryController(current);
                loader.setController(januaryController);
                root = loader.load();
                stage.setTitle("Vacation Planner");
                stage.getIcons().add(new Image("Assets/AppIcon.png"));
                stage.setScene(new Scene(root, 800,500));
                stage.setResizable(false);
                stage.show();

                if(isApproved){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Request approved");
                    stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("Assets/AppIcon.png"));
                    alert.setHeaderText("Woohoo! Looks like some of your requests have been accepted!");
                    alert.setContentText("Check your requests status and enjoy your vacation!");
                    alert.showAndWait();
                }
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
        stage.setTitle("Vacation Planner");
        stage.getIcons().add(new Image("Assets/AppIcon.png"));
        stage.setScene(new Scene(root, 800,494));
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

    public String MD5Hash(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes(), 0, password.length());
        String myHash = new BigInteger(1, md.digest()).toString(16);
        return myHash;
    }
}
