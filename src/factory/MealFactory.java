package factory;

import models.Meal;

/** Creator in the Factory Method pattern. */
public abstract class MealFactory {
    public abstract Meal createMeal();
}
