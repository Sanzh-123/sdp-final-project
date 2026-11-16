package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Stores the meals selected for one order. */
public class Order {
    private static int idCounter = 0;

    private final int id;
    private final List<Meal> meals;

    /**
     * Creates an order from the supplied meals.
     * A defensive copy prevents later builder changes from modifying this order.
     */
    public Order(List<Meal> meals) {
        if (meals == null) {
            throw new IllegalArgumentException("Meals cannot be null.");
        }
        this.id = ++idCounter;
        this.meals = new ArrayList<>(meals);
    }

    public int getId() {
        return id;
    }

    public List<Meal> getMeals() {
        return Collections.unmodifiableList(meals);
    }

    public double getSubtotal() {
        double subtotal = 0.0;
        for (Meal meal : meals) {
            subtotal += meal.getPrice();
        }
        return subtotal;
    }
}
