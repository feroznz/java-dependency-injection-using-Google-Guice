package com.company.ordersystem.service;


import com.company.ordersystem.model.Coffee;

import java.math.BigDecimal;

public interface Order {
    void addToOrder(Coffee coffee);
    BigDecimal orderTotal();
    BigDecimal calculateGST();
    void applyCoupon();
    void applyTwoForOneDiscount();
}
