package com.foodapp.model;
import com.foodapp.exception.CardNumberMismatchException;
public class CardPayment extends PaymentMethod {
    private String cardNumber;
    public CardPayment(String cardNumber) { this.cardNumber = cardNumber; }
    @Override public boolean processPayment(double amount) throws com.foodapp.exception.CardNumberMismatchException {
        if (cardNumber == null || cardNumber.length() < 12) throw new CardNumberMismatchException("Invalid card");
        System.out.println("Card charged: " + amount);
        return true;
    }
}
