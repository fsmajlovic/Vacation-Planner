package Projekat.Months;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Calendar;

public class MonthsModel {
    private ObservableList<String> months = FXCollections.observableArrayList();
    private SimpleStringProperty currentMonth = new SimpleStringProperty();

    public void fill(){
        months.add("Select All");
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        String month = getMonthName();
        currentMonth.set("All Months");
    }

    public ObservableList<String> getMonths() {
        return months;
    }

    public void setMonths(ObservableList<String> months) {
        this.months = months;
    }

    public String getCurrentMonth() {
        return currentMonth.get();
    }

    public SimpleStringProperty getCurrentSimple() {return currentMonth;}

    public SimpleStringProperty currentMonthProperty() {
        return currentMonth;
    }

    public void setCurrentMonth(String currentMonth) {
        this.currentMonth.set(currentMonth);
    }

    public String getMonthName(){
        String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};
        Calendar cal = Calendar.getInstance();
        return monthName[cal.get(Calendar.MONTH)];
    }
}
