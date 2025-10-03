package com.foodapp.model;

public class NormalUser extends Customer {
    public NormalUser(int id, String name, String email, String passwordEncoded) {
        super(id, name, email, passwordEncoded, 0.0); // wallet not used
    }
    @Override public String getRole() { return "NormalUser"; }
    @Override public double getWallet() { throw new UnsupportedOperationException("Normal users don't have wallets!"); }
    @Override public void addToWallet(double amt) { throw new UnsupportedOperationException("Normal users don't have wallets!"); }
    @Override public void deductFromWallet(double amt) { throw new UnsupportedOperationException("Normal users don't have wallets!"); }
}
