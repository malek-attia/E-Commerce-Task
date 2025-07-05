package com.ecommerce.service.shipping;

import com.ecommerce.model.product.behavior.Shippable;

import java.util.Map;

public interface ShippingService {
    void shipItems(Map<Shippable, Integer> shippableItems);
    double calculateShippingFee(double totalWeight); // Assume a simple fee calculation
}