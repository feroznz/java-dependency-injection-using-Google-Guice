package com.company.ordersystem.model;

/***
 * Base entity for all types of beverages.
 */
public class BaseBeverage {

    private String name;
    private Size size;

    public BaseBeverage(String name, Size size)
    {
        this.name = name;
        this.size = size;
    }

    public String getName()
    {
        return this.name;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

}
