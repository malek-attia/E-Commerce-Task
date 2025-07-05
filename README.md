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
