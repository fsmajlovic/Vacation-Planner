package Projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
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
    public int daysLeft = 10;
    public ArrayList<String> reserved = new ArrayList<>();
    public ArrayList<Integer> reservedNumbers = new ArrayList<>();


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
        //System.out.println(tgl.getText());

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
        Integer min = Collections.min(reservedNumbers);
        Integer max = Collections.max(reservedNumbers);

        LocalDate fromDate = LocalDate.of(2020, Month.JANUARY, min);
        LocalDate toDate = LocalDate.of(2020, Month.JANUARY, max);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String from = fromDate.format(formatter);
        String to = toDate.format(formatter);

        if(!reservedNumbers.isEmpty()) {

            fromField.setText(from.toString());
            fromField.getStyleClass().clear();
            fromField.getStyleClass().add("fieldContainsNumbers");
            if(reservedNumbers.size() != 1 && reservedNumbers.size() != 0) {

                toField.setText(to.toString());
                toField.getStyleClass().clear();
                toField.getStyleClass().add("fieldContainsNumbers");
            }
            else if(reservedNumbers.size() == 0){
                toField.setText("");
                toField.getStyleClass().clear();
                toField.getStyleClass().add("fieldDoesNotContainNumbers");
            }
        }
        else{
            fromField.setText("");
            fromField.getStyleClass().clear();
            fromField.getStyleClass().add("fieldDoesNotContainNumbers");
            toField.setText("");
            toField.clear();
            toField.getStyleClass().add("fieldDoesNotContainNumbers");
        }
    }
}
