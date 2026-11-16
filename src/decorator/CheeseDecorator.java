package decorator;

import models.Meal;

/** Adds cheese to any Meal. */
public class CheeseDecorator extends MealDecorator {
    public CheeseDecorator(Meal meal) {
        super(meal);
    }

    @Override
    protected String getExtraName() {
        return "Cheese";
    }

    @Override
    protected double getExtraPrice() {
        return 300.00;
    }
}
