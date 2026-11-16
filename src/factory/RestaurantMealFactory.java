package factory;

import models.Burger;
import models.Meal;
import models.Pizza;

/**
 * Provides the restaurant's concrete meal creators.
 * Each nested creator overrides createMeal(), preserving the Factory Method pattern
 * while keeping the requested folder/file structure compact.
 */
public final class RestaurantMealFactory {
    private RestaurantMealFactory() {
        // Utility class; use the static factory methods below.
    }

    public static MealFactory pizzaFactory() {
        return new PizzaMealFactory();
    }

    public static MealFactory burgerFactory() {
        return new BurgerMealFactory();
    }

    private static class PizzaMealFactory extends MealFactory {
        @Override
        public Meal createMeal() {
            return new Pizza();
        }
    }

    private static class BurgerMealFactory extends MealFactory {
        @Override
        public Meal createMeal() {
            return new Burger();
        }
    }
}
