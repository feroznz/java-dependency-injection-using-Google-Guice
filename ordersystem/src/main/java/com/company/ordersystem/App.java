package com.company.ordersystem;

import com.company.ordersystem.di.OrderModule;
import com.company.ordersystem.model.CoffeType;
import com.company.ordersystem.model.Coffee;
import com.company.ordersystem.model.Ingredient;
import com.company.ordersystem.model.Size;
import com.company.ordersystem.service.OrderService;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class App {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new OrderModule());

        /*    Build object using injector*/
        OrderService orderService = injector.getInstance(OrderService.class);

        Coffee espresso = new Coffee(CoffeType.ESPRESSO, Ingredient.NONE, Size.REGULAR);
        Coffee largeCappucino = new Coffee(CoffeType.CAPPUCCINO, Ingredient.NONE, Size.LARGE);
        Coffee soyFlatWhite = new Coffee(CoffeType.FLAT_WHITE, Ingredient.SOY_MILK, Size.REGULAR);
        Coffee cappuccino = new Coffee(CoffeType.CAPPUCCINO, Ingredient.NONE, Size.REGULAR);

        orderService.addToOrder(espresso);
        orderService.addToOrder(largeCappucino);
        orderService.addToOrder(soyFlatWhite);
        orderService.addToOrder(cappuccino);

        System.out.println("Total for order: " + orderService.orderTotal());
        System.out.println("Total GST: " + orderService.calculateGST());
    }
}
