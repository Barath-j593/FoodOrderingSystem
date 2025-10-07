package com.foodapp.service;

import com.foodapp.model.Order;
import com.foodapp.storage.DataStore;
import java.io.FileWriter;
import java.io.PrintWriter;

public class ReportService {

    public void generateSummary() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("reports/summary.txt"))) {
            int totalRestaurants = DataStore.getRestaurants().size();
            int totalUsers = DataStore.getUsers().size();
            int totalOrders = DataStore.getOrders().size();

            double totalRevenue = 0.0;
            for (Order o : DataStore.getOrders()) {
                if (o.getStatus().equalsIgnoreCase("PAID") || o.getStatus().equalsIgnoreCase("DELIVERED")) {
                    totalRevenue += o.getTotal();
                }
            }

            pw.println("===== System Summary Report =====");
            pw.println("Total Restaurants: " + totalRestaurants);
            pw.println("Total Users: " + totalUsers);
            pw.println("Total Orders: " + totalOrders);
            pw.println("Total Revenue: ₹" + totalRevenue);
            pw.println("=================================");
            
            System.out.println("✅ Report generated successfully with revenue details!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
