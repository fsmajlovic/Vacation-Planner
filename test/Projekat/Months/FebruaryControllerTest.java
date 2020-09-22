package Projekat.Months;

import static org.junit.jupiter.api.Assertions.*;

import Projekat.SignUpController;
import Projekat.User;
import Projekat.VacationDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxRobotException;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.service.query.EmptyNodeQueryException;

import java.awt.*;

@ExtendWith(ApplicationExtension.class)
class FebruaryControllerTest {
    public VacationDAO dao;

    @Start
    public void start (Stage stage) throws Exception{
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("February.fxml"));

        dao = VacationDAO.getInstance();
        User u = new User(-1, "FebruaryTest", "FebruaryTest", "FebruaryTest@gmail.com",
                "FebruaryTest", "FebruaryTest", 0, 10, 0);
        dao.addUser(u);
        User user = dao.getUserByUsername("FebruaryTest");

        FebruaryController februaryController = new FebruaryController(user);
        loader.setController(februaryController);
        root = loader.load();
        stage.setTitle("Vacation Planner");
        stage.getIcons().add(new Image("Assets/AppIcon.png"));
        stage.setScene(new Scene(root, 800,494));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
    }

    @Test
    void nextMonthFXTest(FxRobot robot){
        robot.clickOn("#nxtMonth");
        Text monthText = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(monthText);

        dao.deleteUserByUsername("FebruaryTest");
    }

    @Test
    void previousMonthFXTest(FxRobot robot){
        robot.clickOn("#pvsMonth");

        Text monthText = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(monthText);

        dao.deleteUserByUsername("FebruaryTest");
    }


    @Test
    void fromToTextfieldFXTest(FxRobot robot){
        TextField fromTextField = robot.lookup("#fromField").queryAs(TextField.class);
        assertNotNull(fromTextField);

        TextField toTextField = robot.lookup("#toField").queryAs(TextField.class);
        assertNotNull(toTextField);

        ToggleButton btnTwo = robot.lookup("#btnTwo").queryAs(ToggleButton.class);
        assertNotNull(btnTwo);

        ToggleButton btnThree = robot.lookup("#btnThree").queryAs(ToggleButton.class);
        assertNotNull(btnThree);

        ToggleButton btnFour = robot.lookup("#btnFour").queryAs(ToggleButton.class);
        assertNotNull(btnFour);

        ToggleButton btnFive = robot.lookup("#btnFive").queryAs(ToggleButton.class);
        assertNotNull(btnFive);


        robot.sleep(200);
        robot.clickOn("#btnFive");
        robot.clickOn("#btnSix");
        robot.clickOn("#btnNine");
        robot.sleep(200);
        robot.clickOn("#btnNine");


        boolean correctFromTo = false;
        if(fromTextField.getText().equals("05 February 2020") && toTextField.getText().equals("06 February 2020"))
            correctFromTo = true;
        assertTrue(correctFromTo);

        TextField daysLeftTextField = robot.lookup("#DaysLeftTextField").queryAs(TextField.class);
        boolean correctDaysLeft = false;
        if(daysLeftTextField.getText().equals("8"))
            correctDaysLeft = true;
        assertTrue(correctDaysLeft);

        dao.deleteUserByUsername("FebruaryTest");
    }

    @Test
    void requestsStatusFXStatus(FxRobot robot){
        Button btnStatus = robot.lookup("#btnStatus").queryAs(Button.class);
        assertNotNull(btnStatus);

        robot.clickOn("#btnStatus");
        Text requestsText = robot.lookup("#requestsText").queryAs(Text.class);
        assertNotNull(requestsText);

        dao.deleteUserByUsername("FebruaryTest");
    }

    @Test
    void logoutButtonFXTest(FxRobot robot){
        Button btnLogoutJanuary = robot.lookup("#btnLogoutJanuary").queryAs(Button.class);
        assertNotNull(btnLogoutJanuary);
        robot.clickOn("#btnLogoutJanuary");

        boolean nullLabel = false;
        try {
            Label copyrightLabel = robot.lookup("#copyrightLabel").queryAs(Label.class);
        }catch (EmptyNodeQueryException e){
            nullLabel = true;
        }
        assertTrue(nullLabel);
        dao.deleteUserByUsername("FebruaryTest");
    }

    @Test
    void maxedOutDaysLeftFXTest(FxRobot robot){
        TextField fromTextField = robot.lookup("#fromField").queryAs(TextField.class);
        assertNotNull(fromTextField);

        TextField toTextField = robot.lookup("#toField").queryAs(TextField.class);
        assertNotNull(toTextField);

        ToggleButton tgThree = robot.lookup("#btnThree").queryAs(ToggleButton.class);
        assertNotNull(tgThree);

        robot.clickOn("#btnTwo");
        robot.clickOn("#btnThree");
        robot.clickOn("#btnFour");
        robot.clickOn("#btnFive");
        robot.clickOn("#btnSix");
        robot.clickOn("#btnNine");
        robot.clickOn("#btnTen");
        robot.clickOn("#btnEleven");
        robot.clickOn("#btnTwelve");
        robot.clickOn("#btnThirteen");
        robot.clickOn("#btnSix");

        TextField daysLeftTextField = robot.lookup("#DaysLeftTextField").queryAs(TextField.class);
        boolean correctDaysLeft = false;
        if(daysLeftTextField.getText().equals("0"))
            correctDaysLeft = true;
        assertTrue(correctDaysLeft);

        Button sendRequestBtn = robot.lookup("#sendRequestBtn").queryAs(Button.class);
        assertNotNull(sendRequestBtn);
        robot.clickOn("#sendRequestBtn");

        Label labelRequestOk = robot.lookup("#labelRequestOk").queryAs(Label.class);
        assertNotNull(labelRequestOk);
        assertTrue(labelRequestOk.isVisible());


        dao.deleteUserByUsername("FebruaryTest");
    }

    @Test
    void unselectAllFXTest(FxRobot robot) {
        TextField fromTextField = robot.lookup("#fromField").queryAs(TextField.class);
        assertNotNull(fromTextField);

        TextField toTextField = robot.lookup("#toField").queryAs(TextField.class);
        assertNotNull(toTextField);

        ToggleButton tgOne = robot.lookup("#btnOne").queryAs(ToggleButton.class);
        assertNotNull(tgOne);

        ToggleButton tgThree = robot.lookup("#btnThree").queryAs(ToggleButton.class);
        assertNotNull(tgThree);

        robot.clickOn("#btnTwo");
        robot.clickOn("#btnThree");
        robot.clickOn("#btnFour");

        robot.clickOn("#btnFour");
        robot.clickOn("#btnThree");
        robot.clickOn("#btnTwo");


        boolean correct = false;
        if (fromTextField.getText().equals("") && toTextField.getText().equals(""))
            correct = true;
        assertTrue(correct);

        dao.deleteUserByUsername("FebruaryTest");
    }

    @Test
    void noDaysSelectedAlertFXTest(FxRobot robot){
        Button sendRequestBtn = robot.lookup("#sendRequestBtn").queryAs(Button.class);
        assertNotNull(sendRequestBtn);
        robot.clickOn("#sendRequestBtn");

        boolean alert = false;
        try {
            robot.clickOn("OK");
            alert = true;
        }catch (FxRobotException e){
            e.printStackTrace();
        }
        assertTrue(alert);

        dao.deleteUserByUsername("FebruaryTest");
    }


}