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
    public User currentAdmin, currentEmployee;
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
        this.obs = FXCollections.observableArrayList(usersWithRequests);
    }

    @FXML
    public void initialize(){
        model.fill();
        choiceSelectMonth.setItems(model.getMonths());
        selectedMonthLabel.textProperty().bind(model.getCurrentSimple());
        choiceSelectMonth.getSelectionModel().select("Select All");
        GreetingsLabel.setText("Welcome " + currentAdmin.getFirstName() + " " + currentAdmin.getLastName() + "!");
        for(Request r: requests){
            User u = findUserById(r.getUserId());
            User user = new User(u.getId(), u.getFirstName(), u.getLastName(),  u.getEmail(), u.getUsername(), u.getPassword(), u.getAdminId(), u.daysleft,
                    r.getRequestId(), r.getFromDate(), r.getToDate());
            usersWithRequests.add(user);
        }
        this.obs = FXCollections.observableArrayList(usersWithRequests);

        for(User u: usersWithRequests){
            System.out.println(u.getUsername());
        }

        listOfRequests.setItems(obs);
        listOfRequests.getSelectionModel().selectedItemProperty().addListener((obss, oldVal, newVal)-> {
            fieldFirstName.setText(newVal.getFirstName());
            fieldLastName.setText(newVal.getLastName());
            fieldToDate.setText(newVal.getTo());
            fieldFromDate.setText(newVal.getFrom());
        });

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

    public void onActionApprove(ActionEvent actionEvent){
        if(listOfRequests.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Employee selection");
            alert.setHeaderText("Informacija o passwordu ispod!");
            alert.setContentText("You need to select an user.");
            alert.showAndWait();
        }
    }

}
