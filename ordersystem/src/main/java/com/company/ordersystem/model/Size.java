package com.company.ordersystem.model;

import java.math.BigDecimal;

public enum Size {
    // This hardcoded amounts should come from a setting file.
    REGULAR(new BigDecimal(0)),
    LARGE(new BigDecimal(.50));

    private BigDecimal price;

    Size(BigDecimal price) {
        this.price = price;
    }
    public BigDecimal getPrice() {
        return price;
    }
}