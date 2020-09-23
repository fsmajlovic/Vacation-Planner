package Projekat;

import Projekat.Months.JanuaryController;
import Projekat.User;
import Projekat.VacationDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class NavigationTest {

    public VacationDAO dao;

    @Start
    public void start(Stage stage) throws Exception {
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
        stage.setScene(new Scene(root, 800, 494));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
    }

    @Test
    void nextPreviousMonths(FxRobot robot){
        Text month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#nxtMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#nxtMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#nxtMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#nxtMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#nxtMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#nxtMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#nxtMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#nxtMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#nxtMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#nxtMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#nxtMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#nxtMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#pvsMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#pvsMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#pvsMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#pvsMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#pvsMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#pvsMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#pvsMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#pvsMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#pvsMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#pvsMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#pvsMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);

        robot.clickOn("#pvsMonth");
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        month_text = robot.lookup("#monthText").queryAs(Text.class);
        assertNotNull(month_text);


        int user_id = dao.getUserByUsername("JanuaryTest").getId();
        dao.deleteRequestsForUser(user_id);
        dao.deleteUserByUsername("JanuaryTest");
    }

}