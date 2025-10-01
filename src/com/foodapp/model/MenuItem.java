package com.foodapp.model;
import java.io.Serializable;
public class MenuItem implements Serializable {
    private int id;
    private String name;
    private String desc;
    private double price;

    public MenuItem() {}
    public MenuItem(int id, String name, String desc, double price) {
        this.id = id; this.name = name; this.desc = desc; this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDesc() { return desc; }
    public double getPrice() { return price; }

    @Override public String toString() {
        return id + ": " + name + " - " + desc + " : " + price;
    }
}
