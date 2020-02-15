package Projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class RequestsStatusController {
    public ListView listApr, listDen, listPen;
    private VacationDAO dao;
    private int id;
    private ObservableList<String> approvedObs, deniedObs, pendingObs;
    private ArrayList<String> approvedArray, deniedArray, pendingArray;
    private ArrayList<Request> requests;
    public RequestsStatusController(int id){
        dao = VacationDAO.getInstance();
        this.id = id;
        this.approvedArray = new ArrayList<>();
        this.pendingArray = new ArrayList<>();
        this.deniedArray = new ArrayList<>();
        this.approvedObs = FXCollections.observableArrayList(approvedArray);
        this.pendingObs = FXCollections.observableArrayList(pendingArray);
        this.deniedObs = FXCollections.observableArrayList(deniedArray);
        listApr = new ListView();
        listDen = new ListView();
        listPen = new ListView();
    }

    @FXML
    public void initialize(){
        requests = dao.requests();
        for(Request r : requests){
            if(r.userId == id){
                if(r.getApproved() == 1)
                    approvedArray.add(r.fromDate + " to " + r.toDate);
                else if(r.getApproved() == 0)
                    pendingArray.add(r.fromDate + " to " + r.toDate);
                else
                    deniedArray.add(r.fromDate + " to " + r.toDate);
            }
        }
        approvedObs = FXCollections.observableArrayList(approvedArray);
        pendingObs = FXCollections.observableArrayList(pendingArray);
        deniedObs = FXCollections.observableArrayList(deniedArray);
        listApr.setItems(approvedObs);
        listPen.setItems(pendingObs);
        listDen.setItems(deniedObs);
    }

}
