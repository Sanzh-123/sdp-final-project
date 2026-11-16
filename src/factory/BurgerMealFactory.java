package factory;

import models.Burger;
import models.Meal;

/** Concrete factory for Burger. */
public class BurgerMealFactory implements MealFactory {
    @Override
    public Meal createMeal() {
        return new Burger();
    }
}
