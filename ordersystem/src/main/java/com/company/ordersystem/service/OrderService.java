package com.company.ordersystem.service;

import com.company.ordersystem.model.Coffee;
import com.google.inject.Inject;

import java.math.BigDecimal;

public class OrderService {
    private final Order order;

    @Inject
    public OrderService(Order order) {
        this.order = order;
    }

    public void addToOrder(Coffee coffee) {
        order.addToOrder(coffee);
    }

    public BigDecimal orderTotal() {
        return order.orderTotal();
    }

    public BigDecimal calculateGST() {
        return order.calculateGST();
    }

    public void applyCoupon() {
        order.applyCoupon();
    }

    public void applyTwoForOneDiscount() {
        order.applyTwoForOneDiscount();
    }
}
