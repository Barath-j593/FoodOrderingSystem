package com.foodapp.service;

import com.foodapp.model.Order;
import com.foodapp.storage.DataStore;
import com.foodapp.util.Pair;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReportService {

    public void generateSummary() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("reports/summary.txt"))) {
            int totalRestaurants = DataStore.getRestaurants().size();
            int totalUsers = DataStore.getUsers().size();
            int totalOrders = DataStore.getOrders().size();

            double totalRevenue = 0.0;
            Map<String, Double> restaurantRevenue = new HashMap<>();

            // Calculate total revenue and restaurant-wise revenue
            for (Order o : DataStore.getOrders()) {
                if (o.getStatus().equalsIgnoreCase("PAID") || o.getStatus().equalsIgnoreCase("DELIVERED")) {
                    totalRevenue += o.getTotal();
                    String restaurantName = o.getRestaurantName();
                    restaurantRevenue.put(restaurantName,
                        restaurantRevenue.getOrDefault(restaurantName, 0.0) + o.getTotal());
                }
            }

            //  Create AnalyticsService to demonstrate bounded generics
            AnalyticsService<Double> analytics = new AnalyticsService<>();
            double avgRevenue = analytics.calculateAverage(new ArrayList<>(restaurantRevenue.values()));

            //  Write report details
            pw.println("===== System Summary Report =====");
            pw.println("Total Restaurants: " + totalRestaurants);
            pw.println("Total Users: " + totalUsers);
            pw.println("Total Orders: " + totalOrders);
            pw.println("Total Revenue: ₹" + totalRevenue);
            pw.println("---------------------------------");

            // Restaurant-wise revenue (with Pair<K,V>)
            pw.println("\n--- Revenue by Restaurant ---");
            for (Map.Entry<String, Double> entry : restaurantRevenue.entrySet()) {
                Pair<String, Double> pair = new Pair<>(entry.getKey(), entry.getValue());
                pw.println(pair.getKey() + ": ₹" + pair.getValue());
            }

            pw.println("---------------------------------");
            pw.println("Average Revenue per Restaurant: ₹" + avgRevenue);
            pw.println("=================================");
            System.out.println(" Report generated successfully with revenue analytics!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  Generates order details report
    public static void generateOrderReport() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("reports/orders.txt"))) {
            for (Order order : DataStore.getOrders()) {
                pw.println(order);
            }
            System.out.println(" Order report generated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
