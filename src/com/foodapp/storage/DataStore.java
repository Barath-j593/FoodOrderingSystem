package com.foodapp.storage;
import com.foodapp.exception.*;
import com.foodapp.model.*;
import com.foodapp.util.SerializationUtil;
import java.util.*;

public class DataStore {
    private static final String USERS = "data/users.dat";
    private static final String RESTS = "data/restaurants.dat";
    private static final String ORDERS = "data/orders.dat";

    private static List<User> users = new ArrayList<>();
    private static List<Restaurant> restaurants = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();

    static {
        Object u = SerializationUtil.load(USERS);
        if (u != null) users = (List<User>) u;
        Object r = SerializationUtil.load(RESTS);
        if (r != null) restaurants = (List<Restaurant>) r;
        Object o = SerializationUtil.load(ORDERS);
        if (o != null) orders = (List<Order>) o;
    }

    public static List<User> getUsers() { return users; }
    public static List<Restaurant> getRestaurants() { return restaurants; }
    public static List<Order> getOrders() { return orders; }

    public static void addUser(User u) { users.add(u); SerializationUtil.save(users, USERS); }
    public static void addRestaurant(Restaurant r) { restaurants.add(r); SerializationUtil.save(restaurants, RESTS); }
    public static void addOrder(Order o) { orders.removeIf(existing -> existing.getId() == o.getId()); orders.add(o); SerializationUtil.save(orders, ORDERS); }


    public static User findUserByEmail(String email) { for (User u : users) if (u.getEmail() != null && u.getEmail().equals(email)) return u; return null; }
    public static Restaurant findRestaurantById(int id) throws HotelNotFoundException { 
        for (Restaurant r : restaurants) if (r.getId() == id) return r;
        throw new HotelNotFoundException("HotelNotFoundException: Restaurant/Hotel ID " + id + " not found"); }
    public static Order findOrderById(int id) throws OrderNotFoundException { for (Order o : orders) if (o.getId() == id) return o; throw new OrderNotFoundException("OrderNotFoundException: Order with ID " + id + " not found"); }
    
    private static final String FEEDBACKS = "data/feedbacks.dat";
private static List<Feedback> feedbacks = new ArrayList<>();

static {
    Object f = SerializationUtil.load(FEEDBACKS);
    if (f != null) feedbacks = (List<Feedback>) f;
}

public static List<Feedback> getFeedbacks() { return feedbacks; }
public static void addFeedback(Feedback f) {
    feedbacks.add(f);
    SerializationUtil.save(feedbacks, FEEDBACKS);
}

}
