package com.company.ordersystem.service;


import com.company.ordersystem.di.OrderModule;
import com.company.ordersystem.model.CoffeType;
import com.company.ordersystem.model.Coffee;
import com.company.ordersystem.model.Ingredient;
import com.company.ordersystem.model.Size;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.math.BigDecimal;

@Guice(modules = {OrderModule.class})
public class OrderServiceTest {
    @Inject
    private OrderService orderService;

    @BeforeMethod(alwaysRun = true)
    @Inject
    public void setUp() {
        Injector injector = com.google.inject.Guice.createInjector(new OrderModule());
        orderService = injector.getInstance(OrderService.class);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        this.orderService = null;
    }

    @Test
    public void testAddToOrder() {
        Coffee espresso = new Coffee(CoffeType.ESPRESSO, Ingredient.NONE, Size.REGULAR);
        orderService.addToOrder(espresso);

        String coffeeName = espresso.getName();
        Assert.assertEquals(coffeeName, "ESPRESSO");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testAddToOrder_Should_Fail() throws NullPointerException {
        Coffee espresso = new Coffee(CoffeType.ESPRESSO, Ingredient.NONE, Size.LARGE);
        orderService.addToOrder(espresso);
    }

    @Test
    public void testOrderTotal() {
        Coffee espresso = new Coffee(CoffeType.ESPRESSO, Ingredient.NONE, Size.REGULAR);
        Coffee largeCappucino = new Coffee(CoffeType.CAPPUCCINO, Ingredient.NONE, Size.LARGE);
        Coffee soyFlatWhite = new Coffee(CoffeType.FLAT_WHITE, Ingredient.SOY_MILK, Size.REGULAR);
        Coffee cappuccino = new Coffee(CoffeType.CAPPUCCINO, Ingredient.NONE, Size.REGULAR);

        orderService.addToOrder(espresso);
        orderService.addToOrder(largeCappucino);
        orderService.addToOrder(soyFlatWhite);
        orderService.addToOrder(cappuccino);

        BigDecimal orderTotal = orderService.orderTotal();
        Assert.assertEquals(orderTotal, new BigDecimal(14.50));
    }

    @Test
    public void testOrderTotal_Should_Return_ZERO() {
        BigDecimal orderTotal = orderService.orderTotal();
        Assert.assertEquals(orderTotal, new BigDecimal(0));
    }

    @Test
    public void testCalculateGST() {
        Coffee espresso = new Coffee(CoffeType.ESPRESSO, Ingredient.NONE, Size.REGULAR);
        Coffee largeCappucino = new Coffee(CoffeType.CAPPUCCINO, Ingredient.NONE, Size.LARGE);
        Coffee soyFlatWhite = new Coffee(CoffeType.FLAT_WHITE, Ingredient.SOY_MILK, Size.REGULAR);
        Coffee cappuccino = new Coffee(CoffeType.CAPPUCCINO, Ingredient.NONE, Size.REGULAR);

        orderService.addToOrder(espresso);
        orderService.addToOrder(largeCappucino);
        orderService.addToOrder(soyFlatWhite);
        orderService.addToOrder(cappuccino);

        BigDecimal orderTotal = orderService.calculateGST();
        BigDecimal expectedTotal = new BigDecimal("1.45");

        Assert.assertEquals(orderTotal, expectedTotal);
    }

    @Test
    public void testApplyCoupon() {
        Coffee largeCappuccino = new Coffee(CoffeType.CAPPUCCINO, Ingredient.NONE, Size.LARGE);
        Coffee soyFlatWhite = new Coffee(CoffeType.FLAT_WHITE, Ingredient.SOY_MILK, Size.REGULAR);

        orderService.addToOrder(largeCappuccino);
        orderService.addToOrder(soyFlatWhite);
        orderService.applyCoupon();

        BigDecimal totalAmount = orderService.orderTotal();
        BigDecimal expectedTotalAmount = new BigDecimal("7.20");

        BigDecimal totalGST = orderService.calculateGST();
        BigDecimal expectedGST = new BigDecimal("0.72");
        Assert.assertEquals(totalAmount, expectedTotalAmount);
        Assert.assertEquals(totalGST, expectedGST);
    }

    @Test
    public void testApplyCoupon_Should_ZERO() {
        orderService.applyCoupon();

        BigDecimal totalAmount = orderService.orderTotal();
        BigDecimal expectedTotalAmount = new BigDecimal("0.0");

        BigDecimal totalGST = orderService.calculateGST();
        BigDecimal expectedGST = new BigDecimal("0.00");
        Assert.assertEquals(totalAmount, expectedTotalAmount);
        Assert.assertEquals(totalGST, expectedGST);
    }

    @Test
    public void testGSTWhenApplyCoupon() {
        Coffee largeCappuccino = new Coffee(CoffeType.CAPPUCCINO, Ingredient.NONE, Size.LARGE);
        Coffee soyFlatWhite = new Coffee(CoffeType.FLAT_WHITE, Ingredient.SOY_MILK, Size.REGULAR);

        orderService.addToOrder(largeCappuccino);
        orderService.addToOrder(soyFlatWhite);
        orderService.applyCoupon();

        BigDecimal totalGST = orderService.calculateGST();
        BigDecimal expectedGST = new BigDecimal("0.72");
        Assert.assertEquals(totalGST, expectedGST);
    }

    @Test
    public void testTwoForOneDiscount() {
        Coffee largeCappuccino = new Coffee(CoffeType.CAPPUCCINO, Ingredient.NONE, Size.LARGE);
        Coffee flatWhite = new Coffee(CoffeType.FLAT_WHITE, Ingredient.NONE, Size.REGULAR);

        orderService.addToOrder(largeCappuccino);
        orderService.addToOrder(flatWhite);
        orderService.applyTwoForOneDiscount();

        BigDecimal totalAmount = orderService.orderTotal();
        BigDecimal expectedTotalAmount = new BigDecimal("4.0");

        BigDecimal totalGST = orderService.calculateGST();
        BigDecimal expectedGST = new BigDecimal("0.40");

        Assert.assertEquals(totalGST, expectedGST);
        Assert.assertEquals(totalAmount, expectedTotalAmount);
    }

    @Test
    public void testTwoForOneDiscount_Should_Fail() {
        Coffee largeCappuccino = new Coffee(CoffeType.CAPPUCCINO, Ingredient.NONE, Size.LARGE);
        Coffee flatWhite = new Coffee(CoffeType.FLAT_WHITE, Ingredient.NONE, Size.REGULAR);
        Coffee espresso = new Coffee(CoffeType.ESPRESSO, Ingredient.NONE, Size.REGULAR);
        Coffee espresso2 = new Coffee(CoffeType.ESPRESSO, Ingredient.NONE, Size.REGULAR);

        orderService.addToOrder(largeCappuccino);
        orderService.addToOrder(flatWhite);
        orderService.addToOrder(espresso);
        orderService.addToOrder(espresso2);
        orderService.applyTwoForOneDiscount();

        BigDecimal totalAmount = orderService.orderTotal();
        BigDecimal expectedTotalAmount = new BigDecimal("13.5");

        Assert.assertEquals(totalAmount, expectedTotalAmount);
    }
}