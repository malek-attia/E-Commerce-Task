package com.ecommerce.model.product.behavior.Impl;

import com.ecommerce.model.product.behavior.Expirable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ExpirableImpl implements Expirable {
    private LocalDate expirationDate;

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }
}