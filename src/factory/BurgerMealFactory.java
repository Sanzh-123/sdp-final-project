package factory;

import models.*;

public class BurgerMealFactory implements MealFactory {
    @Override
    public Meal createMeal() {return new Burger();}
}
