package models;

import java.util.ArrayList;
import java.util.List;

/** Stores the meals selected by one customer. */
public class Order {
    static int idCounter;
    private int id;
    private String name, date;
    private List<Meal> meals = new ArrayList<>();
    
    public Order(String name, String date, List<Meal> meals) {
        this.id = ++idCounter;
        this.name = name;
        this.date = date;
        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public double getSubtotal() {
        double subtotal = 0.0;
        for (Meal meal : meals) {
            subtotal += meal.getPrice();
        }
        return subtotal;
    }
}
