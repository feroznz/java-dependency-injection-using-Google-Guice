package com.company.ordersystem.di;

import com.company.ordersystem.service.Order;
import com.company.ordersystem.service.impl.OrderImpl;
import com.google.inject.AbstractModule;

/***
 * Dependency Injection - define binding
 */
public class OrderModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Order.class).to(OrderImpl.class);
    }
}
