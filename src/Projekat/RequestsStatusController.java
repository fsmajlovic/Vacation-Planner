package Projekat;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class RequestsStatusController {
    private ListView listApproved, listDenied, listPending;
    private VacationDAO dao;
    private int id;
    private ObservableList<String> approved, denied, pending;
    public RequestsStatusController(int id){
        dao = VacationDAO.getInstance();
        this.id = id;
    }

    @FXML
    public void initialize(){

    }

}
