package com.ecommerce.service.shipping;

import com.ecommerce.model.product.behavior.Shippable;

import java.util.Map;

public class StandardShippingService implements ShippingService {

    private static final double RATE_PER_KG = 10.0; // Example rate

    @Override
    public void shipItems(Map<Shippable, Integer> shippableItems) {
        if (shippableItems.isEmpty()) {
            System.out.println("No items to ship.");
            return;
        }

        System.out.println("\n** Shipment notice **");
        double totalWeight = 0;
        for (Map.Entry<Shippable, Integer> entry : shippableItems.entrySet()) {
            Shippable item = entry.getKey();
            System.out.println(String.format("%dx %s %.1fg", entry.getValue(), item.getName(), item.getWeight() * 1000));
            totalWeight += item.getWeight() *  entry.getValue();
        }
        System.out.println(String.format("Total package weight %.1fkg", totalWeight));
    }

    @Override
    public double calculateShippingFee(double totalWeight) {
        // Simple linear shipping fee calculation
        return totalWeight * RATE_PER_KG;
    }
}