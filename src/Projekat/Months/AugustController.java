package Projekat.Months;

import Projekat.Request;
import Projekat.User;
import Projekat.VacationDAO;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class AugustController extends MonthController {

    public TextField DaysLeftTextField = new TextField(), fromField, toField;
    public String from, to;
    public int daysLeft, maxDays;
    public ArrayList<String> reserved = new ArrayList<>();
    public ArrayList<Integer> reservedNumbers = new ArrayList<>();
    public User current;
    public Request req;
    public Label labelRequestOk;
    public VacationDAO dao;
    public MonthInterface monthInterface;

    public AugustController(User current){
        this.current = current;
        dao = VacationDAO.getInstance();
        daysLeft = dao.getDaysLeftByUsername(current.getUsername());
        maxDays = daysLeft;
        monthInterface = new Navigation();
    }

    @FXML
    public void initialize(){
        DaysLeftTextField.setText(String.valueOf(daysLeft));
    }

    public Request getRequest(){
        return req;
    }

    public void nxtMonth(MouseEvent mouseEvent) throws IOException {
        FebruaryController februaryController = new FebruaryController(current);
        monthInterface.nextMonth("February.fxml", toField, current,februaryController);
    }

    public void pvsMonth(MouseEvent mouseEvent) throws IOException {
        JanuaryController januaryController = new JanuaryController(current);
        monthInterface.previousMonth("January.fxml", toField, current, januaryController);
    }

    public void StatusOnAction(ActionEvent actionEvent) throws IOException {
        monthInterface.statusInfo(current);
    }

    public void LogoutAction(ActionEvent actionEvent) {
        Stage st = (Stage) toField.getScene().getWindow();
        st.close();
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
            DaysLeftTextField.setText(String.valueOf(daysLeft));
            ((ToggleButton) actionEvent.getSource()).getStyleClass().clear();
            ((ToggleButton) actionEvent.getSource()).getStyleClass().add("isPressed");
        }
        else if(daysLeft > 0  && daysLeft < 10 && tgl.getStyleClass().contains("isPressed")){
            if(Integer.parseInt(tgl.getText()) != Integer.parseInt((Collections.max(reserved))) &&
                    Integer.parseInt(tgl.getText()) != Integer.parseInt((Collections.min(reserved)))){
                return;
            }
            if(maxDays > daysLeft) {
                //Updating the ArrayList
                reserved.remove((tgl.getText()));
                //Updating DaysLeft
                daysLeft++;
                DaysLeftTextField.setText(String.valueOf(daysLeft));
                ((ToggleButton) actionEvent.getSource()).getStyleClass().clear();
                ((ToggleButton) actionEvent.getSource()).getStyleClass().add("notPressed");
            }
        }
        else if(daysLeft == 0 && tgl.getStyleClass().contains("isPressed")){
            if(Integer.parseInt(tgl.getText()) != Integer.parseInt((Collections.max(reserved))) &&
                    Integer.parseInt(tgl.getText()) != Integer.parseInt((Collections.min(reserved)))) {
                return;
            }
            //Updating the ArrayList
            reserved.remove((tgl.getText()));
            //Updating DaysLeft
            daysLeft++;
            DaysLeftTextField.setText(String.valueOf(daysLeft));
            ((ToggleButton) actionEvent.getSource()).getStyleClass().clear();
            ((ToggleButton) actionEvent.getSource()).getStyleClass().add("notPressed");
        }

        reservedNumbers.clear();
        for(String a: reserved){
            reservedNumbers.add(Integer.parseInt(a));
        }
        Collections.sort(reservedNumbers);

        if(!reservedNumbers.isEmpty()) {
            Integer min = Collections.min(reservedNumbers);
            Integer max = Collections.max(reservedNumbers);

            LocalDate fromDate = LocalDate.of(2020, Month.FEBRUARY, min);
            LocalDate toDate = LocalDate.of(2020, Month.FEBRUARY, max);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
            from = fromDate.format(formatter);
            to = toDate.format(formatter);

            if (!reservedNumbers.isEmpty()) {

                fromField.setText(from);
                fromField.getStyleClass().clear();
                fromField.getStyleClass().add("fieldContainsNumbers");
                if (reservedNumbers.size() != 0) {
                    toField.setText(to);
                    toField.getStyleClass().clear();
                    toField.getStyleClass().add("fieldContainsNumbers");
                } else {
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

    public void sendRequestOnAction(ActionEvent actionEvent) {
        if(reserved.isEmpty())
            monthInterface.alertBoxNoDays();

        //Updates request for getRequest method
        req = new Request();
        req.setFromDate(fromField.getText());
        req.setToDate(toField.getText());
        req.setApproved(0);
        req.setUserId(current.getId());

        labelRequestOk.setVisible(true);
        PauseTransition visiblePause = new PauseTransition(
                Duration.seconds(2)
        );
        visiblePause.setOnFinished(
                event -> labelRequestOk.setVisible(false)
        );
        visiblePause.play();
        dao.addNewRequest(req);
        dao.updateDaysLeft(Integer.parseInt(DaysLeftTextField.getText()), current);
        daysLeft = Integer.parseInt(DaysLeftTextField.getText());
        maxDays = daysLeft;
    }
}
