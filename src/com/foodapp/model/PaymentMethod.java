package com.foodapp.model;

import com.foodapp.exception.*;
import java.io.Serializable;

public abstract class PaymentMethod implements Serializable {
    public abstract boolean processPayment(double amount) throws InsufficientBalanceException, CardNumberMismatchException, WalletEmptyException;
    public boolean refund(double amount) { return true; }
}
