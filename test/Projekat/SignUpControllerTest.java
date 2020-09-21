package Projekat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class SignUpControllerTest {

    @Start
    public void start (Stage stage) throws Exception{
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpScreen.fxml"));
        SignUpController signUpController = new SignUpController();
        loader.setController(signUpController);
        root = loader.load();
        stage.setTitle("Vacation Planner");
        stage.getIcons().add(new Image("Assets/AppIcon.png"));
        stage.setScene(new Scene(root, 800,494));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
    }

    @Test
    void invalidCredentialsFXTest(FxRobot robot){

        TextField firstNameTF = robot.lookup("#firstNameTF").queryAs(TextField.class);
        assertNotNull(firstNameTF);
        TextField lastNameTF = robot.lookup("#lastNameTF").queryAs(TextField.class);
        assertNotNull(lastNameTF);
        TextField emailTF = robot.lookup("#emailTF").queryAs(TextField.class);
        assertNotNull(lastNameTF);
        TextField usernameTF = robot.lookup("#usernameTF").queryAs(TextField.class);
        assertNotNull(lastNameTF);
        TextField passwordTF = robot.lookup("#passwordTF").queryAs(TextField.class);
        assertNotNull(lastNameTF);

        KeyCode ctrl = KeyCode.CONTROL;
        if(System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;

        robot.clickOn("#firstNameTF");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("a1234");

        robot.clickOn("#lastNameTF");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("a@#A@");

        robot.clickOn("#emailTF");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("a");

        robot.clickOn("#usernameTF");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("@#a%%");

        robot.clickOn("#passwordTF");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("a");


        Button signUpBtn = robot.lookup("#signUpBtn").queryAs(Button.class);
        assertNotNull(signUpBtn);
        robot.clickOn("#signUpBtn");

        Label invalidFirstNameLabel = robot.lookup("#invalidFirstNameLabel").queryAs(Label.class);
        Label invalidLastNameLabel = robot.lookup("#invalidLastNameLabel").queryAs(Label.class);
        Label incorrectEmailLabel = robot.lookup("#incorrectEmailLabel").queryAs(Label.class);
        Label invalidUsernameLabel = robot.lookup("#invalidUsernameLabel").queryAs(Label.class);
        Label incorrectPasswordLabel = robot.lookup("#invalidFirstNameLabel").queryAs(Label.class);


        assertTrue(incorrectEmailLabel.isVisible());
        assertTrue(invalidUsernameLabel.isVisible());
        assertTrue(incorrectPasswordLabel.isVisible());
        assertTrue(invalidFirstNameLabel.isVisible());
        assertTrue(invalidLastNameLabel.isVisible());

    }


    @Test
    void correctSignUpFXTest(FxRobot robot){

        TextField firstNameTF = robot.lookup("#firstNameTF").queryAs(TextField.class);
        assertNotNull(firstNameTF);
        TextField lastNameTF = robot.lookup("#lastNameTF").queryAs(TextField.class);
        assertNotNull(lastNameTF);
        TextField emailTF = robot.lookup("#emailTF").queryAs(TextField.class);
        assertNotNull(lastNameTF);
        TextField usernameTF = robot.lookup("#usernameTF").queryAs(TextField.class);
        assertNotNull(lastNameTF);
        TextField passwordTF = robot.lookup("#passwordTF").queryAs(TextField.class);
        assertNotNull(lastNameTF);

        KeyCode ctrl = KeyCode.CONTROL;
        if(System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;

        robot.clickOn("#firstNameTF");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("goodUser");

        robot.clickOn("#lastNameTF");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("goodUser");

        robot.clickOn("#emailTF");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("goodUser123@gmail.com");

        robot.clickOn("#usernameTF");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("goodUser123");

        robot.clickOn("#passwordTF");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("12345");

        Button signUpBtn = robot.lookup("#signUpBtn").queryAs(Button.class);
        assertNotNull(signUpBtn);
        robot.clickOn("#signUpBtn");

        VacationDAO dao = VacationDAO.getInstance();
        User u = dao.getUserByUsername("goodUser123");
        assertNotNull(u);

        //is Email and Username already in use?
        robot.clickOn("#firstNameTF");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("goodUser");

        robot.clickOn("#lastNameTF");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("goodUser");

        robot.clickOn("#emailTF");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("goodUser123@gmail.com");

        robot.clickOn("#usernameTF");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("goodUser123");

        robot.clickOn("#passwordTF");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("12345");

        Label emailInUseLabel = robot.lookup("#emailInUseLabel").queryAs(Label.class);
        Label usernameInUseLabel = robot.lookup("#usernameInUseLabel").queryAs(Label.class);

        robot.clickOn("#signUpBtn");

        assertTrue(emailInUseLabel.isVisible());
        assertTrue(usernameInUseLabel.isVisible());

        robot.clickOn("#signUpBtn");

        //Delete test user from db
        dao.deleteUserById(u);
    }


    @Test
    void backBtnFXTest(FxRobot robot){
        Button b = robot.lookup("#backBtn").queryAs(Button.class);
        assertNotNull(b);
        robot.clickOn("#backBtn");
        Label copyRightLabel =  robot.lookup("#copyrightLabel").queryAs(Label.class);
        assertNotNull(copyRightLabel);
    }

}