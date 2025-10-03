package com.foodapp.model;

import com.foodapp.exception.InsufficientBalanceException;
import com.foodapp.exception.WalletEmptyException;

public class WalletPayment extends PaymentMethod {
    private Customer customer;
    public WalletPayment(Customer c) { this.customer = c; }
    @Override 
    public boolean processPayment(double amount) throws InsufficientBalanceException, WalletEmptyException {
        if (customer.getWallet() == 0) throw new WalletEmptyException("WalletEmptyException: Wallet is empty, cannot process payment...");
        if (customer.getWallet() < amount) throw new InsufficientBalanceException("InsufficientBalanceException: Wallet Low");
        customer.deductFromWallet(amount);
        System.out.println("Wallet paid: " + amount);
        return true;
    }
}
