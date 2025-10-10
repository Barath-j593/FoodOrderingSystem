package com.foodapp.service;
import com.foodapp.model.*;
import com.foodapp.storage.DataStore;
import java.util.*;
public class OrderService implements Records{

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
                o.assignDelivery((DeliveryStaff) u);
                DataStore.addOrder(o);
                DataStore.addUser(u);
                break;
            }
        }
    }

    public void displayDetails() {
        List<Order> orders = DataStore.getOrders();

        if (orders == null || orders.isEmpty()) {
            System.out.println("\nNo orders found in the system.\n");
            return;
        }

        System.out.println("\n===== All Orders =====");
        for (Order o : orders) {
            System.out.print("Order ID: " + o.getId());
            System.out.print(" | Customer ID: " + o.getCustomerId());
            System.out.print(" | Restaurant ID: " + o.getRestaurantId());
            System.out.print(" | Total: Rs." + o.getTotal());
            System.out.print(" | Status: " + o.getStatus());
            if (o.getDeliveryStaff() != null) {
                System.out.print(" | Delivery Staff: " + o.getDeliveryStaff().getName());
            } else {
                System.out.print(" | Delivery Staff: [Unassigned]");
            }


        }
        System.out.println("\n======================================\n");
    }
}
