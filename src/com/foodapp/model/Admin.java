package com.foodapp.model;
public class Admin extends User {
    public Admin() { super(); }
    public Admin(int id, String name, String email, String passwordEncoded) { super(id,name,email,passwordEncoded); }
    @Override public String getRole() { return "Admin"; }
}
