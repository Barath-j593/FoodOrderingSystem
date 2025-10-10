# FoodOrderAndTrackingSystem (Java Console Application)


## 📘 Overview

The **FoodOrderAndTrackingSystem** is a Java-based console application that allows users to browse restaurants, order food, make payments, and track deliveries.  
It’s designed using **Object-Oriented Programming (OOP)** concepts and includes three roles — **Customer**, **Admin**, and **Delivery Staff** — each with specific privileges and menus.

This project demonstrates:
- Application of **Encapsulation, Inheritance, Polymorphism, and Abstraction**
- Use of **custom exceptions** and **file handling**
- **Report generation** and **persistent data management**

---

## ⚙️ Initial Setup & Execution

Before running the project, compile and execute it using the following commands:

```bash
# Step 1: Compile all Java files and store compiled classes in 'out' folder
$files = Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files

# Step 2: Run the main application
java -cp out com.foodapp.app.Main
```
---

## 🧩 Object-Oriented Concepts Used

1. Classes & Objects
- Many real world entities such as User, Cart, Restaurant, PaymentMethod, OrderService, etc. are created in the project
- Each Classes created has their respective attributes and methods. (E.g. id, name, cuisine and addMenuItem in Restaurant, name, email and getName, getEmail in User).
- Creation of Objects in the project (main program).
```bash
static UserService userService = new UserService();
static OrderService orderService = new OrderService();
```

2. Encapsulation
- Private fields in classes like User, Customer, CartItem, etc.
- Use of getter/setter methods to control access. (getName, setEmail, getOrderById, etc)
- Use of Validations in Setter. (email format in User, CardNumber format in CardPayment, etc.)

3. Inheritance
- User inherited by Admin, Customer and DeliveryStaff.
- Payment inherited by CashPayment, CardPayment, and WalletPayment.
- Implemented Method overriding for getRole in subclasses of User, processPayment in subclasses of Payment and several other methods.
- Use of “super” keyword in constructors.

4. Polymorphism
- Compile Time Polymorphism exhibited by Constructor overloading (multiple constructors)
- Run Time Polymorphism exhibited by method overriding.
- Dynamic method dispatch exhibited by PaymentMethod along with its subclass.
- Use of parent references: PaymentMethod is used for Cash/Card/WalletPayment.

5. Abstraction
- Use of abstract class User, PaymentMethod.
- Contains abstract methods getRole, processPayment.
- Defines a common behavior without exposing implementation details which will be overridden by their subclasses.

6. Constructors
- Use of default constructors in Box, Pair, CartItem.
- Use of parameterized Constructors in User, Order.
- Constructor overloading in User and several classes.
- Constructor chaining using super in PaymentMethod.

7. Access Modifiers
- Use of private access specifier to fields of class.  
E.g. private String passwordEncoded
- Use of public access specifiers to attributes of class.  
E.g. public int getId()
- Use of protected access specifiers to attributes of class.  
E.g. protected int id
- Use of default access specifiers.  
E.g. static Object f – in Datastore.java

8. Packages
- Organized Project Directory with user-defined packages.
- Imported and used classes from different packages.
- Refer "Project File Structure" given below.

9. Exception Handling
- Use of custom as well as default exceptions. (InsufficientBalanceExceptions, OrderNotFoundExceptions, etc.)
- Use of try-catch-finally block to handle them.
- try-catch blocks throughout main program and service classes.

10. Generics
- Use of generics in Pair<K,V> and implicitly in Collections.
- Bounded generics used in Box<T extends Number>
- E.g. public static <T> void printData(T data){ … }.

11. Collections Framework
- ArrayList in Cart, Customer, etc, HashSet for tracking IDs
- Use of iterators and enhanced for loops in main program and various service classes. 
- Operations like searching/filtering done on lists in main program and various service classes.
```bash
for(Order o: DataStore.getOrders()) System.out.println(o);
for (CartItem ci : target.getItems()) qty += ci.getQty();

orders.removeIf(existing -> existing.getId() == o.getId());
```

12. File Handling
- Use of FileHandling operations to read and write data of users, orders and restaurants using serialization.
- Data’s stored in file can retrieved again after restarting of system thus maintaining continuity.


