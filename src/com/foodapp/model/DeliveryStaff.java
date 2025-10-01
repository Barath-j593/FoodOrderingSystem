package com.foodapp.model;
import java.util.ArrayList;
import java.util.List;
public class DeliveryStaff extends User {
    private List<Integer> assigned = new ArrayList<>();
    public DeliveryStaff() { super(); }
    public DeliveryStaff(int id, String name, String email, String passwordEncoded) { super(id,name,email,passwordEncoded); }
    @Override public String getRole() { return "Delivery"; }
    public void assignOrder(int id) { assigned.add(id); }
    public List<Integer> getAssigned() { return assigned; }
}
