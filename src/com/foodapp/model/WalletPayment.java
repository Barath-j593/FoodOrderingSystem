package com.foodapp.model;
public class WalletPayment extends PaymentMethod {
    private Customer customer;
    public WalletPayment(Customer c) { this.customer = c; }
    @Override public boolean processPayment(double amount) throws com.foodapp.exception.InsufficientBalanceException {
        if (customer.getWallet() < amount) throw new com.foodapp.exception.InsufficientBalanceException("Wallet low");
        customer.deductFromWallet(amount);
        System.out.println("Wallet paid: " + amount);
        return true;
    }
}
