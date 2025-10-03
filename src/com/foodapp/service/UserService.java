package com.foodapp.service;
import com.foodapp.storage.DataStore;
import com.foodapp.model.*;
import com.foodapp.util.CryptoUtil;
public class UserService {
    public User registerNormalUser(String name, String email, String password) {
        int id = DataStore.getUsers().size() + 1;
        String enc = CryptoUtil.encode(password);
        NormalUser u = new NormalUser(id, name, email, enc);
        DataStore.addUser(u);
        return u;
    }
    public User registerPremiumUser(String name, String email, String password) {
        int id = DataStore.getUsers().size() + 1;
        String enc = CryptoUtil.encode(password);
        PremiumUser u = new PremiumUser(id, name, email, enc, 0.0); // wallet starts at zero
        DataStore.addUser(u);
        return u;
    }

    public User registerAdmin(String name, String email, String password){
        int id = DataStore.getUsers().size() + 1;
        Admin a = new Admin(id, name, email, CryptoUtil.encode(password));
        DataStore.addUser(a);
        return a;
    }
    public User registerDelivery(String name, String email, String password){
        int id = DataStore.getUsers().size() + 1;
        DeliveryStaff d = new DeliveryStaff(id, name, email, CryptoUtil.encode(password));
        DataStore.addUser(d);
        return d;
    }
    public User login(String email, String password){
        User u = DataStore.findUserByEmail(email);
        if (u == null) return null;
        String enc = CryptoUtil.encode(password);
        if (u.checkPassword(enc)) return u;
        return null;
    }
}