---

## 🧩 Features

### 👤 Customer
- Register as **Normal** or **Premium User**
- Login with email and password
- Browse **restaurants** and **menus**
- Add food items to **cart**
- **Place orders** and choose a payment method:
  - 💵 Cash Payment
  - 💳 Card Payment
  - 👛 Wallet Payment (Premium users only)
- Track order delivery status
- Premium users enjoy **discounts** and can **add money** to their wallet

### 🧑‍💼 Admin
- Secure admin login
- Add new **restaurants** and **menu items**
- Generate detailed **summary reports**
- View all existing **orders**

### 🚚 Delivery Staff
- Login to delivery panel
- View **assigned orders**
- Update delivery status (**Out for Delivery → Delivered**)

---

## 🔑 Pre-Built Login Credentials

| Role | Email | Password |
|------|--------|----------|
| 🧍 Customer | `cust1@example.com` | `pass1` |
| 💎 Premium Customer | `prem1@example.com` | `prem123` |
| 🧑‍💼 Admin | `admin@example.com` | `admin123` |
| 🚚 Delivery Staff | `del1@example.com` | `del123` |

---

## 🧠 System Flow

### Main Menu
```
===== Online Food Ordering System =====
1. Customer Login / Register
2. Admin Login
3. Delivery Staff Login
4. Exit
```

### Customer Flow
```
View Restaurants → View Menu & Add to Cart → Place Order → Payment → Track Delivery
```

### Admin Flow
```
Add Restaurant → Add Menu Item → View Orders → Generate Reports
```

### Delivery Flow
```
View Assigned Orders → Update Delivery Status
```

---

## 🧾 Project File Structure

```
src/
├── README.md
│
├── app
│ └── Main.java
│
├── exception
│ ├── CardNumberMismatchException.java
│ ├── HotelNotFoundException.java
│ ├── InsufficientBalanceException.java
│ ├── OrderNotFoundException.java
│ └── WalletEmptyException.java
│
├── model
│ ├── Admin.java
│ ├── CardPayment.java
│ ├── Cart.java
│ ├── CartItem.java
│ ├── CashPayment.java
│ ├── Customer.java
│ ├── DeliveryStaff.java
│ ├── Feedback.java
│ ├── MenuItem.java
│ ├── NormalUser.java
│ ├── Order.java
│ ├── PaymentMethod.java
│ ├── PremiumUser.java
│ ├── Report.java
│ ├── Restaurant.java
│ ├── User.java
│ └── WalletPayment.java
│
├── service
│ ├── FeedbackService.java
│ ├── OrderService.java
│ ├── ReportService.java
│ └── UserService.java
│
├── storage
│ └── DataStore.java
│
└── util
├── Box.java
├── CryptoUtil.java
└── Pair.java
```

---

## 🧾 Report Generation

When an admin generates a report, it’s saved automatically to:

```
reports/summary.txt
```

### Report Includes:
- Total number of orders
- Total revenue
- Orders by restaurant
- Payment method breakdown
- Delivery performance stats

---

## 🧮 Modules Split-Up

| Module | Description |
|---------|-------------|
| **User Management** | Registration, login, and role-based access |
| **Restaurant Management** | Admins can add restaurants and menu items |
| **Order Management** | Customers can place and track orders |
| **Payment Processing** | Cash, Card, and Wallet payments |
| **Report Management** | Admin can generate sales and order reports |
| **Delivery Management** | Delivery staff can update delivery statuses |

---

## 🧩 Scope and Limitations

### ✅ Scope
- Simulates a complete food ordering and delivery flow
- Multi-role system (Customer, Admin, Delivery Staff)
- In-memory data persistence
- Secure login and report generation

### ⚠️ Limitations
- No real database connection (in-memory storage)
- Console-based user interface
- No concurrency or network-based communication
- Limited error validation on input

---

## 🏁 Inference

The **FoodOrderAndTrackingSystem** successfully implements an end-to-end simulation of an online food ordering platform.  
It demonstrates practical application of **OOP principles**, **data management**, and **user-role interaction**.  

This project provides a strong foundation for future expansion into GUI-based or web-based systems, and stands as a complete, functional demonstration of Java-based modular application development.

---

