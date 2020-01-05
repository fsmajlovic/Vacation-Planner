package Projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class SignUpController {
    public TextField firstNameTF;
    public TextField lastNameTF;
    public TextField emailTF;
    public TextField usernameTF;
    public PasswordField passwordTF;
    public PreparedStatement getNextIdStmt, addIntoDatabaseStmt;
    public UsersModel model = new UsersModel();
    public ArrayList<User> listUsers = new ArrayList<>();
    public Label usernameInUseLabel;
    public Label emailInUseLabel;
    public Label invalidLastNameLabel;
    public Label invalidFirstNameLabel;
    public Label incorrectEmailLabel;
    public Label invalidUsernameLabel;
    public Label incorrectPasswordLabel;

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent AdminParent = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        Scene AdminScene = new Scene(AdminParent, 500, 300);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(AdminScene);
        window.show();
    }

    public void signUpOnAction(ActionEvent actionEvent) {
        //Getting information from input
        String first_name = firstNameTF.getText();
        String last_name = lastNameTF.getText();
        String email = emailTF.getText();
        String username = usernameTF.getText();
        String password = passwordTF.getText();
        boolean firstNameWrong = false, lastNameWrong = false, emailWrong = false, emailInUse = false, usernameWrong = false, userNameInUse = false, passwordWrong = false;
        //Check if first_name is valid
        firstNameWrong = isValidName(first_name);
        //Check if last_name is valid
        lastNameWrong = isValidName(last_name);
        //Check if email is in the right format
        emailWrong = isValidMail(email);
        //Check if username in the right format
        usernameWrong = isValidUser(username);
        //Check if password is at least 4 letters long
        if(password.length() < 4) passwordWrong = true;
        //Check if user already exist with model so we don't have to create another statement
        model.fill();
        listUsers = model.getListUsers();
        for(User u: listUsers){
            if(u.getEmail().equals(email)) emailInUse = true;
            if(u.getUsername().equals(username)) userNameInUse = true;
        }
        //Setting alert labels visible acording to the input
        if(firstNameWrong || lastNameWrong || emailWrong || emailInUse || usernameWrong || userNameInUse || passwordWrong){
            if(firstNameWrong) invalidFirstNameLabel.setVisible(true);
            if(lastNameWrong) invalidLastNameLabel.setVisible(true);
            if(emailInUse) emailInUseLabel.setVisible(true);
            if(userNameInUse) usernameInUseLabel.setVisible(true);
            if(emailWrong) incorrectEmailLabel.setVisible(true);
            if(usernameWrong) invalidUsernameLabel.setVisible(true);
            if(passwordWrong) incorrectPasswordLabel.setVisible(true);
            return;
        }
        
        Connection myConn = null;
        int nextId = -1;
        try {
            myConn = DriverManager.getConnection("jdbc:sqlite:VPdatabase.db");
            String sqlprep = "insert into users(first_name, last_name, email, username," +
                    " password, admin_id, daysleft, requests_id)values (?, ?, ?, ?, ?, ?, ?, ?);";
            addIntoDatabaseStmt = myConn.prepareStatement(sqlprep);
            addIntoDatabaseStmt.setString(1, first_name);
            addIntoDatabaseStmt.setString(2, last_name);
            addIntoDatabaseStmt.setString(3, email);
            addIntoDatabaseStmt.setString(4, username);
            addIntoDatabaseStmt.setString(5, password);
            addIntoDatabaseStmt.setInt(6, 0);
            addIntoDatabaseStmt.setInt(7, 10);
            addIntoDatabaseStmt.setInt(8, 0);
            addIntoDatabaseStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        firstNameTF.setText("");
        lastNameTF.setText("");
        emailTF.setText("");
        usernameTF.setText("");
        passwordTF.setText("");

    }

    public void usernameOnMouseClicked(MouseEvent mouseEvent) {
        if(usernameInUseLabel.isVisible()){
            usernameTF.setText("");
            usernameInUseLabel.setVisible(false);
        }
        if(invalidUsernameLabel.isVisible()){
            usernameTF.setText("");
            invalidUsernameLabel.setVisible(false);
        }
    }

    public void emailOnMouseClicked(MouseEvent mouseEvent) {
        if(emailInUseLabel.isVisible()){
            emailTF.setText("");
            emailInUseLabel.setVisible(false);
        }
        if(incorrectEmailLabel.isVisible()){
            emailTF.setText("");
            incorrectEmailLabel.setVisible(false);
        }
    }

    public boolean isValidName(String name){
        if(name.length() < 1)
            return true;
        for(int i = 0; i < name.length(); i++){
            if(!Character.isLetter(name.charAt(i)) && name.charAt(i) != ' ' && name.charAt(i) != '-'){
                return true;
            }
        }
        return false;
    }

    public boolean isValidMail(String email){
        if(!email.contains("@") || email.charAt(0) == '@' || email.charAt(email.length()-1) == '@') return true;
        return false;
    }

    public boolean isValidUser(String tekst){
        if(tekst.length() > 16)
            return true;
        for(int i = 0; i < tekst.length(); i++){
            if(!Character.isLetter(tekst.charAt(i)) && tekst.charAt(i) != '_'
                    && !Character.isDigit(tekst.charAt(i))){
                return true;
            }
        }
        return false;
    }

    public void passwordOnMouseClicked(MouseEvent mouseEvent) {
        if(incorrectPasswordLabel.isVisible()){
            incorrectPasswordLabel.setVisible(false);
            passwordTF.setText("");
        }
    }

    public void firstNameOnMouseClicked(MouseEvent mouseEvent) {
        if(invalidFirstNameLabel.isVisible()){
            firstNameTF.setText("");
            invalidFirstNameLabel.setVisible(false);
        }
    }


    public void lastNameOnMouseClicked(MouseEvent mouseEvent) {
        if(invalidLastNameLabel.isVisible()){
            lastNameTF.setText("");
            invalidLastNameLabel.setVisible(false);
        }
    }
}
