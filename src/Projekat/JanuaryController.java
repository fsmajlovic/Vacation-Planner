package Projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JanuaryController {

    public ToggleButton btnOne;
    public TextField labelDaysLeft;
    public TextField  fromField;
    public TextField toField;
    public int daysLeft;
    public ArrayList<String> reserved = new ArrayList<>();
    public ArrayList<Integer> reservedNumbers = new ArrayList<>();
    public PreparedStatement getUserByUsernameStmt, updateDaysLeftStmt, addRequestStmt;
    public User current = new User();
    public String from, to;

    @FXML
    public void initialize(String usernameFromLogin){
        //Get user from databes by username
        Connection myConn;
        try {
            myConn = DriverManager.getConnection("jdbc:sqlite:VPdatabase.db");
            String getUser = "select id, first_name, last_name, email, username, password, admin_id, daysleft, " +
                    "requests_id from users where username = ?";
            getUserByUsernameStmt = myConn.prepareStatement(getUser);
            getUserByUsernameStmt.setString(1, usernameFromLogin);
            ResultSet rs = getUserByUsernameStmt.executeQuery();
            while (rs.next()) {
                current.setId(rs.getInt("id"));
                current.setFirst_name(rs.getString("first_name"));
                current.setLast_name(rs.getString("last_name"));
                current.setEmail(rs.getString("email"));
                current.setUsername(rs.getString("username"));
                current.setPassword(rs.getString("password"));
                current.setAdmin_id(rs.getInt("admin_id"));
                current.setDaysleft(rs.getInt("daysleft"));
                current.setRequests_id(rs.getInt("requests_id"));
            }
            System.out.println("user data: " + current.getUsername() + " " + current.getFirst_name() +
                    " " + current.getLast_name() + " " + current.daysleft);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        daysLeft = current.getDaysleft();
        labelDaysLeft.setText(String.valueOf(daysLeft));
    }

    public void LogoutAction(ActionEvent actionEvent) {
        Parent AdminParent = null;
        try {
            AdminParent = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene AdminScene = new Scene(AdminParent, 500, 300);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(AdminScene);
        window.show();
    }

    public void btnPressed(ActionEvent actionEvent) {
         ToggleButton tgl = (ToggleButton) actionEvent.getSource();

        if(daysLeft > 0 && tgl.getStyleClass().contains("notPressed")){
            //Updating the ArrayList
            if (reserved.isEmpty()) {
                reserved.add((tgl.getText()));
            }
            else if(reserved.stream().anyMatch(x-> {
                return x == (tgl.getText());
            })){
                //Do not add a duplicate
            }
            else{
                reserved.add((tgl.getText()));
            }
            //Updating DaysLeft
            daysLeft--;
            labelDaysLeft.setText(String.valueOf(daysLeft));
            ((ToggleButton) actionEvent.getSource()).getStyleClass().clear();
            ((ToggleButton) actionEvent.getSource()).getStyleClass().add("isPressed");
        }
        else if(daysLeft > 0  && daysLeft < 10 && tgl.getStyleClass().contains("isPressed")){
            //Updating the ArrayList
            reserved.remove((tgl.getText()));
            //Updating DaysLeft
            daysLeft++;
            labelDaysLeft.setText(String.valueOf(daysLeft));
            ((ToggleButton) actionEvent.getSource()).getStyleClass().clear();
            ((ToggleButton) actionEvent.getSource()).getStyleClass().add("notPressed");
        }
        else if(daysLeft == 0 && tgl.getStyleClass().contains("isPressed")){
            //Updating the ArrayList
            reserved.remove((tgl.getText()));
            //Updating DaysLeft
            daysLeft++;
            labelDaysLeft.setText(String.valueOf(daysLeft));
            ((ToggleButton) actionEvent.getSource()).getStyleClass().clear();
            ((ToggleButton) actionEvent.getSource()).getStyleClass().add("notPressed");
        }

        reservedNumbers.clear();
        for(String a: reserved){
            reservedNumbers.add(Integer.parseInt(a));
        }
        Collections.sort(reservedNumbers);
        System.out.println("Array: ");
        for(Integer a: reservedNumbers){
            System.out.println(a);
        }
        if(!reservedNumbers.isEmpty()) {
            Integer min = Collections.min(reservedNumbers);
            Integer max = Collections.max(reservedNumbers);

            LocalDate fromDate = LocalDate.of(2020, Month.JANUARY, min);
            LocalDate toDate = LocalDate.of(2020, Month.JANUARY, max);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
            from = fromDate.format(formatter);
            to = toDate.format(formatter);

            if (!reservedNumbers.isEmpty()) {

                fromField.setText(from.toString());
                fromField.getStyleClass().clear();
                fromField.getStyleClass().add("fieldContainsNumbers");
                if (reservedNumbers.size() != 0) {
                    toField.setText(to.toString());
                    toField.getStyleClass().clear();
                    toField.getStyleClass().add("fieldContainsNumbers");
                } else if (reservedNumbers.size() == 0) {
                    toField.setText("");
                    toField.getStyleClass().clear();
                    toField.getStyleClass().add("fieldDoesNotContainNumbers");
                }
            } else {
                fromField.setText("");
                fromField.getStyleClass().clear();
                fromField.getStyleClass().add("fieldDoesNotContainNumbers");
                toField.setText("");
                toField.clear();
                toField.getStyleClass().add("fieldDoesNotContainNumbers");
            }
        }
        else{
            fromField.setText("");
            toField.setText("");
        }

    }

    public void nxtMonth(MouseEvent mouseEvent) throws IOException {
        Parent MonthParent = FXMLLoader.load(getClass().getResource("February" + ".fxml"));
        Scene MonthScene = new Scene(MonthParent, 800, 500);
        Stage window = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(MonthScene);
        window.show();
    }

    public void pvsMonth(MouseEvent mouseEvent) throws IOException {
        Parent MonthParent = FXMLLoader.load(getClass().getResource("December" + ".fxml"));
        Scene MonthScene = new Scene(MonthParent, 800, 500);
        Stage window = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(MonthScene);
        window.show();
    }

    public void sendRequestOnAction(ActionEvent actionEvent) {
        current.setDaysleft(daysLeft);
        Connection myConn;
        try {
            myConn = DriverManager.getConnection("jdbc:sqlite:VPdatabase.db");
            String updateDaysLeft = "update users " +
                             "set daysleft = ?" +
                             "where username = ?;";
            updateDaysLeftStmt = myConn.prepareStatement(updateDaysLeft);
            updateDaysLeftStmt.setInt(1, daysLeft);
            updateDaysLeftStmt.setString(2, current.getUsername());
            updateDaysLeftStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String from_date = from;
        String to_date = to;

//        try {
//            myConn = DriverManager.getConnection("jdbc:sqlite:VPdatabase.db");
//            String addRequest = "update requests " +
//                    "set from_date = ?, to_date = ?, user_id = ?" +
//                    "where username = ?;";
//            addRequestStmt = myConn.prepareStatement(addRequest);
//            addRequestStmt.setInt(1, daysLeft);
//            addRequestStmt.setString(2, current.getUsername());
//            addRequestStmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }




    }
}
