package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Stores the meals selected by one customer. */
public class Order {
    private final List<Meal> meals = new ArrayList<>();

    public void addMeal(Meal meal) {
        if (meal == null) {
            throw new IllegalArgumentException("Meal cannot be null.");
        }
        meals.add(meal);
    }

    public List<Meal> getMeals() {
        return Collections.unmodifiableList(meals);
    }

    public boolean isEmpty() {
        return meals.isEmpty();
    }

    public double getSubtotal() {
        double subtotal = 0.0;
        for (Meal meal : meals) {
            subtotal += meal.getPrice();
        }
        return subtotal;
    }
}
