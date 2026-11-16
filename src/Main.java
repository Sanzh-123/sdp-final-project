import decorator.BaconDecorator;
import decorator.CheeseDecorator;
import decorator.SauceDecorator;
import factory.MealFactory;
import factory.RestaurantMealFactory;
import models.Meal;
import models.Order;

import java.util.Locale;
import java.util.Scanner;

/** Console entry point for the Restaurant Ordering System. */
public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        Order order = new Order();
        boolean running = true;

        System.out.println("=== Restaurant Ordering System ===");

        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    addCustomizedMeal(order, RestaurantMealFactory.pizzaFactory());
                    break;
                case 2:
                    addCustomizedMeal(order, RestaurantMealFactory.burgerFactory());
                    break;
                case 3:
                    printOrder(order);
                    break;
                case 4:
                    checkout(order);
                    running = false;
                    break;
                case 0:
                    System.out.println("Order cancelled. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Please choose a listed option.\n");
            }
        }

        SCANNER.close();
    }

    /** Creates the base meal through Factory Method, then decorates it. */
    private static void addCustomizedMeal(Order order, MealFactory factory) {
        Meal meal = factory.createMeal();

        System.out.println("\nCustomize your " + meal.getName() + ":");
        boolean customizing = true;

        while (customizing) {
            printToppingsMenu();
            int choice = readInt("Choose an extra: ");

            switch (choice) {
                case 1:
                    meal = new CheeseDecorator(meal);
                    System.out.println("Cheese added. Current price: "
                            + formatPrice(meal.getPrice()) + " KZT\n");
                    break;
                case 2:
                    meal = new BaconDecorator(meal);
                    System.out.println("Bacon added. Current price: "
                            + formatPrice(meal.getPrice()) + " KZT\n");
                    break;
                case 3:
                    meal = new SauceDecorator(meal);
                    System.out.println("Extra sauce added. Current price: "
                            + formatPrice(meal.getPrice()) + " KZT\n");
                    break;
                case 0:
                    customizing = false;
                    break;
                default:
                    System.out.println("Please choose a listed option.\n");
            }
        }

        order.addMeal(meal);
        System.out.println("Added to your order: " + meal.getName()
                + " - " + formatPrice(meal.getPrice()) + " KZT\n");
    }

    private static void printMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add Pizza  - 2,500 KZT");
        System.out.println("2. Add Burger - 1,800 KZT");
        System.out.println("3. View order");
        System.out.println("4. Checkout");
        System.out.println("0. Exit without checkout");
    }

    private static void printToppingsMenu() {
        System.out.println("1. Cheese      + 300 KZT");
        System.out.println("2. Bacon       + 500 KZT");
        System.out.println("3. Extra sauce + 150 KZT");
        System.out.println("0. Finish customizing");
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private static void printOrder(Order order) {
        System.out.println("\n--- Your Order ---");
        if (order.isEmpty()) {
            System.out.println("Your order is empty.\n");
            return;
        }

        int itemNumber = 1;
        for (Meal meal : order.getMeals()) {
            System.out.printf(Locale.US, "%d. %s - %,.0f KZT%n",
                    itemNumber, meal.getName(), meal.getPrice());
            itemNumber++;
        }
        System.out.printf(Locale.US, "Subtotal: %,.0f KZT%n%n", order.getSubtotal());
    }

    private static void checkout(Order order) {
        System.out.println("\n--- Checkout ---");
        if (order.isEmpty()) {
            System.out.println("There are no items to check out.");
            return;
        }

        printOrder(order);
        System.out.printf(Locale.US, "Thank you! Your total is %,.0f KZT.%n",
                order.getSubtotal());
    }

    private static String formatPrice(double price) {
        return String.format(Locale.US, "%,.0f", price);
    }
}
