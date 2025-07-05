package com.ecommerce.model.product.behavior.Impl;

import com.ecommerce.model.product.behavior.Shippable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShippableImpl implements Shippable {
    private double weight;
    private String productName;

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public String getName() {
        return productName;
    }
}