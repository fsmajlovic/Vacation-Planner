package Projekat;

import java.util.Date;

public class Request {
    public int request_id;
    public Date from;
    public Date to;

    public Request(int request_id, Date from, Date to) {
        this.request_id = request_id;
        this.from = from;
        this.to = to;
    }

    public Request() {
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
