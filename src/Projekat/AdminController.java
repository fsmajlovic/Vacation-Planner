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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    public VacationDAO dao;

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
        dao = VacationDAO.getInstance();
    }

    @FXML
    public void initialize(){
        
        model.fill();
        choiceSelectMonth.setItems(model.getMonths());
        selectedMonthLabel.textProperty().bind(model.getCurrentSimple());
        choiceSelectMonth.getSelectionModel().select("Select All");
        GreetingsLabel.setText("Welcome " + currentAdmin.getFirstName() + " " + currentAdmin.getLastName() + "!");
        for(Request r: requests){
            if(r.getApproved() == 0) {
                User u = findUserById(r.getUserId());
                User user = new User(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getUsername(), u.getPassword(), u.getAdminId(), u.daysleft,
                        r.getRequestId(), r.getFromDate(), r.getToDate());
                usersWithRequests.add(user);
            }
        }
        this.obs = FXCollections.observableArrayList(usersWithRequests);
        listOfRequests.setItems(obs);


        choiceSelectMonth.getSelectionModel().selectedItemProperty().addListener((obss, oldVal, newVal)-> {
            ArrayList<User> usersInMonth = new ArrayList<>();

            try {
                if(newVal.equals("January")){
                    usersInMonth = getUsersFromMonth("January");
                    obs = FXCollections.observableArrayList(usersInMonth);
                    listOfRequests.setItems(obs);
                }
                else if(newVal.equals("February")){
                    usersInMonth = getUsersFromMonth("February");
                    obs = FXCollections.observableArrayList(usersInMonth);
                    listOfRequests.setItems(obs);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


        });


//        choiceSelectMonth.getSelectionModel().selectedItemProperty().addListener((obss, oldVal, newVal)-> {
////            if(newVal.equals("January")){
////                try {
////                    JanuaryUsers = FXCollections.observableArrayList(getUsersFromMonth("January"));
////                    listOfRequests.setItems(JanuaryUsers);
////                } catch (ParseException e) {
////                    e.printStackTrace();
////                }
////            }
////            else if(newVal.equals("February")){
////                try {
////                    ObservableList<User> FebruaryUsers = FXCollections.observableArrayList(getUsersFromMonth("February"));
////                    listOfRequests.setItems(FebruaryUsers);
////                } catch (ParseException e) {
////                    e.printStackTrace();
////                }
////            }
////
////        });

        listOfRequests.getSelectionModel().selectedItemProperty().addListener((obss, oldVal, newVal)-> {
            if(newVal != null && oldVal != null) {
                fieldFirstName.setText(newVal.getFirstName());
                fieldLastName.setText(newVal.getLastName());
                fieldToDate.setText(newVal.getTo());
                fieldFromDate.setText(newVal.getFrom());
            }
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
            alert.setHeaderText("Employee not selected.");
            alert.setContentText("You need to select an employee.");
            alert.showAndWait();
        }
        int id = listOfRequests.getSelectionModel().getSelectedItem().getRequestsId();
        dao.approveRequest(id);
        listOfRequests.getItems().remove(listOfRequests.getSelectionModel().getSelectedIndex());
    }

    public void onActionDeny(ActionEvent actionEvent){
        if(listOfRequests.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Employee selection");
            alert.setHeaderText("Employee not selected.");
            alert.setContentText("You need to select an employee.");
            alert.showAndWait();
        }
        int id = listOfRequests.getSelectionModel().getSelectedItem().getRequestsId();
        dao.denyRequest(id);
        listOfRequests.getItems().remove(listOfRequests.getSelectionModel().getSelectedIndex());
        usersWithRequests.remove(listOfRequests.getSelectionModel().getSelectedItem());
    }

    public ArrayList<User> getUsersFromMonth(String month) throws ParseException {
        ArrayList<User> users = new ArrayList<>();
        for (Request r : requests) {
            if (r.getApproved() == 0 && getMonthFromRequest(r).equals(month)) {
                User u = findUserById(r.getUserId());
                User user = new User(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getUsername(), u.getPassword(), u.getAdminId(), u.daysleft,
                        r.getRequestId(), r.getFromDate(), r.getToDate());
                users.add(user);
            }
        }
        return users;
    }

    public String getMonthFromRequest(Request r){
        String s = r.getFromDate();
        s = s.replaceAll("\\d","");
        s = s.replaceAll("[^a-zA-Z0-9]", "");
        System.out.println(s);
        return s;
    }
}
