package Projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class AdminController{
    public ChoiceBox<String> choiceSelectMonth;
    public Label GreetingsLabel;
    public Label selectedMonthLabel;
    public MonthsModel model = new MonthsModel();
    public Button btnApprove;
    public Button btnDeny;
    public ListView<User> listOfRequests;
    public TextField fieldFromDate;
    public TextField fieldFirstName;
    public TextField fieldToDate;
    public TextField fieldLastName;
    public User currentAdmin;
    public ArrayList<User> users;
    public ArrayList<Request> requests;
    public ArrayList<User> usersWithRequests;
    public ObservableList<User> obs;

    public void LogoutAdminOnAction(ActionEvent actionEvent) throws IOException {
        Stage stg = (Stage) GreetingsLabel.getScene().getWindow();
        stg.close();
    }

    public AdminController (User currentAdmin, ArrayList<User> users, ArrayList<Request> requests){
        this.currentAdmin = currentAdmin;
        this.users = users;
        this.requests = requests;
        this.usersWithRequests = new ArrayList<>();
        for(Request r: requests){
                User u = new User();
                u = findUserById(r.getUserId());
                usersWithRequests.add(u);
        }
        this.obs = FXCollections.observableArrayList(usersWithRequests);
    }

    @FXML
    public void initialize(){
        model.fill();
        choiceSelectMonth.setItems(model.getMonths());
        selectedMonthLabel.textProperty().bind(model.getCurrentSimple());
        choiceSelectMonth.getSelectionModel().select("Select All");
        GreetingsLabel.setText("Welcome " + currentAdmin.getFirstName() + " " + currentAdmin.getLastName() + "!");



        for(User u: usersWithRequests){
            System.out.println(u.getUsername());
        }

        listOfRequests.setItems(obs);

    }

    public void changeMonth(ActionEvent actionEvent) {
        if(choiceSelectMonth.getValue().equals("Select All")){
            model.setCurrentMonth("All Months");
        }
        else {
            model.setCurrentMonth(choiceSelectMonth.getValue());
        }
    }

    public User findUserById(int id){
        for(User u: users){
            if(u.getId() == id)
                return u;
        }
        return null;
    }

}
