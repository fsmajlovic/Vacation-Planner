package Projekat;

import java.sql.*;
import java.util.ArrayList;


public class VacationDAO {
    private static VacationDAO instance;
    private Connection myConn;
    private PreparedStatement getUsersStmt, addNewRequestStmt;

    public static VacationDAO getInstance(){
        if(instance == null) instance = new VacationDAO();
        return instance;
    }

    private VacationDAO(){
        try {
            myConn = DriverManager.getConnection("jdbc:sqlite:VPdatabase.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            getUsersStmt = myConn.prepareStatement("select * from users order by first_name desc");
            addNewRequestStmt = myConn.prepareStatement("insert into requests (from_date, to_date, approved, user_id) values (?,?,?,?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> users(){
        ResultSet rs = null;
        ArrayList<User> users = new ArrayList<>();
        try {
            rs = getUsersStmt.executeQuery();

                while (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setFirstName(rs.getString("first_name"));
                    u.setLastName(rs.getString("last_name"));
                    u.setEmail(rs.getString("email"));
                    u.setUsername( rs.getString("username"));
                    u.setPassword(rs.getString("password"));
                    u.setAdminId(rs.getInt("admin_id"));
                    u.setDaysleft(rs.getInt("daysLeft"));
                    u.setRequestsId(rs.getInt("requests_id"));
                    users.add(u);
                }
            } catch(SQLException e){
                e.printStackTrace();
            }
        return users;
    }

    public void addNewRequest(Request req){
        try {
            addNewRequestStmt.setString(1, req.getFromDate());
            addNewRequestStmt.setString(2, req.getToDate());
            addNewRequestStmt.setInt(3, 0);
            addNewRequestStmt.setInt(4, req.getUserId());
            addNewRequestStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
