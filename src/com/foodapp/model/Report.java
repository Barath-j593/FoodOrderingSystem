package com.foodapp.model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class Report {
    public static <T> void printData(T data) { System.out.println(data); }
    public static void appendOrder(String line) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("reports/orders.txt", true))) {
            pw.println(line);
        } catch (IOException e) { e.printStackTrace(); }
    }
    public static void generateSummary() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("reports/summary.txt"))) {
            pw.println("Report generated");
        } catch (IOException e) { e.printStackTrace(); }
    }
    public static void generateOrderReport() {
        try (
            BufferedReader br = new BufferedReader(new FileReader("order.dat"));
            PrintWriter pw = new PrintWriter(new FileWriter("reports/orders.txt", true)) // append mode
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                pw.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

}
