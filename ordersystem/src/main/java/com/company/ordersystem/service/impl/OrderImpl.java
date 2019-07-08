package com.company.ordersystem.service.impl;

import com.company.ordersystem.model.CoffeType;
import com.company.ordersystem.model.Coffee;
import com.company.ordersystem.model.Ingredient;
import com.company.ordersystem.model.Size;
import com.company.ordersystem.service.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class OrderImpl implements Order {

    private final BigDecimal REGULAR_PRICE = new BigDecimal(3.5);
    private final BigDecimal REGULAR_ESPRESSO_PRICE = new BigDecimal(3);
    private final BigDecimal COUPON_DISCOUNT_AMOUNT = new BigDecimal(10);

    private final BigDecimal GST_AMOUNT = new BigDecimal(10);
    private final List<Coffee> orders = new ArrayList<>();

    private boolean applyCoupon;
    private boolean isTwoForOneDiscount;

    @Override
    public void addToOrder(Coffee coffee) {
        if (isValidOrder(coffee)) {
            this.orders.add(coffee);
        } else {
            throw new NullPointerException();
        }

    }

    @Override
    public BigDecimal orderTotal() {
        BigDecimal total = BigDecimal.ZERO;
        List<BigDecimal> coffeePrices = new ArrayList<>();

        for (Coffee coffee : orders) {
            BigDecimal currentCoffeeTotal;

            if (coffee.getCoffeeType().equals(CoffeType.ESPRESSO)) {
                currentCoffeeTotal = REGULAR_ESPRESSO_PRICE;
            } else {
                if (coffee.getSize().getPrice().equals(BigDecimal.ZERO) && coffee.getIngredient().equals(Ingredient.NONE))
                    currentCoffeeTotal = REGULAR_PRICE;
                else {
                    currentCoffeeTotal = coffee.getSize().getPrice().add(REGULAR_PRICE).add(coffee.getIngredient().getPrice());
                }
            }
            coffeePrices.add(currentCoffeeTotal);
            total = total.add(currentCoffeeTotal);
        }
        // check if coupon discount need to be applied.
        if(applyCoupon){
            total = total.subtract(calculatePercentage(total,COUPON_DISCOUNT_AMOUNT));
        }
        if(isTwoForOneDiscount){
            // find cheapest and deduct that amount from the total.
            Optional<BigDecimal> lowestAmount = coffeePrices.stream().min(Comparator.naturalOrder());
            if(lowestAmount.isPresent()){
                total = total.subtract(lowestAmount.get());
            }
        }

        return total;    }

    @Override
    public BigDecimal calculateGST() {
        BigDecimal totalGST = calculatePercentage(orderTotal(),GST_AMOUNT);
        return totalGST.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    @Override
    public void applyCoupon() {
        applyCoupon = true;
    }

    @Override
    public void applyTwoForOneDiscount() {
        isTwoForOneDiscount = orders.size() ==2;
    }

    /***
     * An Espresso comes in one size only - regular.
     * @param coffee
     * @return
     */
    private boolean isValidOrder(Coffee coffee) {
        boolean validOrder;
        validOrder = coffee.getCoffeeType() != CoffeType.ESPRESSO || coffee.getSize() != Size.LARGE;
        return validOrder;
    }

    private BigDecimal calculatePercentage(BigDecimal total, BigDecimal percentage){
        BigDecimal totalPercentageAmount = total.multiply(percentage.divide(new BigDecimal(100)));
        return totalPercentageAmount;
    }

}
