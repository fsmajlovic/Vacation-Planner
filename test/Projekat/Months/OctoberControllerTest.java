package Projekat.Months;

import Projekat.User;
import Projekat.VacationDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxRobotException;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.service.query.EmptyNodeQueryException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class OctoberControllerTest {

    public VacationDAO dao;

    @Start
    public void start(Stage stage) throws Exception {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("October.fxml"));

        dao = VacationDAO.getInstance();
        User u = new User(-1, "OctoberTest", "OctoberTest", "OctoberTest@gmail.com",
                "OctoberTest", "OctoberTest", 0, 10, 0);
        dao.addUser(u);
        User user = dao.getUserByUsername("OctoberTest");

        OctoberController octoberController = new OctoberController(user);
        loader.setController(octoberController);
        root = loader.load();
        stage.setTitle("Vacation Planner");
        stage.getIcons().add(new Image("Assets/AppIcon.png"));
        stage.setScene(new Scene(root, 800, 494));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
    }

    @Test
    void fromToTextfieldFXTest(FxRobot robot) {
        TextField fromTextField = robot.lookup("#fromField").queryAs(TextField.class);
        assertNotNull(fromTextField);

        TextField toTextField = robot.lookup("#toField").queryAs(TextField.class);
        assertNotNull(toTextField);

        robot.sleep(200);
        robot.clickOn("#btnOne");
        robot.clickOn("#btnTwo");
        robot.doubleClickOn("#btnFive");


        boolean correctFromTo = false;
        if (fromTextField.getText().equals("01 October 2020") && toTextField.getText().equals("02 October 2020"))
            correctFromTo = true;
        assertTrue(correctFromTo);

        TextField daysLeftTextField = robot.lookup("#DaysLeftTextField").queryAs(TextField.class);
        boolean correctDaysLeft = false;
        if (daysLeftTextField.getText().equals("8"))
            correctDaysLeft = true;
        assertTrue(correctDaysLeft);

        int user_id = dao.getUserByUsername("OctoberTest").getId();
        dao.deleteRequestsForUser(user_id);
        dao.deleteUserByUsername("OctoberTest");
    }

    @Test
    void requestsStatusFXStatus(FxRobot robot) {
        Button btnStatus = robot.lookup("#btnStatus").queryAs(Button.class);
        assertNotNull(btnStatus);

        robot.clickOn("#btnStatus");
        Text requestsText = robot.lookup("#requestsText").queryAs(Text.class);
        assertNotNull(requestsText);
        int user_id = dao.getUserByUsername("OctoberTest").getId();
        dao.deleteRequestsForUser(user_id);
        dao.deleteUserByUsername("OctoberTest");
    }

    @Test
    void logoutButtonFXTest(FxRobot robot) {
        Button btnLogoutJanuary = robot.lookup("#btnLogoutJanuary").queryAs(Button.class);
        assertNotNull(btnLogoutJanuary);
        robot.clickOn("#btnLogoutJanuary");

        boolean nullLabel = false;
        try {
            Label copyrightLabel = robot.lookup("#copyrightLabel").queryAs(Label.class);
        } catch (EmptyNodeQueryException e) {
            nullLabel = true;
        }
        assertTrue(nullLabel);
        int user_id = dao.getUserByUsername("OctoberTest").getId();
        dao.deleteRequestsForUser(user_id);
        dao.deleteUserByUsername("OctoberTest");
    }

    @Test
    void maxedOutDaysLeftFXTest(FxRobot robot) {
        TextField fromTextField = robot.lookup("#fromField").queryAs(TextField.class);
        assertNotNull(fromTextField);

        TextField toTextField = robot.lookup("#toField").queryAs(TextField.class);
        assertNotNull(toTextField);

        robot.clickOn("#btnOne");
        robot.clickOn("#btnTwo");
        robot.clickOn("#btnFive");
        robot.clickOn("#btnSix");
        robot.clickOn("#btnSeven");
        robot.clickOn("#btnEight");
        robot.clickOn("#btnNine");
        robot.clickOn("#btnTwelve");
        robot.clickOn("#btnThirteen");
        robot.clickOn("#btnFourteen");
        robot.clickOn("#btnFifteen");
        robot.clickOn("#btnSeven");

        TextField daysLeftTextField = robot.lookup("#DaysLeftTextField").queryAs(TextField.class);
        boolean correctDaysLeft = false;
        if (daysLeftTextField.getText().equals("0"))
            correctDaysLeft = true;
        assertTrue(correctDaysLeft);

        Button sendRequestBtn = robot.lookup("#sendRequestBtn").queryAs(Button.class);
        assertNotNull(sendRequestBtn);
        robot.clickOn("#sendRequestBtn");

        Label labelRequestOk = robot.lookup("#labelRequestOk").queryAs(Label.class);
        assertNotNull(labelRequestOk);
        assertTrue(labelRequestOk.isVisible());


        int user_id = dao.getUserByUsername("OctoberTest").getId();
        dao.deleteRequestsForUser(user_id);
        dao.deleteUserByUsername("OctoberTest");
    }

    @Test
    void unselectAllFXTest(FxRobot robot) {
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
        if (fromTextField.getText().equals("") && toTextField.getText().equals(""))
            correct = true;
        assertTrue(correct);

        int user_id = dao.getUserByUsername("OctoberTest").getId();
        dao.deleteRequestsForUser(user_id);
        dao.deleteUserByUsername("OctoberTest");
    }

    @Test
    void noDaysSelectedAlertFXTest(FxRobot robot) {
        Button sendRequestBtn = robot.lookup("#sendRequestBtn").queryAs(Button.class);
        assertNotNull(sendRequestBtn);
        robot.clickOn("#sendRequestBtn");

        boolean alert = false;
        try {
            robot.clickOn("OK");
            alert = true;
        } catch (FxRobotException e) {
            e.printStackTrace();
        }
        assertTrue(alert);

        int user_id = dao.getUserByUsername("OctoberTest").getId();
        dao.deleteRequestsForUser(user_id);
        dao.deleteUserByUsername("OctoberTest");
    }
}

