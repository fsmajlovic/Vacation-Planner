package Projekat;

public class User {
    public int id;
    public String first_name, last_name, email, username, password;
    public int admin_id;
    public int daysleft;
    public int requests_id;

    public User(int id, String first_name, String last_name, String email, String username, String password, int admin_id, int daysleft, int requests_id) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.admin_id = admin_id;
        this.daysleft = daysleft;
        this.requests_id = requests_id;
    }

    public User() {
        this.id = 0;
        this.first_name = "";
        this.last_name = "";
        this.email = "";
        this.username = "";
        this.password = "";
        this.admin_id = 0;
        this.daysleft = 0;
        this.requests_id = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public int getDaysleft() {
        return daysleft;
    }

    public void setDaysleft(int daysleft) {
        this.daysleft = daysleft;
    }

    public int getRequests_id() {
        return requests_id;
    }

    public void setRequests_id(int requests_id) {
        this.requests_id = requests_id;
    }
}
