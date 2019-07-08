package com.company.ordersystem.model;

public class Coffee extends BaseBeverage {

    private Ingredient ingredient;
    private CoffeType coffeeType;

    public Coffee(CoffeType coffeeType, Ingredient ingredient, Size size) {
        super(coffeeType.name(), size);
        this.ingredient = ingredient;
        this.coffeeType = coffeeType;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }
    public CoffeType getCoffeeType() {
        return coffeeType;
    }
}