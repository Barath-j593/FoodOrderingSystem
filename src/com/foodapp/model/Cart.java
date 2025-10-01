package com.foodapp.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Cart implements Serializable {
    private List<CartItem> items = new ArrayList<>();
    public void add(MenuItem m, int qty) { if(m==null) return; items.add(new CartItem(m, qty)); }
    public List<CartItem> getItems() { return items; }
    public double total() { double t=0; for(CartItem c: items) t += c.subtotal(); return t; }
    public void clear() { items.clear(); }
    @Override public String toString() { if(items.isEmpty()) return "Cart is empty"; StringBuilder sb = new StringBuilder(); for(CartItem c: items) sb.append(c).append("\n"); sb.append("Total: ").append(total()); return sb.toString(); }
}
