package decorator;

import models.Meal;

/** Adds extra sauce to any Meal. */
public class SauceDecorator extends MealDecorator {
    public SauceDecorator(Meal meal) {
        super(meal);
    }

    @Override
    protected String getExtraName() {
        return "Extra Sauce";
    }

    @Override
    protected double getExtraPrice() {
        return 150.00;
    }
}
