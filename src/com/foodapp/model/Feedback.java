package com.foodapp.model;

import java.io.Serializable;
import java.util.Date;

public class Feedback implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int customerId;
    private String message;
    private Date date;

    public Feedback(int id, int customerId, String message) {
        this.id = id;
        this.customerId = customerId;
        this.message = message;
        this.date = new Date();
    }

    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public String getMessage() { return message; }
    public Date getDate() { return date; }

    @Override
    public String toString() {
        return "Feedback #" + id + " from Customer " + customerId + " on " + date + ": " + message;
    }
}
