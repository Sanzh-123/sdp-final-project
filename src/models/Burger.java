package models;

/** A basic burger menu item. */
public class Burger implements Meal {
    @Override
    public String getName() {
        return "Burger";
    }

    @Override
    public double getPrice() {
        return 1800.00;
    }
}
