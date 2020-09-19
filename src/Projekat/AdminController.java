package Projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;


public class AdminController{
    public ChoiceBox<String> choiceSelectMonth;
    public Label GreetingsLabel;
    public Label selectedMonthLabel;
    public MonthsModel model = new MonthsModel();
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
    public VacationDAO dao;
    public ImageView imgViewReport;

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

        Image image = new Image("Assets/report.png");
        imgViewReport.setImage(image);

        model.fill();
        choiceSelectMonth.setItems(model.getMonths());
        selectedMonthLabel.textProperty().bind(model.getCurrentSimple());
        choiceSelectMonth.getSelectionModel().select("Select All");
        GreetingsLabel.setText("Welcome " + currentAdmin.getFirstName() + " " + currentAdmin.getLastName() + "!");
        for(Request r: requests){
            if(r.getApproved() == 0) {
                User u = findUserById(r.getUserId());
                User user = new User(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getUsername(),
                        u.getPassword(), u.getAdminId(), u.daysleft, r.getRequestId(), r.getFromDate(), r.getToDate());
                usersWithRequests.add(user);
            }
        }
        this.obs = FXCollections.observableArrayList(usersWithRequests);
        listOfRequests.setItems(obs);

        choiceSelectMonth.getSelectionModel().selectedItemProperty().addListener((obss, oldVal, newVal)-> {
            ArrayList<User> usersInMonth = new ArrayList<>();

            try {
                if(newVal.equals("Select All")){
                    obs.removeAll();
                    obs = FXCollections.observableArrayList(usersWithRequests);
                    listOfRequests.setItems(obs);
                }
                switch (newVal) {
                    case "January":
                        obs.removeAll();
                        usersInMonth = getUsersFromMonth("January");
                        obs = FXCollections.observableArrayList(usersInMonth);
                        listOfRequests.setItems(obs);
                        break;
                    case "February":
                        obs.removeAll();
                        usersInMonth = getUsersFromMonth("February");
                        obs = FXCollections.observableArrayList(usersInMonth);
                        listOfRequests.setItems(obs);
                        break;
                    case "March":
                        obs.removeAll();
                        usersInMonth = getUsersFromMonth("March");
                        obs = FXCollections.observableArrayList(usersInMonth);
                        listOfRequests.setItems(obs);
                        break;
                    case "April":
                        obs.removeAll();
                        usersInMonth = getUsersFromMonth("April");
                        obs = FXCollections.observableArrayList(usersInMonth);
                        listOfRequests.setItems(obs);
                        break;
                    case "May":
                        obs.removeAll();
                        usersInMonth = getUsersFromMonth("May");
                        obs = FXCollections.observableArrayList(usersInMonth);
                        listOfRequests.setItems(obs);
                        break;
                    case "June":
                        obs.removeAll();
                        usersInMonth = getUsersFromMonth("June");
                        obs = FXCollections.observableArrayList(usersInMonth);
                        listOfRequests.setItems(obs);
                        break;
                    case "July":
                        obs.removeAll();
                        usersInMonth = getUsersFromMonth("July");
                        obs = FXCollections.observableArrayList(usersInMonth);
                        listOfRequests.setItems(obs);
                        break;
                    case "August":
                        obs.removeAll();
                        usersInMonth = getUsersFromMonth("August");
                        obs = FXCollections.observableArrayList(usersInMonth);
                        listOfRequests.setItems(obs);
                        break;
                    case "September":
                        obs.removeAll();
                        usersInMonth = getUsersFromMonth("September");
                        obs = FXCollections.observableArrayList(usersInMonth);
                        listOfRequests.setItems(obs);
                        break;
                    case "October":
                        obs.removeAll();
                        usersInMonth = getUsersFromMonth("October");
                        obs = FXCollections.observableArrayList(usersInMonth);
                        listOfRequests.setItems(obs);
                        break;
                    case "November":
                        obs.removeAll();
                        usersInMonth = getUsersFromMonth("November");
                        obs = FXCollections.observableArrayList(usersInMonth);
                        listOfRequests.setItems(obs);
                        break;
                    case "December":
                        obs.removeAll();
                        usersInMonth = getUsersFromMonth("December");
                        obs = FXCollections.observableArrayList(usersInMonth);
                        listOfRequests.setItems(obs);
                        break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });

        listOfRequests.getSelectionModel().selectedItemProperty().addListener((obss, oldVal, newVal)-> {
            if(newVal != null) {
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
            showAlert();
            return;
        }
        int id = listOfRequests.getSelectionModel().getSelectedItem().getRequestsId();
        dao.approveRequest(id);
        User u = listOfRequests.getSelectionModel().getSelectedItem();
        listOfRequests.getItems().remove(listOfRequests.getSelectionModel().getSelectedIndex());
        usersWithRequests.remove(u);
    }

    public void onActionDeny(ActionEvent actionEvent){
        if(listOfRequests.getSelectionModel().getSelectedItem() == null){
            showAlert();
            return;
        }
        int id = listOfRequests.getSelectionModel().getSelectedItem().getRequestsId();
        dao.denyRequest(id);
        User u = listOfRequests.getSelectionModel().getSelectedItem();
        listOfRequests.getItems().remove(listOfRequests.getSelectionModel().getSelectedIndex());
        usersWithRequests.remove(u);
    }

        public ArrayList<User> getUsersFromMonth(String month) throws ParseException {
        ArrayList<User> users = new ArrayList<>();
        for (User u : usersWithRequests) {
            if (getMonthFromStringDate(u.getFrom()).equals(month)) {
                users.add(u);
            }
        }
        return users;
    }

    public String getMonthFromStringDate(String r){
        String s = r;
        s = s.replaceAll("\\d","");
        s = s.replaceAll("[^a-zA-Z0-9]", "");
        return s;
    }

    public void showAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Employee selection");
        alert.setHeaderText("Employee not selected.");
        alert.setContentText("You need to select an employee.");
        alert.showAndWait();
    }

    public void rprtOnClicked(MouseEvent mouseEvent) {
        try {
            new PrintReport().showReport(dao.getMyConn());
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }
}
