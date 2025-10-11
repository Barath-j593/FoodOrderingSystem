package com.foodapp.service;

import com.foodapp.model.Order;
import com.foodapp.storage.DataStore;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReportService implements Records, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public void displayDetails() {
        System.out.println("\n===== Displaying Report from Saved File =====");
        File file = new File("reports/summary.txt");

        if (!file.exists()) {
            System.out.println("No report found. Please generate a report first.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("=============================================\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

public void generateSummary() {
    try (PrintWriter pw = new PrintWriter(new FileWriter("reports/summary.txt"))) {
        int totalRestaurants = DataStore.getRestaurants().size();
        int totalUsers = DataStore.getUsers().size();
        int totalOrders = DataStore.getOrders().size();

        double totalRevenue = 0.0;

        // Maps for revenue and order counts per restaurant
        Map<String, Double> restaurantRevenue = new HashMap<>();
        Map<String, Integer> restaurantOrderCount = new HashMap<>();

        //  Initialize all restaurants with 0 revenue and 0 orders
        DataStore.getRestaurants().forEach(r -> {
            restaurantRevenue.put(r.getName(), 0.0);
            restaurantOrderCount.put(r.getName(), 0);
        });

        // Update revenue and order counts only for placed orders
        for (Order o : DataStore.getOrders()) {
            if (o.getStatus().equalsIgnoreCase("PAID") || o.getStatus().equalsIgnoreCase("DELIVERED")) {
                totalRevenue += o.getTotal();

                String restaurantName = o.getRestaurantName();

                // Update revenue
                restaurantRevenue.put(restaurantName,
                        restaurantRevenue.getOrDefault(restaurantName, 0.0) + o.getTotal());

                // Update order count
                restaurantOrderCount.put(restaurantName,
                        restaurantOrderCount.getOrDefault(restaurantName, 0) + 1);
            }
        }

        // Generic analytics for revenue and orders
        AnalyticsService<Double> revenueAnalytics = new AnalyticsService<>();
        AnalyticsService<Integer> orderAnalytics = new AnalyticsService<>();

        double avgRevenue = revenueAnalytics.calculateAverage(new ArrayList<>(restaurantRevenue.values()));
        double avgOrdersPerRestaurant = orderAnalytics.calculateAverage(new ArrayList<>(restaurantOrderCount.values()));

        pw.println("===== System Summary Report =====");
        pw.println("Total Restaurants: " + totalRestaurants);
        pw.println("Total Users: " + totalUsers);
        pw.println("Total Orders: " + totalOrders);
        pw.println("Total Revenue: Rs." + totalRevenue);
        pw.println("---------------------------------");

        pw.println("\n--- Revenue & Orders by Restaurant ---");
        for (String restaurant : restaurantRevenue.keySet()) {
            double revenue = restaurantRevenue.get(restaurant);
            int orderCount = restaurantOrderCount.get(restaurant);
            pw.println(restaurant + " -> Orders: " + orderCount + ", Revenue: Rs." + revenue);
        }

        pw.println("---------------------------------");
        pw.println("Average Revenue per Restaurant: Rs." + avgRevenue);
        pw.println("Average Orders per Restaurant: " + avgOrdersPerRestaurant);
        pw.println("=================================");

        System.out.println("Report file generated successfully with ALL restaurants included!");
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    public static void generateOrderReport() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("reports/orders.txt"))) {
            for (Order order : DataStore.getOrders()) {
                pw.println(order);
            }
            System.out.println("Order report generated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
