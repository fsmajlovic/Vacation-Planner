package Projekat;

import java.sql.*;
import java.util.ArrayList;


public class VacationDAO {
    private static VacationDAO instance;
    private Connection myConn;
    private PreparedStatement getUsersStmt, addNewRequestStmt, getRequestsStmt, approveRequestStmt, denyRequestStmt,
            addIntoDatabaseStmt, getApprovedRequestById, updateDaysLeftStmt, getDaysLeftByIdStmt, deleteUserByIdStmt,
            deleteRequestsForUserStmt, getUserByUsernameStmt, deleteRequestForTestingStmt, deleteUserByUsernameForTestingStmt;

    public static VacationDAO getInstance(){
        if(instance == null) instance = new VacationDAO();
        return instance;
    }

    public Connection getMyConn(){
        return myConn;
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
            getRequestsStmt = myConn.prepareStatement("select * from requests order by approved desc");
            approveRequestStmt = myConn.prepareStatement("update requests set approved = 1 where request_id = ?");
            denyRequestStmt = myConn.prepareStatement("update requests set approved = -1 where request_id = ?");
            String sqlprep = "insert into users(first_name, last_name, email, username," +
                    " password, admin_id, daysleft, requests_id)values (?, ?, ?, ?, ?, ?, ?, ?);";
            addIntoDatabaseStmt = myConn.prepareStatement(sqlprep);
            getApprovedRequestById = myConn.prepareStatement("select approved from requests where user_id = ?");
            updateDaysLeftStmt = myConn.prepareStatement("update users set daysleft = ? where id = ?");
            getDaysLeftByIdStmt = myConn.prepareStatement("select daysleft from users where username = ?");
            deleteUserByIdStmt = myConn.prepareStatement("delete from users where id = ?");
            deleteRequestsForUserStmt = myConn.prepareStatement("delete from requests where user_id = ?");
            getUserByUsernameStmt = myConn.prepareStatement("select u.id, u.first_name, u.last_name, u.email," +
                    " u.username, u.password, u.admin_id, u.daysleft, u.requests_id from users u where u.username = ?");
            deleteRequestForTestingStmt = myConn.prepareStatement("delete from requests where from_date = ? and to_date = ? and user_id = ?");
            deleteUserByUsernameForTestingStmt = myConn.prepareStatement("delete from users where username = ?");
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

    public ArrayList<Request> requests(){
        ArrayList<Request> requests = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = getRequestsStmt.executeQuery();
            while (rs.next()){
                Request req = new Request();
                req.setRequestId(rs.getInt("request_id"));
                req.setFromDate(rs.getString("from_date"));
                req.setToDate(rs.getString("to_date"));
                req.setApproved(rs.getInt("approved"));
                req.setUserId(rs.getInt("user_id"));
                requests.add(req);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public void approveRequest(int id){
        try {
            approveRequestStmt.setInt(1, id);
            approveRequestStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void denyRequest(int id) {
        try {
            denyRequestStmt.setInt(1, id);
            denyRequestStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User u){
        try {
            addIntoDatabaseStmt.setString(1, u.getFirstName());
            addIntoDatabaseStmt.setString(2, u.getLastName());
            addIntoDatabaseStmt.setString(3, u.getEmail());
            addIntoDatabaseStmt.setString(4, u.getUsername());
            addIntoDatabaseStmt.setString(5, u.getPassword());
            addIntoDatabaseStmt.setInt(6, 0);
            addIntoDatabaseStmt.setInt(7, 10);
            addIntoDatabaseStmt.setInt(8, 0);
            addIntoDatabaseStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isApproved(User u){
        try {
            getApprovedRequestById.setInt(1, u.getId());
            ResultSet rs = getApprovedRequestById.executeQuery();
            while(rs.next()){
                if(rs.getInt("approved") == 1)
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateDaysLeft(int daysLeft, User u){
        try {
            updateDaysLeftStmt.setInt(1, daysLeft);
            updateDaysLeftStmt.setInt(2, u.getId());
            updateDaysLeftStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getDaysLeftByUsername(String username){
        int daysleft = -1;
        try {
            getDaysLeftByIdStmt.setString(1, username);
            ResultSet rs = getDaysLeftByIdStmt.executeQuery();
            daysleft =  rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daysleft;
    }

    public void deleteUserById(User user){
        try {
            deleteUserByIdStmt.setInt(1, user.getId());
            deleteUserByIdStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUserByUsername(String username){
        try {
            deleteUserByUsernameForTestingStmt.setString(1, username);
            deleteUserByUsernameForTestingStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRequestsForUser(int user_id){
        try {
            deleteRequestsForUserStmt.setInt(1, user_id);
            deleteRequestsForUserStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserByUsername(String username){
        User u = new User();
        try {
            getUserByUsernameStmt.setString(1, username);
            ResultSet rs = null;
            rs = getUserByUsernameStmt.executeQuery();
            u.setId(rs.getInt("id"));
            u.setFirstName(rs.getString("first_name"));
            u.setLastName(rs.getString("last_name"));
            u.setEmail(rs.getString("email"));
            u.setUsername( rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setAdminId(rs.getInt("admin_id"));
            u.setDaysleft(rs.getInt("daysLeft"));
            u.setRequestsId(rs.getInt("requests_id"));
        } catch(SQLException e){
            e.printStackTrace();
        }
        return u;
    }

    public void deleteRequestForTesting(String from_date, String to_date, int user_id){
        try {
            deleteRequestForTestingStmt.setString(1, from_date);
            deleteRequestForTestingStmt.setString(2, to_date);
            deleteRequestForTestingStmt.setInt(3, user_id);
            deleteRequestForTestingStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
