package Projekat.Months;

import Projekat.User;
import Projekat.VacationDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
class AugustControllerTest {

    public VacationDAO dao;

    @Start
    public void start(Stage stage) throws Exception {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("August.fxml"));

        dao = VacationDAO.getInstance();
        User u = new User(-1, "AugustTest", "AugustTest", "AugustTest@gmail.com",
                "AugustTest", "AugustTest", 0, 10, 0);
        dao.addUser(u);
        User user = dao.getUserByUsername("AugustTest");

        AugustController augustController = new AugustController(user);
        loader.setController(augustController);
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
        robot.clickOn("#btnTen");
        robot.clickOn("#btnEleven");
        robot.clickOn("#btnTwelve");
        robot.clickOn("#btnTwelve");


        boolean correctFromTo = false;
        if (fromTextField.getText().equals("10 August 2020") && toTextField.getText().equals("11 August 2020"))
            correctFromTo = true;
        assertTrue(correctFromTo);


        TextField daysLeftTextField = robot.lookup("#DaysLeftTextField").queryAs(TextField.class);
        boolean correctDaysLeft = false;
        if (daysLeftTextField.getText().equals("8"))
            correctDaysLeft = true;
        assertTrue(correctDaysLeft);

        int user_id = dao.getUserByUsername("AugustTest").getId();
        dao.deleteRequestsForUser(user_id);
        dao.deleteUserByUsername("AugustTest");
    }

    @Test
    void requestsStatusFXStatus(FxRobot robot) {
        Button btnStatus = robot.lookup("#btnStatus").queryAs(Button.class);
        assertNotNull(btnStatus);

        robot.clickOn("#btnStatus");
        Text requestsText = robot.lookup("#requestsText").queryAs(Text.class);
        assertNotNull(requestsText);
        int user_id = dao.getUserByUsername("AugustTest").getId();
        dao.deleteRequestsForUser(user_id);
        dao.deleteUserByUsername("AugustTest");
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
        int user_id = dao.getUserByUsername("AugustTest").getId();
        dao.deleteRequestsForUser(user_id);
        dao.deleteUserByUsername("AugustTest");
    }

    @Test
    void maxedOutDaysLeftFXTest(FxRobot robot) {
        TextField fromTextField = robot.lookup("#fromField").queryAs(TextField.class);
        assertNotNull(fromTextField);

        TextField toTextField = robot.lookup("#toField").queryAs(TextField.class);
        assertNotNull(toTextField);


        robot.clickOn("#btnThree");
        robot.clickOn("#btnFour");
        robot.clickOn("#btnFive");
        robot.clickOn("#btnSix");
        robot.clickOn("#btnSeven");
        robot.clickOn("#btnTen");
        robot.clickOn("#btnEleven");
        robot.clickOn("#btnTwelve");
        robot.clickOn("#btnThirteen");
        robot.clickOn("#btnFourteen");
        robot.clickOn("#btnSeventeen");
        robot.clickOn("#btnThirteen");



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

        int user_id = dao.getUserByUsername("AugustTest").getId();
        dao.deleteRequestsForUser(user_id);
        dao.deleteUserByUsername("AugustTest");
    }

    @Test
    void unselectAllFXTest(FxRobot robot) {
        TextField fromTextField = robot.lookup("#fromField").queryAs(TextField.class);
        assertNotNull(fromTextField);

        TextField toTextField = robot.lookup("#toField").queryAs(TextField.class);
        assertNotNull(toTextField);

        robot.clickOn("#btnTwelve");
        robot.clickOn("#btnThirteen");
        robot.clickOn("#btnFourteen");

        robot.clickOn("#btnFourteen");
        robot.clickOn("#btnThirteen");
        robot.clickOn("#btnTwelve");


        boolean correct = false;
        if (fromTextField.getText().equals("") && toTextField.getText().equals(""))
            correct = true;
        assertTrue(correct);

        int user_id = dao.getUserByUsername("AugustTest").getId();
        dao.deleteRequestsForUser(user_id);
        dao.deleteUserByUsername("AugustTest");
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

        int user_id = dao.getUserByUsername("AugustTest").getId();
        dao.deleteRequestsForUser(user_id);
        dao.deleteUserByUsername("AugustTest");
    }
}

