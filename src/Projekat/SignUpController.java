package Projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class SignUpController {
    public TextField firstNameTF;
    public TextField lastNameTF;
    public TextField emailTF;
    public TextField usernameTF;
    public PasswordField passwordTF;
    public PreparedStatement getNextIdStmt, addIntoDatabaseStmt;
    public Label Labeltest;

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent AdminParent = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        Scene AdminScene = new Scene(AdminParent, 500, 300);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(AdminScene);
        window.show();
    }

    public void signUpOnAction(ActionEvent actionEvent) {
        //Gettin information from input

        String first_name = firstNameTF.getText();
        String last_name = lastNameTF.getText();
        String email = emailTF.getText();
        String username = usernameTF.getText();
        String password = passwordTF.getText();

        Connection myConn = null;
        int nextId = -1;
        try {
            myConn = DriverManager.getConnection("jdbc:sqlite:VPdatabase.db");
//            getNextIdStmt = myConn.prepareStatement("select MAX(id)+1 from users");
//            ResultSet rs = getNextIdStmt.executeQuery();
//            if(rs.next()){
//                nextId = rs.getInt(1);
//            }
            String sqlprep = "insert into users(first_name, last_name, email, username," +
                    " password, admin_id, daysleft, requests_id)values (?, ?, ?, ?, ?, ?, ?, ?);";
            addIntoDatabaseStmt = myConn.prepareStatement(sqlprep);
            //addIntoDatabaseStmt.setInt(1, nextId);
            addIntoDatabaseStmt.setString(1, first_name);
            addIntoDatabaseStmt.setString(2, last_name);
            addIntoDatabaseStmt.setString(3, email);
            addIntoDatabaseStmt.setString(4, username);
            addIntoDatabaseStmt.setString(5, password);
            addIntoDatabaseStmt.setInt(6, 0);
            addIntoDatabaseStmt.setInt(7, 10);
            addIntoDatabaseStmt.setInt(8, 11);
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


}
