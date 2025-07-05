package com.ecommerce.model.product.behavior;

import java.time.LocalDate;

public interface Expirable{
    LocalDate getExpirationDate();
    boolean isExpired();
}
