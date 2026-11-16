package builder;

import models.*;

import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {
    private List<Meal> meals = new ArrayList<>();
    
    public void addMeal(Meal meal) {this.meals.add(meal);}
    public List<Meal> getMeals() {return meals;}
    
    public double getSubtotal() {
        double subtotal = 0.0;
        for (Meal meal : meals) {
            subtotal += meal.getPrice();
        }
        return subtotal;
    }
    
    public Order build() {return new Order(name, date, meals);}
}
