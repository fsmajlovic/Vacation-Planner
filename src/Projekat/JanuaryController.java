package Projekat;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JanuaryController {

    public TextField DaysLeftTextField = new TextField();
    public TextField  fromField;
    public TextField toField;
    public String from, to;
    public int daysLeft;
    public ArrayList<String> reserved = new ArrayList<>();
    public ArrayList<Integer> reservedNumbers = new ArrayList<>();
    public User current;
    public Request req;
    public Label labelRequestOk;
    public VacationDAO dao;

    public JanuaryController(User current){
        this.current = current;
        dao = VacationDAO.getInstance();
        daysLeft = dao.getDaysLeftByUsername(current.getUsername());
    }

    @FXML
    public void initialize(){
        DaysLeftTextField.setText(String.valueOf(daysLeft));
    }

    public void LogoutAction(ActionEvent actionEvent) {
        Stage st = (Stage) toField.getScene().getWindow();
        st.close();
    }

    public void btnPressed(ActionEvent actionEvent) {
         ToggleButton tgl = (ToggleButton) actionEvent.getSource();
        if(daysLeft > 0 && tgl.getStyleClass().contains("notPressed")){
//            if(reserved.size() > 0 && (edge == 6 || edge == 13 || edge == 20 || edge == 27)
//                    && (Integer.parseInt((Collections.max(reserved))) !=
//                    (edge-3)) && (Integer.parseInt((Collections.min(reserved))) != Integer.parseInt(tgl.getText()) + 1)){
//                return;
//            }
//            else if(reserved.size() > 0 && (edge != 6 && edge != 13 && edge != 20 && edge != 27) &&
//                    (Integer.parseInt((Collections.max(reserved))) != (Integer.parseInt(tgl.getText())-1)
//                    && Integer.parseInt((Collections.min(reserved))) != Integer.parseInt(tgl.getText()) + 1)) {
//                return;
//            }
            int max = 0, min = 0;
            int dayNumber = Integer.parseInt(tgl.getText());
            if(!reserved.isEmpty()) {
                max = Integer.parseInt((Collections.max(reserved)));
                min = Integer.parseInt((Collections.min(reserved)));
            }
            System.out.println("Max: " + max + " Min: " + min + " Day" + dayNumber);
            if((dayNumber == 6 || dayNumber == 20 || dayNumber == 27 || dayNumber == 13) &&
                max != min  && dayNumber > max && (max != (dayNumber - 3))){
                System.out.println("this1");
                return;
            }
            else if((dayNumber == 6 || dayNumber == 13 || dayNumber == 20 || dayNumber == 27) &&
                min != max && (min != dayNumber + 3) && dayNumber < min){
                System.out.println("this2");
                return;
            }
            else if(max != min && (max != dayNumber - 1) && dayNumber > max &&
                    (dayNumber != 6 && dayNumber != 13 && dayNumber != 20 && dayNumber != 27)){
                System.out.println("this3");
                return;
            }
            else if(min != max && (min != dayNumber + 1) && dayNumber < min  &&
                    (dayNumber != 6 && dayNumber != 13 && dayNumber != 20 && dayNumber != 27)){
                System.out.println("this4");
                return;
            }
            //Updating the ArrayList
            else if (reserved.isEmpty()) {
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
            //Updating the ArrayList
            reserved.remove((tgl.getText()));
            //Updating DaysLeft
            daysLeft++;
            DaysLeftTextField.setText(String.valueOf(daysLeft));
            ((ToggleButton) actionEvent.getSource()).getStyleClass().clear();
            ((ToggleButton) actionEvent.getSource()).getStyleClass().add("notPressed");
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

            LocalDate fromDate = LocalDate.of(2020, Month.JANUARY, min);
            LocalDate toDate = LocalDate.of(2020, Month.JANUARY, max);
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
        boolean isApproved = dao.isApproved(current);
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("February.fxml"));
        FebruaryController februaryController = new FebruaryController(current);
        loader.setController(februaryController);
        root = loader.load();
        stage.setTitle("Vacation Planner");
        stage.getIcons().add(new Image("AppIcon.png"));
        stage.setScene(new Scene(root, 800, 500));
        stage.setResizable(false);
        stage.show();
        Stage stg = (Stage) toField.getScene().getWindow();
        stg.close();
        if (isApproved) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Request approved");
            stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("AppIcon.png"));
            alert.setHeaderText("Woohoo! Looks like some of your requests have been accepted!");
            alert.setContentText("Check your requests status and enjoy your vacation!");
            alert.showAndWait();

        }
    }

    public void pvsMonth(MouseEvent mouseEvent) throws IOException {
        Parent MonthParent = FXMLLoader.load(getClass().getResource("December" + ".fxml"));
        Scene MonthScene = new Scene(MonthParent, 800, 500);
        Stage window = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(MonthScene);
        window.show();
    }

    public Request getRequest(){
        return req;
    }

    public void sendRequestOnAction(ActionEvent actionEvent) {
        if(reserved.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Day selection");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("AppIcon.png"));
            alert.setHeaderText("No days selected.");
            alert.setContentText("You need to select one or more days!");
            alert.showAndWait();
            return;
        }

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
    }

    public void StatusOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RequestsStatus.fxml"));
        RequestsStatusController requestsStatusController = new RequestsStatusController(current.getId());
        loader.setController(requestsStatusController);
        root = loader.load();
        stage.getIcons().add(new Image("AppIcon.png"));
        stage.setTitle("Status");
        stage.setScene(new Scene(root, 540,190));
        stage.setResizable(false);
        stage.show();
    }
}
