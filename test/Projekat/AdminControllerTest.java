package Projekat;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class AdminControllerTest {

    VacationDAO dao;

    @Start
    public void start(Stage stage) throws Exception {

        dao = VacationDAO.getInstance();
        String password = "AdminTest";
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes(), 0, password.length());
        String myHash = new BigInteger(1, md.digest()).toString(16);
        User u = new User(-1, "AdminTest", "AdminTest", "AdminTest@gmail.com",
                "AdminTest", myHash, 0, 10, 0);
        dao.addUser(u);
        User user = dao.getUserByUsername("AdminTest");

        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        stage.setTitle("Vacation Planner");
        stage.getIcons().add(new Image("Assets/AppIcon.png"));
        stage.setScene(new Scene(root, 800, 494));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
    }

    @Test
    void bigTest(FxRobot robot) {
        TextField tf = robot.lookup("#usernameTextField").queryAs(TextField.class);
        assertNotNull(tf);
        robot.clickOn("#usernameTextField");
        KeyCode ctrl = KeyCode.CONTROL;
        if(System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("AdminTest");
        TextField tfPassword = robot.lookup("#passwordTextField").queryAs(TextField.class);
        assertNotNull(tfPassword);
        robot.clickOn("#passwordTextField");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("AdminTest");
        robot.clickOn("#loginBtn");

        TextField tfFrom = robot.lookup("#fromField").queryAs(TextField.class);
        assertNotNull(tfFrom);

        robot.clickOn("#nxtMonth");
        robot.clickOn("#btnTwo");
        robot.clickOn("#btnThree");
        robot.clickOn("#btnFour");

        robot.clickOn("#sendRequestBtn");

        robot.clickOn("#nxtMonth");
        robot.clickOn("#nxtMonth");
        robot.clickOn("#btnSeven");
        robot.clickOn("#btnEight");
        robot.clickOn("#btnNine");
        robot.clickOn("#sendRequestBtn");

        robot.clickOn("#btnStatus");

        ListView lvPending = robot.lookup("#listPen").queryAs(ListView.class);
        assertNotNull(lvPending);
        ObservableList<String> elements = lvPending.getItems();
        boolean contains = false;
        if(elements.contains("02 February 2020 to 04 February 2020") &&
        elements.contains("07 April 2020 to 09 April 2020"))
            contains = true;
        assertTrue(contains);

        robot.clickOn("#backBtn");
        robot.clickOn("#btnLogoutJanuary");

        robot.clickOn("#usernameTextField");
        robot.write("fsmile");

        robot.clickOn("#passwordTextField");
        robot.write("iamadmin");

        robot.clickOn("#AdminCheckBox");
        robot.clickOn("#loginBtn");

        robot.clickOn("#choiceSelectMonth");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        ListView lvRequests = robot.lookup("#listOfRequests").queryAs(ListView.class);
        robot.clickOn("#listOfRequests");
        ObservableList<String> lvReq = lvRequests.getItems();
        robot.clickOn("#listOfRequests");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#btnApprove");
        contains = false;
        if(!lvReq.contains("You've got a new request by AdminTest"))
            contains = true;
        assertTrue(contains);

        robot.clickOn("#choiceSelectMonth");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#listOfRequests");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#btnDeny");

        robot.clickOn("#logoutBtn");

        robot.clickOn("#usernameTextField");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("AdminTest");
        robot.clickOn("#passwordTextField");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("AdminTest");
        robot.clickOn("#AdminCheckBox");
        robot.clickOn("#loginBtn");

        robot.clickOn("OK");
        robot.clickOn("#btnStatus");

        ListView lvPendingTwo = robot.lookup("#listPen").queryAs(ListView.class);
        assertNotNull(lvPending);
        ObservableList<String> elementsPending = lvPendingTwo.getItems();
        contains = false;
        if(elementsPending.isEmpty())
            contains = true;
        assertTrue(contains);

        ListView lvApproved = robot.lookup("#listApr").queryAs(ListView.class);
        assertNotNull(lvApproved);
        ObservableList<String> elementsApproved = lvApproved.getItems();
        contains = false;
        if(elementsApproved.contains("02 February 2020 to 04 February 2020"))
            contains = true;
        assertTrue(contains);

        ListView lvDenied = robot.lookup("#listDen").queryAs(ListView.class);
        assertNotNull(lvDenied);
        ObservableList<String> elementsDenied = lvDenied.getItems();
        contains = false;
        if(elementsDenied.contains("07 April 2020 to 09 April 2020"))
            contains = true;
        assertTrue(contains);


        int user_id = dao.getUserByUsername("AdminTest").getId();
        dao.deleteRequestsForUser(user_id);
        dao.deleteUserByUsername("AdminTest");

    }
}