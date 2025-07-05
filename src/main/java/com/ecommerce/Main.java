package com.ecommerce;

import com.ecommerce.model.Cart;
import com.ecommerce.model.Customer;
import com.ecommerce.model.product.Product;
import com.ecommerce.model.product.behavior.Impl.ExpirableImpl;
import com.ecommerce.model.product.behavior.Impl.ShippableImpl;
import com.ecommerce.service.shipping.ShippingService;
import com.ecommerce.service.shipping.StandardShippingService;
import com.ecommerce.system.ECommerceSystem;

import java.time.LocalDate;

public class Main {

    // Main method for demonstration
    public static void main(String[] args) {
        // Initialize products
        Product cheese = Product.builder()
                .name("Cheese")
                .price(100.0)
                .quantity(10)
                .expirable(new ExpirableImpl(LocalDate.now().plusDays(30))) // Expires in 30 days
                .shippable(new ShippableImpl(0.2, "Cheese")) // 0.2 kg per unit
                .build();

        Product tv = Product.builder()
                .name("TV")
                .price(1500.0)
                .quantity(5)
                .shippable(new ShippableImpl(10.0, "TV")) // 10 kg per unit
                .build();

        Product scratchCard = Product.builder()
                .name("Mobile Scratch Card")
                .price(50.0)
                .quantity(20)
                .build();

        Product biscuits = Product.builder()
                .name("Biscuits")
                .price(75.0)
                .quantity(15)
                .expirable(new ExpirableImpl(LocalDate.now().plusDays(60)))
                .shippable(new ShippableImpl(0.3, "Biscuits")) // 0.3 kg per unit
                .build();

        Product expiredMilk = Product.builder()
                .name("Expired Milk")
                .price(20.0)
                .quantity(5)
                .expirable(new ExpirableImpl(LocalDate.now().minusDays(1))) // Expired yesterday
                .shippable(new ShippableImpl(1.0, "Expired Milk"))
                .build();

        Product lowStockItem = Product.builder()
                .name("Low Stock Item")
                .price(10.0)
                .quantity(1) // Only 1 in stock
                .build();

        // Initialize customers
        Customer alice = new Customer("Alice", 2000.0);
        Customer bob = new Customer("Bob", 100.0);

        ShippingService standardShipping = new StandardShippingService();
        ECommerceSystem system = new ECommerceSystem(standardShipping);

        // --- Use Case 1: Successful Checkout with all types of products ---
        Cart cart1 = new Cart();
        try {
            cart1.addProduct(cheese, 2);
            cart1.addProduct(tv, 1);
            cart1.addProduct(scratchCard, 1);
            cart1.addProduct(biscuits, 1);
        } catch (IllegalArgumentException e) {
            System.out.println("Cart Addition Error: " + e.getMessage());
        }
        system.checkout(alice, cart1);
        System.out.println("\n------------------------------------------------");

        // --- Use Case 2: Insufficient Balance ---
        Cart cart2 = new Cart();
        try {
            cart2.addProduct(tv, 1); // TV costs 1500, Bob has 50
        } catch (IllegalArgumentException e) {
            System.out.println("Cart Addition Error: " + e.getMessage());
        }
        system.checkout(bob, cart2);
        System.out.println("\n------------------------------------------------");

        // --- Use Case 3: Cart is empty ---
        Cart cart3 = new Cart();
        system.checkout(alice, cart3);
        System.out.println("\n------------------------------------------------");

        // --- Use Case 4: Product out of stock ---
        Cart cart4 = new Cart();
        try {
            cart4.addProduct(lowStockItem, 2); // Tries to add 2, only 1 available
        } catch (IllegalArgumentException e) {
            System.out.println("Cart Addition Error: " + e.getMessage());
        }
        system.checkout(alice, cart4);
        System.out.println("\n------------------------------------------------");

        // --- Use Case 5: Expired product ---
        Cart cart5 = new Cart();
        try {
            cart5.addProduct(expiredMilk, 1);
        } catch (IllegalArgumentException e) {
            System.out.println("Cart Addition Error: " + e.getMessage());
        }
        system.checkout(alice, cart5);
        System.out.println("\n------------------------------------------------");

        // --- Use Case 6: Product added with more than available quantity (caught during addProduct) ---
        Cart cart6 = new Cart();
        try {
            cart6.addProduct(biscuits, 20); // Only 15 available - 1 already shipped to alice
        } catch (IllegalArgumentException e) {
            System.out.println("Cart Addition Error: " + e.getMessage());
        }
        system.checkout(alice, cart6); // Will show cart empty if previous add failed
        System.out.println("\n------------------------------------------------");

        // --- Use Case 7: Checkout with only non-shippable items ---
        Cart cart7 = new Cart();
        try {
            cart7.addProduct(scratchCard, 2);
        } catch (IllegalArgumentException e) {
            System.out.println("Cart Addition Error: " + e.getMessage());
        }
        system.checkout(bob, cart7);
        System.out.println("\n------------------------------------------------");
    }
}
