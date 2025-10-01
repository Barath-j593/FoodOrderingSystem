package com.foodapp.model;
public class CashPayment extends PaymentMethod {
    @Override public boolean processPayment(double amount) { System.out.println("Cash payment accepted: " + amount); return true; }
}
