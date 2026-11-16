package decorator;

import models.Meal;

/** Base decorator. Wraps a Meal and adds one extra. */
public abstract class MealDecorator implements Meal {
    protected final Meal decoratedMeal;

    protected MealDecorator(Meal decoratedMeal) {
        if (decoratedMeal == null) {
            throw new IllegalArgumentException("Meal to decorate cannot be null.");
        }
        this.decoratedMeal = decoratedMeal;
    }

    protected abstract String getExtraName();
    protected abstract double getExtraPrice();

    @Override
    public String getName() {
        return decoratedMeal.getName() + " + " + getExtraName();
    }

    @Override
    public double getPrice() {
        return decoratedMeal.getPrice() + getExtraPrice();
    }
}
