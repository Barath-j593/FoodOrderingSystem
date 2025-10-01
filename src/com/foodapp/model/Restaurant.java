package com.foodapp.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Restaurant implements Serializable {
    private int id;
    private String name;
    private String cuisine;
    private List<MenuItem> items = new ArrayList<>();

    public Restaurant() {}

    public Restaurant(int id, String name, String cuisine) {
        this.id = id; this.name = name; this.cuisine = cuisine;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCuisine() { return cuisine; }

    public void addMenuItem(MenuItem m) { items.add(m); }
    public List<MenuItem> getMenu() { return Collections.unmodifiableList(items); }

    public MenuItem findById(int mid) {
        for (MenuItem m : items) if (m.getId() == mid) return m;
        return null;
    }

    @Override public String toString() {
        return id + ": " + name + " (" + cuisine + ")";
    }
}
