# Design Notes

## Architecture

The application uses a compact package structure under `src/`. `Main.java` starts the Swing window by default and retains the console application behind the `--console` argument.

## Design pattern responsibilities

- **Factory Method:** the factory implementations create the Pizza and Burger products through the MealFactory interface.
- **Decorator:** topping decorators wrap a Meal and add their names and prices at runtime.
- **Observer:** the order subject notifies attached observers when checkout changes the order status.
- **Strategy:** the discount context delegates final-price calculation to the selected discount implementation.
- **Builder:** OrderBuilder collects meals and creates an Order snapshot.
- **Facade:** RestaurantFacade coordinates factories, decorators, builder, discount strategy and order notifications for the UI.

## Swing and image handling

`RestaurantWindow` is the single customer-facing Swing window. `ImageLoader` optionally loads `assets/pizza.png` and `assets/burger.png`, scales the photos to menu-card dimensions, and displays a placeholder when an image is not present. Images are presentation-only and do not change the business logic or design-pattern implementation.

## Limitations

Orders are stored in memory and are cleared when the application closes. There is no database, network service, or real payment processing. The application is intended as a local design-pattern demonstration.
