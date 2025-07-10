# E-commerce System Design

This project implements a simplified e-commerce system in Java, focusing on good object-oriented design principles.

## Features

* **Product Definition:** Name, price, quantity; support for expirable (with date) and shippable (with weight) attributes.
* **Shopping Cart:** Add products with quantity (checks stock and expiration).
* **Checkout Process:**
    * Calculates subtotal, shipping fees, total paid, and updates customer balance.
    * Error handling for empty cart, insufficient balance, out-of-stock, or expired products.
    * Integrates with a `ShippingService` for shippable items.
    * Updates product inventory.
 
## Project Structure

```
ecommerce-system/
├── pom.xml
└── src/
└── main/
└── java/
└── com/
└── ecommerce/
├── model/
│   ├── product/
│   │   ├── behavior/
│   │   │   ├── Impl/
│   │   │   │   ├── ExpirableImpl.java
│   │   │   │   └── ShippableImpl.java
│   │   │   ├── Expirable.java
│   │   │   └── Shippable.java
│   │   └── Product.java
│   ├── Cart.java
│   └── Customer.java
│
├── service/
│   └── shipping/
│       ├── ShippingService.java
│       └── StandardShippingService.java
│
└── system/
├── ECommerceSystem.java
└── Main.java
```

## Technologies Used

* Java 17+
* Maven
* Lombok

## Setup and Run

### Prerequisites

* JDK 17+
* Apache Maven

### Steps

1.  **Clone:** `git clone https://github.com/malek-attia/E-Commerce-Task.git`
2.  **Build:** `cd ecommerce-system && mvn clean install`
3.  **Run:** `java -cp target/ecommerce-system-1.0-SNAPSHOT.jar com.ecommerce.system.ECommerceSystem`

    (or `mvn exec:java -Dexec.mainClass="com.ecommerce.system.ECommerceSystem"`)

# Assumptions

* Simple linear shipping fee calculation.
* Basic error handling (console output, stops checkout).
* Product quantities are mutable directly.
* No explicit thread safety (single-user context assumed).
* `double` for balance (not `BigDecimal` for precision in financial apps).

## Screenshots from running app.

### Adding sample products to the inventory
<img width="668" height="106" alt="Image" src="https://github.com/user-attachments/assets/7084b115-ef89-4f63-b0d2-7c5dc114b7b0" />

### Successful Checkout with all types of products case
<img width="668" height="471" alt="Image" src="https://github.com/user-attachments/assets/14c7cfe0-edaf-4dc1-902a-905933cc377e" />

### Test possible Error Cases
<img width="668" height="647" alt="Image" src="https://github.com/user-attachments/assets/3b51eac1-9df2-4df0-95e1-93a87c02de2e" />

### Non-shippable product checkout
<img width="668" height="386" alt="Image" src="https://github.com/user-attachments/assets/8f1e7bb9-271f-4f43-9d1b-4c418009a6ce" />
