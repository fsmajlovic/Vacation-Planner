package Projekat;

import static org.junit.jupiter.api.Assertions.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

@ExtendWith(ApplicationExtension.class)
class LoginControllerTest {
    @Start
    public void start (Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        stage.setTitle("Vacation Planner");
        stage.getIcons().add(new Image("Assets/AppIcon.png"));
        stage.setScene(new Scene(root, 800, 494));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
    }

    @Test
    void invalidLabelYouAreNotAnAdminFXTest(FxRobot robot){
        TextField tf = robot.lookup("#usernameTextField").queryAs(TextField.class);
        assertNotNull(tf);
        robot.clickOn("#usernameTextField");
        KeyCode ctrl = KeyCode.CONTROL;
        if(System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("test123");
        TextField tfPassword = robot.lookup("#passwordTextField").queryAs(TextField.class);
        assertNotNull(tfPassword);
        robot.clickOn("#passwordTextField");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("test123");
        robot.clickOn("#AdminCheckBox");
        robot.clickOn("#loginBtn");
        Label notAdminLabel = robot.lookup("#yourRNAdminLabel").queryAs(Label.class);
        assertTrue(notAdminLabel.isVisible());
    }

    @Test
    void incorrectUsernameOrPasswordLabelTestsFXTest(FxRobot robot){
        TextField tf = robot.lookup("#usernameTextField").queryAs(TextField.class);
        assertNotNull(tf);
        robot.clickOn("#usernameTextField");
        KeyCode ctrl = KeyCode.CONTROL;
        if(System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("123");
        TextField tfPassword = robot.lookup("#passwordTextField").queryAs(TextField.class);
        assertNotNull(tfPassword);
        robot.clickOn("#passwordTextField");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("1234");
        robot.clickOn("#loginBtn");
        Label invalidLabel = robot.lookup("#invalidLabel").queryAs(Label.class);
        assertTrue(invalidLabel.isVisible());
        //Remove label after clicking on password or username
        robot.clickOn("#usernameTextField");
        assertFalse(invalidLabel.isVisible());
        robot.clickOn("#usernameTextField");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("123");
        robot.clickOn("#loginBtn");
        robot.clickOn("#passwordTextField");
        assertFalse(invalidLabel.isVisible());
    }

    @Test
    void emptyUsernameOrPasswordFXTest(FxRobot robot){
        TextField tf = robot.lookup("#usernameTextField").queryAs(TextField.class);
        assertNotNull(tf);
        robot.clickOn("#loginBtn");
        Label invalidLabel = robot.lookup("#invalidLabel").queryAs(Label.class);
        assertTrue(invalidLabel.isVisible());
    }

    @Test
    void correctAdminLoginFXTest(FxRobot robot){
        TextField tf = robot.lookup("#usernameTextField").queryAs(TextField.class);
        assertNotNull(tf);
        robot.clickOn("#usernameTextField");
        KeyCode ctrl = KeyCode.CONTROL;
        if(System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("fsmile");
        TextField tfPassword = robot.lookup("#passwordTextField").queryAs(TextField.class);
        assertNotNull(tfPassword);
        robot.clickOn("#passwordTextField");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("iamadmin");
        robot.clickOn("#AdminCheckBox");
        robot.clickOn("#loginBtn");

        ListView listOfRequests = robot.lookup("#listOfRequests").queryAs(ListView.class);
        assertNotNull(listOfRequests);
    }

    @Test
    void correctSignUpOpenFXTest(FxRobot robot){
        robot.clickOn("#signUpBtn");
        TextField tf = robot.lookup("#firstNameTF").queryAs(TextField.class);
        assertNotNull(tf);
    }

    @Test
    void correctLoginFXTest(FxRobot robot){
        TextField tf = robot.lookup("#usernameTextField").queryAs(TextField.class);
        assertNotNull(tf);
        robot.clickOn("#usernameTextField");
        KeyCode ctrl = KeyCode.CONTROL;
        if(System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("test123");
        TextField tfPassword = robot.lookup("#passwordTextField").queryAs(TextField.class);
        assertNotNull(tfPassword);
        robot.clickOn("#passwordTextField");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("test123");
        robot.clickOn("#loginBtn");

        TextField tfFrom = robot.lookup("#fromField").queryAs(TextField.class);
        assertNotNull(tfFrom);
    }

    @Test
    void adminLoginAsUserFXTest(FxRobot robot){
        TextField tf = robot.lookup("#usernameTextField").queryAs(TextField.class);
        assertNotNull(tf);
        robot.clickOn("#usernameTextField");
        KeyCode ctrl = KeyCode.CONTROL;
        if(System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("fsmile");
        TextField tfPassword = robot.lookup("#passwordTextField").queryAs(TextField.class);
        assertNotNull(tfPassword);
        robot.clickOn("#passwordTextField");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("iamadmin");
        robot.clickOn("#loginBtn");

        TextField tfFrom = robot.lookup("#fromField").queryAs(TextField.class);
        assertNotNull(tfFrom);
    }




}