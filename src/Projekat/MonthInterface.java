package Projekat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public interface MonthInterface {
    public void nextMonth(String month, TextField toField, User current) throws IOException;
    public void previousMonth(String month, TextField toField, User current);
    public void statusInfo(User current);
}
