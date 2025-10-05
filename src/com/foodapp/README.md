# 🍔 FoodOrderAndTrackingSystem (Java Console Application)

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

## 📘 Overview

The **FoodOrderAndTrackingSystem** is a Java-based console application that allows users to browse restaurants, order food, make payments, and track deliveries.  
It’s designed using **Object-Oriented Programming (OOP)** concepts and includes three roles — **Customer**, **Admin**, and **Delivery Staff** — each with specific privileges and menus.

This project demonstrates:
- Application of **Encapsulation, Inheritance, Polymorphism, and Abstraction**
- Use of **custom exceptions** and **file handling**
- **Report generation** and **persistent data management**

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



## 🧩 Object-Oriented Concepts Used



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

