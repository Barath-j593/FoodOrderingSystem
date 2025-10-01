package com.foodapp.model;
import java.io.Serializable;
public abstract class PaymentMethod implements Serializable {
    public abstract boolean processPayment(double amount) throws com.foodapp.exception.InsufficientBalanceException, com.foodapp.exception.CardNumberMismatchException;
    public boolean refund(double amount) { return true; }
}
