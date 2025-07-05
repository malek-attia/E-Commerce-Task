package com.ecommerce.model.product;

import com.ecommerce.model.product.behavior.Expirable;
import com.ecommerce.model.product.behavior.Shippable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private String name;
    private double price;
    private int quantity;

    // Optional behavior interfaces
    private Expirable expirable;
    private Shippable shippable;

    public boolean isExpired() {
        return expirable != null && expirable.isExpired();
    }

    public boolean isShippable() {
        return shippable != null;
    }

    public double getWeight() {
        if (shippable == null) {
            throw new IllegalStateException("Product is not shippable and does not have a weight.");
        }
        return shippable.getWeight();
    }

    public LocalDate getExpirationDate() {
        if (expirable == null) {
            throw new IllegalStateException("Product is not expirable and does not have an expiration date.");
        }
        return expirable.getExpirationDate();
    }
}