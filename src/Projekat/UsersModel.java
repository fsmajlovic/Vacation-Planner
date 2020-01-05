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

    public void fill() {
        Connection myConn;
        User current = new User();
        try {
            myConn = DriverManager.getConnection("jdbc:sqlite:VPdatabase.db");
            String sqlgetUsers = "select * from users;";
            getUsersStmt = myConn.prepareStatement(sqlgetUsers);
            ResultSet rs = getUsersStmt.executeQuery();
            current.setId(rs.getInt("id"));
            current.setFirst_name(rs.getString("first_name"));
            current.setLast_name(rs.getString("last_name"));
            current.setEmail(rs.getString("email"));
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
