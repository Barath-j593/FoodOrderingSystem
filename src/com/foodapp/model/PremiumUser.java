package com.foodapp.model;

public class PremiumUser extends Customer {
    public PremiumUser(int id, String name, String email, String passwordEncoded, double wallet) {
        super(id, name, email, passwordEncoded, wallet);
    }
    @Override public String getRole() { return "PremiumUser"; }
    
    public double applyDiscount(double amount, int qty) {
        if(qty == 1) return amount * 0.85; 
        if(qty >= 2 && qty <= 5) return amount * 0.90;  
        if(qty > 5) return amount * 0.7;  
        return amount;
    }
}
