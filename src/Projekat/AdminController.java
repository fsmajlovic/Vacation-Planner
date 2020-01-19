package Projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController{
    public ChoiceBox<String> choiceSelectMonth;
    public Label GreetingsLabel;
    public Label selectedMonthLabel;
    public MonthsModel model = new MonthsModel();
    public Button btnApprove;
    public Button btnDeny;
    public ListView listOfRewuests;
    public ListView listOfRequests;
    public TextField fieldFromDate;
    public TextField fieldFirstName;
    public TextField fieldToDate;
    public TextField fieldLastName;
    public User currentAdmin;

    public void LogoutAdminOnAction(ActionEvent actionEvent) throws IOException {
        Stage stg = (Stage) GreetingsLabel.getScene().getWindow();
        stg.close();
    }

    public AdminController (User currentAdmin){
        this.currentAdmin = currentAdmin;
    }

    @FXML
    public void initialize(){
        model.fill();
        choiceSelectMonth.setItems(model.getMonths());
        selectedMonthLabel.textProperty().bind(model.getCurrentSimple());
        choiceSelectMonth.getSelectionModel().select("Select All");
        GreetingsLabel.setText("Welcome " + currentAdmin.getFirstName() + " " + currentAdmin.getLastName() + "!");
    }

    public void changeMonth(ActionEvent actionEvent) {
        if(choiceSelectMonth.getValue().equals("Select All")){
            model.setCurrentMonth("All Months");
        }
        else {
            model.setCurrentMonth(choiceSelectMonth.getValue());
        }
    }


}
