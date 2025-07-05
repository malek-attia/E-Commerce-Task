package com.ecommerce.system;

import com.ecommerce.model.Cart;
import com.ecommerce.model.Customer;
import com.ecommerce.model.product.Product;
import com.ecommerce.model.product.behavior.Shippable;
import com.ecommerce.service.shipping.ShippingService;

import java.util.HashMap;
import java.util.Map;

public class ECommerceSystem {

    private final ShippingService shippingService;

    public ECommerceSystem(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    public void checkout(Customer customer, Cart cart) {
        System.out.println("\n--- Starting Checkout for " + customer.getName() + " ---");

        // 1. Error if Cart is empty
        if (cart.isEmpty()) {
            System.out.println("Error: Cart is empty. Cannot proceed with checkout.");
            return;
        }

        double subtotal = 0;
        double totalWeight = 0;
        Map<Shippable, Integer> shippableItemsForService = new HashMap<>();

        // Validate products in cart and calculate subtotal/weight
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product product = entry.getKey();
            int requestedQuantity = entry.getValue();

            // Error if one product is out of stock
            if (product.getQuantity() < requestedQuantity) {
                System.out.println("Error: Product '" + product.getName() + "' is out of stock. Available: " + product.getQuantity() + ", Requested: " + requestedQuantity);
                return;
            }

            // Error if one product is expired
            if (product.isExpired()) {
                System.out.println("Error: Product '" + product.getName() + "' is expired. Expiration Date: " + product.getExpirationDate());
                return;
            }

            subtotal += product.getPrice() * requestedQuantity;

            // Collect shippable items
            if (product.isShippable()) {
                // For checkout display, we'll iterate through products.
                // For ShippingService, we'll create a list of Shippable objects.
                // Note: The ShippingService interface expects a list of Shippable,
                // so we need to pass the shippable component of the product.

                shippableItemsForService.put(product.getShippable(), requestedQuantity);
                totalWeight += product.getWeight() * requestedQuantity; // Sum total weight for fee calculation

            }
        }

        double shippingFees = shippingService.calculateShippingFee(totalWeight);
        double paidAmount = subtotal + shippingFees;

        // Error if customer's balance is insufficient
        if (customer.getBalance() < paidAmount) {
            System.out.println("Error: Insufficient balance. Customer balance: " + customer.getBalance() + ", Amount due: " + paidAmount);
            return;
        }

        // --- All validations passed, proceed with payment and stock update ---

        // Deduct from customer balance
        customer.setBalance(customer.getBalance() - paidAmount);

        // Update product quantities
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product product = entry.getKey();
            int purchasedQuantity = entry.getValue();
            product.setQuantity(product.getQuantity() - purchasedQuantity);
        }

        // Send shippable items to ShippingService (if any)
        if (!shippableItemsForService.isEmpty()) {
            shippingService.shipItems(shippableItemsForService);
        } else {
            System.out.println("No items require shipping.");
        }


        // Print checkout details
        System.out.println("\n** Checkout receipt **");
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product product = entry.getKey();
            int purchasedQuantity = entry.getValue();
            System.out.println(String.format("%dx %s %.0f", purchasedQuantity, product.getName(), product.getPrice() * purchasedQuantity));
        }
        System.out.println("----------------------");
        System.out.println(String.format("Subtotal %.0f", subtotal));
        System.out.println(String.format("Shipping %.0f", shippingFees));
        System.out.println(String.format("Amount %.0f", paidAmount));
        System.out.println(String.format("Customer current balance after payment %.0f", customer.getBalance()));

        // Clear the cart after successful checkout
        cart.clearCart();
        System.out.println("--- Checkout successful! ---");
    }

}