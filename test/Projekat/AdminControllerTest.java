package Projekat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class AdminControllerTest {

    VacationDAO dao;

    @Start
    public void start(Stage stage) throws Exception {

        dao = VacationDAO.getInstance();
        User u = new User(-1, "AdminTest", "AdminTest", "AdminTest@gmail.com",
                "AdminTest", "AdminTest", 0, 10, 0);
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



}