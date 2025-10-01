package com.foodapp.model;
import java.io.Serializable;
public class CartItem implements Serializable {
    private MenuItem item;
    private int qty;
    public CartItem() {}
    public CartItem(MenuItem item, int qty) { this.item = item; this.qty = qty; }
    public MenuItem getItem() { return item; }
    public int getQty() { return qty; }
    public double subtotal() { return item.getPrice() * qty; }
    @Override public String toString() { return item.getName() + " x " + qty + " = " + subtotal(); }
}
