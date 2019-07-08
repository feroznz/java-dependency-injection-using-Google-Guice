package com.company.ordersystem.model;

import java.math.BigDecimal;

public enum Ingredient {
    SOY_MILK(new BigDecimal(.50)),
    NONE(new BigDecimal(0));

    private final BigDecimal price;

    Ingredient(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}

