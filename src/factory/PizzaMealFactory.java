package factory;

import models.*;

public class PizzaMealFactory implements MealFactory {
	@Override
	public Meal createMeal() {return new Pizza();}
}
