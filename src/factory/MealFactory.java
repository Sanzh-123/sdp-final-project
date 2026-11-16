package factory;

import models.Meal;

/** Factory Method creator: concrete factories decide which Meal to create. */
public interface MealFactory {
    Meal createMeal();
}
