package decorator;

import models.Meal;

/** Adds bacon to any Meal. */
public class BaconDecorator extends MealDecorator {
    public BaconDecorator(Meal meal) {
        super(meal);
    }

    @Override
    protected String getExtraName() {
        return "Bacon";
    }

    @Override
    protected double getExtraPrice() {
        return 500.00;
    }
}
