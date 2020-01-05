package Projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController{
    public ChoiceBox<String> choiceSelectMonth;
    public Label GreetingsLabel;
    public Label selectedMonthLabel;
    public MonthsModel model = new MonthsModel();
    public void LogoutAdminOnAction(ActionEvent actionEvent) throws IOException {
        Parent AdminParent = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        Scene AdminScene = new Scene(AdminParent, 500, 300);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(AdminScene);
        window.show();
    }

    public void initData() {
        model.fill();
        choiceSelectMonth.setItems(model.getMonths());
        selectedMonthLabel.textProperty().bind(model.getCurrentSimple());
    }

    public void initGreetingsMsg(String first_name, String last_name){
        GreetingsLabel.setText("Welcome " + first_name + " " + last_name + "!");
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
