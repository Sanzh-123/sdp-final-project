package factory;

import models.Meal;
import models.Pizza;

/** Concrete factory for Pizza. */
public class PizzaMealFactory implements MealFactory {
    @Override
    public Meal createMeal() {
        return new Pizza();
    }
}
