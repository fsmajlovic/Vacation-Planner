package Projekat;

public class User {
    public int id;
    public String firstName, lastName, email, username, password;
    public int adminId;
    public int daysleft;
    public int requestsId;

    public User(int id, String firstName, String lastName, String email, String username, String password, int adminId, int daysleft, int requestsId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.adminId = adminId;
        this.daysleft = daysleft;
        this.requestsId = requestsId;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getDaysleft() {
        return daysleft;
    }

    public void setDaysleft(int daysleft) {
        this.daysleft = daysleft;
    }

    public int getRequestsId() {
        return requestsId;
    }

    public void setRequestsId(int requestsId) {
        this.requestsId = requestsId;
    }

    @Override
    public String toString() {
        return "You've got a new request by " + getUsername();
    }
}
