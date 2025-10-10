package com.foodapp.model;
import java.io.Serializable;
public abstract class User implements Serializable {
    protected int id;
    private String name;
    private String email;
    private String passwordEncoded;

    public User() {}

    public User(int id, String name, String email, String passwordEncoded) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordEncoded = passwordEncoded;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    protected String getPasswordEncoded() { return passwordEncoded; }

    public void setName(String name) { if(name==null||name.isBlank()) throw new IllegalArgumentException("Name required"); this.name = name; }
    public void setEmail(String email) { if(email==null||!email.contains("@")) throw new IllegalArgumentException("Invalid email"); this.email = email; }

    public boolean checkPassword(String encoded) { return passwordEncoded != null && passwordEncoded.equals(encoded); }

    public String getRole() { return "User"; }
}
