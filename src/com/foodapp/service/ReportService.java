package com.foodapp.service;
import com.foodapp.storage.DataStore;
import java.io.PrintWriter;
import java.io.FileWriter;
public class ReportService {
    public void generateSummary(){
        try(PrintWriter pw = new PrintWriter(new FileWriter("reports/summary.txt"))){
            pw.println("Total restaurants: "+DataStore.getRestaurants().size());
            pw.println("Total users: "+DataStore.getUsers().size());
            pw.println("Total orders: "+DataStore.getOrders().size());
        }catch(Exception e){ e.printStackTrace(); }
    }
}
