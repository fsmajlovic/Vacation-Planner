package Projekat.Months;

import Projekat.Months.MonthController;
import Projekat.User;
import javafx.scene.control.TextField;

import java.io.IOException;

public interface MonthInterface {
    public void nextMonth(String month, TextField toField, User current, MonthController controller) throws IOException;
    public void previousMonth(String month, TextField toField, User current, MonthController controller);
    public void statusInfo(User current);
    public void alertBoxNoDays();
}
