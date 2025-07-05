package com.ecommerce.model;

import com.ecommerce.model.product.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class Cart {
    private Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough '" + product.getName() + "' in stock. Available: " + product.getQuantity());
        }
        if (product.isExpired()) {
            throw new IllegalArgumentException("Product '" + product.getName() + "' is expired.");
        }

        items.merge(product, quantity, Integer::sum);
        System.out.println("Added " + quantity + "x " + product.getName() + " to cart.");
    }

    public void remove(Product product) {
        items.remove(product);
    }

    public void clearCart() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

}