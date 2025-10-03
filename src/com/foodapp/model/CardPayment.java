package com.foodapp.model;
import com.foodapp.exception.CardNumberMismatchException;
public class CardPayment extends PaymentMethod {
    private String cardNumber;
    public CardPayment(String cardNumber) { this.cardNumber = cardNumber; }
    @Override public boolean processPayment(double amount) throws CardNumberMismatchException {
        if (cardNumber == null || cardNumber.length() < 12) throw new CardNumberMismatchException("CardNumberMismatchException: Invalid Card Number. Please Try Again");
        System.out.println("Card charged: " + amount);
        return true;
    }
}
