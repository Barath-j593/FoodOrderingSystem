package com.foodapp.app;
import java.util.*;
import com.foodapp.model.*;
import com.foodapp.service.*;
import com.foodapp.storage.DataStore;
import com.foodapp.util.CryptoUtil;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static UserService userService = new UserService();
    static OrderService orderService = new OrderService();
    static ReportService reportService = new ReportService();

    public static void main(String[] args) {
        new java.io.File("data").mkdirs();
        new java.io.File("reports").mkdirs();

        seedSampleData();

        while(true){
            System.out.println("===== Online Food Ordering System =====");
            System.out.println("1. Customer Login / Register");
            System.out.println("2. Admin Login");
            System.out.println("3. Delivery Staff Login");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            String ch = sc.nextLine();
            if(ch.equals("1")) customerEntry();
            else if(ch.equals("2")) adminEntry();
            else if(ch.equals("3")) deliveryEntry();
            else if(ch.equals("4")) { System.out.println("Bye"); break; }
        }
    }

    private static void customerEntry(){
        System.out.println("1. Login\n2. Register\n3. Back");
        String c = sc.nextLine();
        if(c.equals("1")){
            System.out.print("Email: "); String e = sc.nextLine();
            System.out.print("Password: "); String p = sc.nextLine();
            User u = userService.login(e,p);
            if(u instanceof Customer) customerMenu((Customer)u); else System.out.println("Invalid credentials or not a customer");
        } else if(c.equals("2")){
            System.out.print("Name: "); String n=sc.nextLine();
            System.out.print("Email: "); String e=sc.nextLine();
            System.out.print("Password: "); String p=sc.nextLine();
            userService.registerCustomer(n,e,p); System.out.println("Registered. Login now.");
        }
    }

    private static void adminEntry(){
        System.out.print("Email: "); String e=sc.nextLine();
        System.out.print("Password: "); String p=sc.nextLine();
        User u = userService.login(e,p);
        if(u instanceof Admin) adminMenu((Admin)u); else System.out.println("Invalid admin credentials");
    }

    private static void deliveryEntry(){
        System.out.print("Email: "); String e=sc.nextLine();
        System.out.print("Password: "); String p=sc.nextLine();
        User u = userService.login(e,p);
        if(u instanceof DeliveryStaff) deliveryMenu((DeliveryStaff)u); else System.out.println("Invalid delivery credentials");
    }

    private static void customerMenu(Customer c){
        Cart cart = new Cart();
        while(true){
            System.out.println("----- Customer Menu ("+c.getName()+") -----");
            System.out.println("1. View Restaurants");
            System.out.println("2. View Menu & Add to Cart");
            System.out.println("3. View Cart & Place Order");
            System.out.println("4. Make Payment");
            System.out.println("5. Track Delivery");
            System.out.println("6. Logout");
            System.out.print("Choice: ");
            String ch = sc.nextLine();
            if(ch.equals("1")){
                for(Restaurant r: DataStore.getRestaurants()) System.out.println(r);
            } else if(ch.equals("2")){
                System.out.print("Enter restaurant id: "); int rid = Integer.parseInt(sc.nextLine());
                Restaurant r = DataStore.findRestaurantById(rid); if(r==null){ System.out.println("No such restaurant"); continue; }
                System.out.println("Menu:");
                for(MenuItem mi: r.getMenu()) System.out.println(mi);
                System.out.print("Enter menu id: "); int mid = Integer.parseInt(sc.nextLine());
                MenuItem mi = r.findById(mid);
                if(mi==null){ System.out.println("Invalid menu id"); continue; }
                System.out.print("Qty: "); int q = Integer.parseInt(sc.nextLine());
                cart.add(mi, q);
                System.out.println("Added to cart");
            } else if(ch.equals("3")){
                System.out.println(cart);
                System.out.print("Place order? (y/n): ");
                if(sc.nextLine().equalsIgnoreCase("y")){
                    System.out.print("Enter restaurant id for this order: "); int rid = Integer.parseInt(sc.nextLine());
                    Restaurant r = DataStore.findRestaurantById(rid); if(r==null){ System.out.println("Invalid restaurant"); continue; }
                    Order o = orderService.createOrder(c, r, cart);
                    if(o==null) System.out.println("Cart empty, cannot place order"); else System.out.println("Placed: "+o);
                }
            } else if(ch.equals("4")){
                Order target = null;
                for(Order o: DataStore.getOrders()){
                    if(o.getCustomerId()==c.getId() && o.getStatus().equals("PENDING")) target = o;
                }
                if(target==null){ System.out.println("No pending orders to pay"); continue; }
                System.out.println("Amount to pay: "+target.getTotal());
                System.out.println("1. Cash  2. Card  3. Wallet");
                String pay = sc.nextLine();
                try{
                    if(pay.equals("1")){ new com.foodapp.model.CashPayment().processPayment(target.getTotal()); target.setStatus("PAID"); }
                    else if(pay.equals("2")){ System.out.print("Enter card number: "); String cn = sc.nextLine(); new com.foodapp.model.CardPayment(cn).processPayment(target.getTotal()); target.setStatus("PAID"); }
                    else if(pay.equals("3")){ new com.foodapp.model.WalletPayment(c).processPayment(target.getTotal()); target.setStatus("PAID"); }
                    else { System.out.println("Invalid payment method"); continue; }
                    DataStore.addOrder(target);
                    System.out.println("Payment successful.");
                    orderService.assignDelivery(target);
                    System.out.println("Order is out for delivery.");
                } catch(Exception ex){ System.out.println("Payment failed: "+ex.getMessage()); }
            } else if(ch.equals("5")){
                for(Integer oid: c.getOrderIds()){
                    Order o = DataStore.findOrderById(oid);
                    if(o!=null) System.out.println(o + (o.getDeliveryStaffId()!=null ? (" | Delivery by: "+o.getDeliveryStaffId()) : ""));
                }
            } else if(ch.equals("6")) break;
        }
    }

    private static void adminMenu(Admin a){
        while(true){
            System.out.println("----- Admin Menu -----");
            System.out.println("1. Add Restaurant");
            System.out.println("2. Add Menu Item");
            System.out.println("3. Generate Report");
            System.out.println("4. View All Orders");
            System.out.println("5. Logout");
            System.out.print("Choice: ");
            String ch = sc.nextLine();
            if(ch.equals("1")){
                System.out.print("Name: "); String n = sc.nextLine();
                System.out.print("Cuisine: "); String cu = sc.nextLine();
                int id = DataStore.getRestaurants().size()+1;
                Restaurant r = new Restaurant(id, n, cu);
                DataStore.addRestaurant(r);
                System.out.println("Added restaurant.");
            } else if(ch.equals("2")){
                System.out.print("Restaurant id: "); int rid = Integer.parseInt(sc.nextLine());
                Restaurant r = DataStore.findRestaurantById(rid); if(r==null){ System.out.println("No such restaurant"); continue; }
                System.out.print("Menu id: "); int mid = Integer.parseInt(sc.nextLine());
                System.out.print("Name: "); String mn = sc.nextLine();
                System.out.print("Desc: "); String md = sc.nextLine();
                System.out.print("Price: "); double pr = Double.parseDouble(sc.nextLine());
                r.addMenuItem(new MenuItem(mid, mn, md, pr));
                System.out.println("Menu item added.");
            } else if(ch.equals("3")){
                reportService.generateSummary(); System.out.println("Report generated at reports/summary.txt");
            } else if(ch.equals("4")){
                for(Order o: DataStore.getOrders()) System.out.println(o);
            } else if(ch.equals("5")) break;
        }
    }

    private static void deliveryMenu(DeliveryStaff d){
        while(true){
            System.out.println("----- Delivery Menu ("+d.getName()+") -----");
            System.out.println("1. View Assigned Orders");
            System.out.println("2. Update Delivery Status");
            System.out.println("3. Logout");
            System.out.print("Choice: ");
            String ch = sc.nextLine();
            if(ch.equals("1")){
                for(Integer id: d.getAssigned()){ Order o = DataStore.findOrderById(id); if(o!=null) System.out.println(o); }
            } else if(ch.equals("2")){
                System.out.print("Order id: "); int oid = Integer.parseInt(sc.nextLine());
                Order o = DataStore.findOrderById(oid); if(o==null){ System.out.println("No such order"); continue; }
                o.setStatus("DELIVERED"); DataStore.addOrder(o); System.out.println("Marked delivered.");
            } else if(ch.equals("3")) break;
        }
    }

    private static void seedSampleData(){
        if(DataStore.getUsers().isEmpty()){
            userService.registerCustomer("cust1","cust1@example.com","pass1");
            userService.registerAdmin("admin","admin@example.com","admin123");
            userService.registerDelivery("del1","del1@example.com","del123");
        }
        if(DataStore.getRestaurants().isEmpty()){
            Restaurant p = new Restaurant(1,"Pizza Palace","Italian");
            p.addMenuItem(new MenuItem(101,"Margherita","Classic",250));
            p.addMenuItem(new MenuItem(102,"Farmhouse","Veg",300));
            p.addMenuItem(new MenuItem(103,"Cheese Burst","Cheesy",350));
            Restaurant b = new Restaurant(2,"Burger House","FastFood");
            b.addMenuItem(new MenuItem(201,"Veg Burger","Tasty",120));
            b.addMenuItem(new MenuItem(202,"Chicken Burger","Spicy",180));
            b.addMenuItem(new MenuItem(203,"Fries","Crispy",80));
            DataStore.addRestaurant(p); DataStore.addRestaurant(b);
        }
    }
}
