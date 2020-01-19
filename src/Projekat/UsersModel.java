package Projekat;

import java.sql.*;
import java.util.ArrayList;

public class UsersModel {
    public ArrayList<User> listUsers = new ArrayList<>();
    PreparedStatement getUsersStmt;
    public ArrayList<User> getListUsers() {
        return listUsers;
    }

    public void setListUsers(ArrayList<User> listUsers) {
        this.listUsers = listUsers;
    }


}
