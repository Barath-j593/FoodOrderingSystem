package com.foodapp.model;
import java.io.Serializable;
import java.util.List;
public class Order implements Serializable {
    private int id;
    private int customerId;
    private int restaurantId;
    private List<CartItem> items;
    private double total;
    private String status;
    private Integer deliveryStaffId;

    public Order() {}

    public Order(int id, int customerId, int restaurantId, List<CartItem> items) {
        this.id = id; this.customerId = customerId; this.restaurantId = restaurantId; this.items = items; this.total = calc(); this.status = "PENDING"; this.deliveryStaffId = null;
    }

    private double calc() { double s = 0; for(CartItem c: items) s += c.subtotal(); return s; }

    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public double getTotal() { return total; }
    public String getStatus() { return status; }
    public void setStatus(String s) { status = s; }
    public void assignDelivery(int staffId) { this.deliveryStaffId = staffId; this.status = "OUT_FOR_DELIVERY"; }
    public Integer getDeliveryStaffId() { return deliveryStaffId; }

    @Override public String toString() { return "Order#" + id + " cust=" + customerId + " total=" + total + " status=" + status; }
}
