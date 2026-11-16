package builder;

import models.*;

import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {
    private String name, date;
    private List<Meal> meals = new ArrayList<>();
    
    public void setName(String name) {this.name = name;}
    public void setDate(String date) {this.date = date;}
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
