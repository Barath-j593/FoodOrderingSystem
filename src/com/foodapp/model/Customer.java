package com.foodapp.model;

import java.util.ArrayList;
import java.util.List;
import com.foodapp.exception.InsufficientBalanceException;

public class Customer extends User {
    private double wallet;
    private List<Integer> orderIds = new ArrayList<>();

    public Customer() { super(); }

    public Customer(int id, String name, String email, String passwordEncoded, double wallet) {
        super(id, name, email, passwordEncoded);
        this.wallet = wallet;
    }

    @Override public String getRole() { return "Customer"; }

    public double getWallet() { return wallet; }
    public void addToWallet(double amt) { if(amt<=0) throw new IllegalArgumentException("Amount > 0"); wallet += amt; }
    public void deductFromWallet(double amt) throws InsufficientBalanceException {
        if(amt > wallet) throw new InsufficientBalanceException("Insufficient wallet balance");
        wallet -= amt;
    }

    public void addOrderId(int id) { orderIds.add(id); }
    public List<Integer> getOrderIds() { return orderIds; }
}
