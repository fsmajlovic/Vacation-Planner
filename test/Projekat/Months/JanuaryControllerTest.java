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
class JanuaryControllerTest {

    public VacationDAO dao;

    @Start
    public void start (Stage stage) throws Exception{
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("January.fxml"));

        dao = VacationDAO.getInstance();
        User u = new User(-1, "JanuaryTest", "JanuaryTest", "JanuaryTest@gmail.com",
                "JanuaryTest", "JanuaryTest", 0, 10, 0);
        dao.addUser(u);
        User user = dao.getUserByUsername("JanuaryTest");

        JanuaryController januaryController = new JanuaryController(user);
        loader.setController(januaryController);
        root = loader.load();
        stage.setTitle("Vacation Planner");
        stage.getIcons().add(new Image("Assets/AppIcon.png"));
        stage.setScene(new Scene(root, 800,494));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
    }

    @Test
    void fromToTextfieldFXTest(FxRobot robot){
        TextField fromTextField = robot.lookup("#fromField").queryAs(TextField.class);
        assertNotNull(fromTextField);

        TextField toTextField = robot.lookup("#toField").queryAs(TextField.class);
        assertNotNull(toTextField);

        ToggleButton tgOne = robot.lookup("#btnOne").queryAs(ToggleButton.class);
        assertNotNull(tgOne);

        ToggleButton tgThree = robot.lookup("#btnThree").queryAs(ToggleButton.class);
        assertNotNull(tgThree);


        robot.sleep(200);
        robot.clickOn("#btnTwo");
        robot.clickOn("#btnThree");
        robot.doubleClickOn("#btnSix");


        boolean correctFromTo = false;
        if(fromTextField.getText().equals("02 January 2020") && toTextField.getText().equals("03 January 2020"))
            correctFromTo = true;
        assertTrue(correctFromTo);

        System.out.println("from to" + fromTextField.getText() + " " + toTextField.getText());

        TextField daysLeftTextField = robot.lookup("#DaysLeftTextField").queryAs(TextField.class);
        boolean correctDaysLeft = false;
        if(daysLeftTextField.getText().equals("8"))
            correctDaysLeft = true;
        assertTrue(correctDaysLeft);

        int user_id = dao.getUserByUsername("JanuaryTest").getId();
        dao.deleteRequestsForUser(user_id);
        dao.deleteUserByUsername("JanuaryTest");
    }

    @Test
    void requestsStatusFXStatus(FxRobot robot){
        Button btnStatus = robot.lookup("#btnStatus").queryAs(Button.class);
        assertNotNull(btnStatus);

        robot.clickOn("#btnStatus");
        Text requestsText = robot.lookup("#requestsText").queryAs(Text.class);
        assertNotNull(requestsText);
        int user_id = dao.getUserByUsername("JanuaryTest").getId();
        dao.deleteRequestsForUser(user_id);
        dao.deleteUserByUsername("JanuaryTest");
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
        int user_id = dao.getUserByUsername("JanuaryTest").getId();
        dao.deleteRequestsForUser(user_id);
        dao.deleteUserByUsername("JanuaryTest");
    }

    @Test
    void maxedOutDaysLeftFXTest(FxRobot robot){
        TextField fromTextField = robot.lookup("#fromField").queryAs(TextField.class);
        assertNotNull(fromTextField);

        TextField toTextField = robot.lookup("#toField").queryAs(TextField.class);
        assertNotNull(toTextField);

        ToggleButton tgOne = robot.lookup("#btnOne").queryAs(ToggleButton.class);
        assertNotNull(tgOne);

        ToggleButton tgThree = robot.lookup("#btnThree").queryAs(ToggleButton.class);
        assertNotNull(tgThree);

        robot.clickOn("#btnOne");
        robot.clickOn("#btnTwo");
        robot.clickOn("#btnThree");
        robot.clickOn("#btnSix");
        robot.clickOn("#btnSeven");
        robot.clickOn("#btnEight");
        robot.clickOn("#btnNine");
        robot.clickOn("#btnTen");
        robot.clickOn("#btnThirteen");
        robot.clickOn("#btnFourteen");
        robot.clickOn("#btnFifteen");
        robot.clickOn("#btnTen");

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


        int user_id = dao.getUserByUsername("JanuaryTest").getId();
        dao.deleteRequestsForUser(user_id);
        dao.deleteUserByUsername("JanuaryTest");
    }

    @Test
    void unselectAllFXTest(FxRobot robot){
        TextField fromTextField = robot.lookup("#fromField").queryAs(TextField.class);
        assertNotNull(fromTextField);

        TextField toTextField = robot.lookup("#toField").queryAs(TextField.class);
        assertNotNull(toTextField);

        robot.clickOn("#btnThirteen");
        robot.clickOn("#btnFourteen");
        robot.clickOn("#btnFifteen");

        robot.clickOn("#btnFifteen");
        robot.clickOn("#btnFourteen");
        robot.clickOn("#btnThirteen");



        boolean correct = false;
        if(fromTextField.getText().equals("") && toTextField.getText().equals(""))
            correct = true;
        assertTrue(correct);

        int user_id = dao.getUserByUsername("JanuaryTest").getId();
        dao.deleteRequestsForUser(user_id);
        dao.deleteUserByUsername("JanuaryTest");
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

        int user_id = dao.getUserByUsername("JanuaryTest").getId();
        dao.deleteRequestsForUser(user_id);
        dao.deleteUserByUsername("JanuaryTest");
    }


}