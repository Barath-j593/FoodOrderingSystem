package com.foodapp.app;
import com.foodapp.exception.HotelNotFoundException;
import com.foodapp.exception.OrderNotFoundException;
import com.foodapp.exception.WalletEmptyException;
import com.foodapp.model.*;
import com.foodapp.service.*;
import com.foodapp.storage.DataStore;
import java.util.*;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static UserService userService = new UserService();
    static OrderService orderService = new OrderService();
    static ReportService reportService = new ReportService();
    static FeedbackService feedbackService = new FeedbackService();

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
            else if(ch.equals("4")) { System.out.println("Exiting the System. Thank You!"); break; }
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
            System.out.println("Register as:\n1. Normal User\n2. Premium User");
            String type = sc.nextLine();
            System.out.print("Name: "); String n=sc.nextLine();
            System.out.print("Email: "); String e=sc.nextLine();
            System.out.print("Password: "); String p=sc.nextLine();
            if(type.equals("1")) userService.registerNormalUser(n,e,p);
            else if(type.equals("2")) userService.registerPremiumUser(n,e,p);
            System.out.println("Registered. You can login now :)!.");
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
            System.out.println("6. Give Feedback"); 
            System.out.println("7. Logout");
            
            if (c instanceof PremiumUser) {
            
                System.out.println("8. Add Money to Wallet");
            }
            System.out.print("Choice: ");
            String ch = sc.nextLine();
            if(ch.equals("1")){
                for(Restaurant r: DataStore.getRestaurants()) System.out.println(r);
            } else if(ch.equals("2")){
                System.out.print("Enter restaurant id: "); int rid = Integer.parseInt(sc.nextLine());
                try {
                    Restaurant r = DataStore.findRestaurantById(rid); 
                    System.out.println("Menu:");
                    for(MenuItem mi: r.getMenu()) System.out.println(mi);
                    System.out.print("Enter menu id: "); int mid = Integer.parseInt(sc.nextLine());
                    MenuItem mi = r.findById(mid);
                    if(mi==null){ System.out.println("Invalid menu id"); continue; }
                    System.out.print("Qty: "); int q = Integer.parseInt(sc.nextLine());
                    cart.add(mi, q);
                    System.out.println("Added to cart"); 
                } catch (HotelNotFoundException x) {
                    System.out.println(x);
                    continue;  
                }
                
            } else if(ch.equals("3")){
                System.out.println(cart);
                System.out.print("Place order? (y/n): ");
                if(sc.nextLine().equalsIgnoreCase("y")){
                    System.out.print("Enter restaurant id for this order: "); int rid = Integer.parseInt(sc.nextLine());
                    try { 
                        Restaurant r = DataStore.findRestaurantById(rid);  
                        Order o = orderService.createOrder(c, r, cart);
                        if(o==null) System.out.println("Cart empty, cannot place order"); else System.out.println("Placed: "+o);
                    } catch (HotelNotFoundException e) {
                        System.out.println(e);
                        continue;  
                    }

                    
                    
                }
            } else if(ch.equals("4")){
                Order target = null;
                for(Order o: DataStore.getOrders()){
                    if(o.getCustomerId() == c.getId() && o.getStatus().equals("PENDING")) {
                        target = o;
                        break;
                    }
                }
                if(target == null){
                    System.out.println("No pending orders to pay");
                    continue;
                }

                double total = target.getTotal();  
                int qty = 0;
                for (CartItem ci : target.getItems()) {
                    qty += ci.getQty();
                }
                if (c instanceof PremiumUser) {
                    total = ((PremiumUser) c).applyDiscount(total, qty);
                    System.out.println("Discount applied! Amount to pay: " + total);
                } else {
                    System.out.println("Amount to pay: " + total);
                }

                if (c instanceof PremiumUser) { System.out.println("1. Cash  2. Card  3. Wallet");} else { System.out.println("1. Cash  2. Card");}
                
                String pay = sc.nextLine();

                try{
                    if(pay.equals("1")){ 
                        System.out.println("Processing...");
                        try {
                            Thread.sleep(2000); 
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            
                        }

                        new  CashPayment().processPayment(target.getTotal());
                        target.setStatus("PAID");
                    
                        try {
                            Thread.sleep(1500); 
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        orderService.assignDelivery(target);
                        System.out.println("Order is out for delivery."); 
                    }

                    else if(pay.equals("2")){ 
                        System.out.print("Enter card number: ");
                        String cn = sc.nextLine();
                        System.out.println("Processing your card payment...");
                        try {
                            Thread.sleep(2000);  
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        new CardPayment(cn).processPayment(target.getTotal()); // May throw error if card invalid
                        target.setStatus("PAID");
                        System.out.println("Payment successful....");

                        try {
                            Thread.sleep(1500);  
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        orderService.assignDelivery(target);
                        System.out.println("Order is out for delivery.");
                    }
                    
                    else if(pay.equals("3") && c instanceof PremiumUser){
                        try {
                            new  WalletPayment((PremiumUser)c).processPayment(total);
                            target.setStatus("PAID");
                            DataStore.addOrder(target);
                            System.out.println("Payment successful.");
                            System.out.println("Remaining wallet balance: " + c.getWallet());
                            orderService.assignDelivery(target);
                            System.out.println("Order is out for delivery.");
                        } catch (WalletEmptyException e) {
                            System.out.println(e);
                            continue;
                        }
                        
                    }
                    
                    else { System.out.println("Invalid payment method"); continue; }
                    DataStore.addOrder(target);
                    System.out.println("Payment successful.");
                    orderService.assignDelivery(target);
                    System.out.println("Order is out for delivery.");
                } catch(Exception e){ System.out.println("Payment failed: "+e.getMessage()); }
            } else if(ch.equals("5")){
                Set<Integer> shown = new HashSet<>();
                boolean hasOrders = false;
                for(Integer oid: c.getOrderIds()){
                    if (!shown.add(oid)) continue;
                    try {
                        Order o = DataStore.findOrderById(oid);
                        hasOrders = true;
                        System.out.println(o + (o.getDeliveryStaffId() != null ? (" | Delivery by: " + o.getDeliveryStaffId()) : ""));
                    
                    } catch (OrderNotFoundException e) {
                        System.out.println(e);
                    }
                    
                }
                if (!hasOrders) {
                    System.out.println("All orders are delivered /not placed any orders yet.");
                }
            } else if(ch.equals("7")) break;
            else if(ch.equals("8") && c instanceof PremiumUser){
                System.out.print("Amount to add: ");
                double amt = Double.parseDouble(sc.nextLine());
                ((PremiumUser)c).addToWallet(amt);
                System.out.println("Added to wallet. New balance: " + ((PremiumUser)c).getWallet());
            }
             else if(ch.equals("6")){
                System.out.print("Enter your feedback: ");
                String msg = sc.nextLine();
                feedbackService.giveFeedback(c, msg);   //  Added feedback
            }
        }
    }

    private static void adminMenu(Admin a){
        while(true){
            System.out.println("----- Admin Menu -----");
            System.out.println("1. Add Restaurant");
            System.out.println("2. Add Menu Item");
            System.out.println("3. Generate Report");
            System.out.println("4. View All Orders");
            System.out.println("5. Show Feedback");
            System.out.println("6. Logout");
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
                try {
                    Restaurant r = DataStore.findRestaurantById(rid);
                    System.out.print("Menu id: "); int mid = Integer.parseInt(sc.nextLine());
                    System.out.print("Name: "); String mn = sc.nextLine();
                    System.out.print("Desc: "); String md = sc.nextLine();
                    System.out.print("Price: "); double pr = Double.parseDouble(sc.nextLine());
                    r.addMenuItem(new MenuItem(mid, mn, md, pr));
                    System.out.println("Menu item added.");
                } catch (HotelNotFoundException e) {
                    System.out.println(e);
                    continue;  
                }

                
            } else if(ch.equals("3")){
                reportService.generateSummary(); ReportService.generateOrderReport(); System.out.println("Report generated at reports/summary.txt and reports/orders.txt");
            } else if(ch.equals("4")){
                for(Order o: DataStore.getOrders()) System.out.println(o);
            } else if(ch.equals("6")) break;
            else if(ch.equals("5")){
                feedbackService.showAllFeedbacks();   // Admin sees feedback
            }
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
                boolean found = false;
                Set<Integer> shown = new HashSet<>();
                for(Integer id: d.getAssigned()) { 
                    if (!shown.add(id)) continue;
                    try {
                        Order o = DataStore.findOrderById(id);
                        found = true;
                        System.out.println(o);
                    } catch (OrderNotFoundException x) {
                        System.out.println(x);
                    }
                                      
                     
                }
                if (!found) {
                    System.out.println("You have no assigned orders at the moment.");
                }
            } else if(ch.equals("2")){
                System.out.print("Order id: "); int oid = Integer.parseInt(sc.nextLine());
                try{
                    Order o = DataStore.findOrderById(oid);
                    o.setStatus("DELIVERED"); DataStore.addOrder(o); System.out.println("Marked delivered.");
                }
                catch (OrderNotFoundException e) {
                    System.out.println(e);
                }

            } else if(ch.equals("3")) break;
        }
    }

    private static void seedSampleData(){
        if(DataStore.getUsers().isEmpty()){
            userService.registerNormalUser("cust1","cust1@example.com","pass1");
            userService.registerPremiumUser("prem1","prem1@example.com","prem123");
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
