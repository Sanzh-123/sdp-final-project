package models;

/** A basic pizza menu item. */
public class Pizza implements Meal {
    @Override
    public String getName() {
        return "Pizza";
    }

    @Override
    public double getPrice() {
        return 2500.00;
    }
}
