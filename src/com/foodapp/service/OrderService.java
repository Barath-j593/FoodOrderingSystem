package com.foodapp.service;
import com.foodapp.model.*;
import com.foodapp.storage.DataStore;
import java.util.*;
public class OrderService {

    private static int NEXT;
    static {
        try {
            // Initialize NEXT to max existing order ID + 1
            List<Order> orders = DataStore.getOrders();
            if (orders != null && !orders.isEmpty()) {
                int maxId = orders.stream().mapToInt(Order::getId).max().orElse(0);
                NEXT = maxId + 1;
            }
        } catch (Exception e) {
            NEXT = 1;
        }
    }
    
    public Order createOrder(Customer c, Restaurant r, Cart cart){
        if (cart.getItems().isEmpty()) return null;
        Order o = new Order(NEXT++, c.getId(), r.getId(), new ArrayList<>(cart.getItems()));
        DataStore.addOrder(o);
        c.addOrderId(o.getId());
        //Report.appendOrder(o.toString());
        cart.clear();
        return o;
    }
    public void assignDelivery(Order o){
        for (User u : DataStore.getUsers()){
            if (u instanceof DeliveryStaff){
                ((DeliveryStaff)u).assignOrder(o.getId());
                o.assignDelivery(u.getId());
                DataStore.addOrder(o);
                DataStore.addUser(u);
                break;
            }
        }
    }
}
