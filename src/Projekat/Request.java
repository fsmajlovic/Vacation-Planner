package Projekat;

import java.util.Date;

public class Request {
    public int requestId;
    public String fromDate;
    public String toDate;
    public int approved;
    public int userId;

    public Request(int requestId, String fromDate, String toDate, int approved, int userId) {
        this.requestId = requestId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.approved = approved;
        this.userId = userId;
    }

    public Request() {
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
