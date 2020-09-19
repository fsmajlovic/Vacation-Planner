package Projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
import java.util.regex.Pattern;

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
    public VacationDAO dao;
    public ImageView backgroundImgView;


    SignUpController(){
        dao = VacationDAO.getInstance();
    }

    @FXML
    public void intialize(){
        Image image = new Image("Assets/background2.gif");
        backgroundImgView.setImage(image);
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent AdminParent = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        Scene AdminScene = new Scene(AdminParent, 800, 494);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(AdminScene);
        window.show();
    }

    public void signUpOnAction(ActionEvent actionEvent) throws NoSuchAlgorithmException {
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
        listUsers = dao.users();
        for(User u: listUsers){
            if(u.getEmail().equals(email)) emailInUse = true;
            if(u.getUsername().equals(username)) userNameInUse = true;
        }
        //Setting alert labels visible acording to the input
        if(!firstNameWrong || !lastNameWrong || !emailWrong || emailInUse || usernameWrong || userNameInUse || passwordWrong){
            if(!firstNameWrong) invalidFirstNameLabel.setVisible(true);
            if(!lastNameWrong) invalidLastNameLabel.setVisible(true);
            if(emailInUse) emailInUseLabel.setVisible(true);
            if(userNameInUse){
                usernameInUseLabel.setVisible(true);
            }
            else if(usernameWrong){
                invalidUsernameLabel.setVisible(true);
            }
            if(!emailWrong) incorrectEmailLabel.setVisible(true);
            if(passwordWrong) incorrectPasswordLabel.setVisible(true);
            return;
        }

        String hashPass = MD5Hash(password);
        User u = new User(-1, first_name, last_name, email, username, hashPass,0,10,0);
        dao.addUser(u);

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
        boolean valid = name.matches("(?i)[a-z]([- ',.a-z]{0,23}[a-z])?");
        return valid;
    }

    public boolean isValidMail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public boolean isValidUser(String tekst){
        if(tekst.isEmpty())
            return true;
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

    public String MD5Hash(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes(), 0, password.length());
        String myHash = new BigInteger(1, md.digest()).toString(16);
        return myHash;
    }

}
