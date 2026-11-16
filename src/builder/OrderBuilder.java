package builder;

import models.Meal;
import models.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Builder that collects meals and creates an Order. */
public class OrderBuilder {
    private final List<Meal> meals = new ArrayList<>();

    public OrderBuilder addMeal(Meal meal) {
        if (meal == null) {
            throw new IllegalArgumentException("Meal cannot be null.");
        }
        meals.add(meal);
        return this;
    }

    public List<Meal> getMeals() {
        return Collections.unmodifiableList(meals);
    }

    public boolean removeMeal(int index) {
        if (index < 0 || index >= meals.size()) {
            return false;
        }
        meals.remove(index);
        return true;
    }

    public void clear() {
        meals.clear();
    }

    public double getSubtotal() {
        double subtotal = 0.0;
        for (Meal meal : meals) {
            subtotal += meal.getPrice();
        }
        return subtotal;
    }

    /**
     * Builds an Order snapshot from the meals currently in this builder.
     * The Order constructor copies the list, so later builder changes do not
     * modify an Order that has already been built.
     */
    public Order build() {
        if (meals.isEmpty()) {
            throw new IllegalStateException("Cannot build an order without meals.");
        }
        return new Order(meals);
    }
}
