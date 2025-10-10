package com.foodapp.model;

import com.foodapp.storage.DataStore;
import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

    private int id;
    private int customerId;
    private String name;
    private int restaurantId;
    private List<CartItem> items;
    private double total;
    private String status;
    private Integer deliveryStaffId;

    // Default constructor
    public Order() {}

    // Constructor with parameters
    public Order(int id, int customerId, int restaurantId, List<CartItem> items) {
        this.id = id;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.items = items;
        this.total = calc();
        this.status = "PENDING";
        this.deliveryStaffId = null;
    }

    // Calculate total amount of order
    private double calc() {
        double s = 0;
        for (CartItem c : items)
            s += c.subtotal();
        return s;
    }

    // Getters and setters
    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public int getRestaurantId() { return restaurantId; }
    public double getTotal() { return total; }
    public String getStatus() { return status; }
    public void setStatus(String s) { status = s; }
    public void assignDelivery(int staffId) {
        this.deliveryStaffId = staffId;
        this.status = "OUT_FOR_DELIVERY";
    }
    public Integer getDeliveryStaffId() { return deliveryStaffId; }
    public List<CartItem> getItems() { return items; }

   
    public String getRestaurantName() {
        try {
            return DataStore.findRestaurantById(this.restaurantId).getName();
        } catch (Exception e) {
            return "Unknown Restaurant";
        }
    }

    @Override
    public String toString() {
        try {
            for (User u : DataStore.getUsers()) {
                if (u.getId() == customerId) {
                    name = u.getName();
                    break;
                }
            }
        } catch (Exception e) {
            name = "";
        }

        return "Order#" + id +
               " | CustomerID=" + customerId +
               " | Name=" + name +
               " | Restaurant=" + getRestaurantName() +
               " | Total=" + total +
               " | Status=" + status;
    }
}
