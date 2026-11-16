package facade;

import builder.OrderBuilder;
import decorator.BaconDecorator;
import decorator.CheeseDecorator;
import decorator.SauceDecorator;
import factory.BurgerMealFactory;
import factory.MealFactory;
import factory.PizzaMealFactory;
import models.Meal;
import models.Order;
import observer.CustomerObserver;
import observer.KitchenObserver;
import observer.OrderSubject;
import observer.Observer;
import strategy.Context;
import strategy.NoDiscount;
import strategy.StaffDiscount;
import strategy.StudentDiscount;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/** Facade that coordinates ordering, customization, discounts and notifications. */
public class RestaurantFacade {
    private final OrderSubject subject;
    private final OrderBuilder builder;
    private final Context discount;
    private final Scanner scanner;

    public RestaurantFacade() {
        subject = new OrderSubject();
        subject.attach(new KitchenObserver());
        subject.attach(new CustomerObserver("Ali"));

        builder = new OrderBuilder();
        discount = new Context();
        scanner = new Scanner(System.in);
    }

    public void printMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add Pizza  - 2,500 KZT");
        System.out.println("2. Add Burger - 1,800 KZT");
        System.out.println("3. View order");
        System.out.println("4. Select discount");
        System.out.println("5. Checkout");
        System.out.println("0. Exit without checkout");
    }

    private void printToppingsMenu() {
        System.out.println("1. Cheese      + 300 KZT");
        System.out.println("2. Bacon       + 500 KZT");
        System.out.println("3. Extra sauce + 150 KZT");
        System.out.println("0. Finish customizing");
    }

    private void printDiscountMenu() {
        System.out.println("1. Student - 30% (max 2000 KZT)");
        System.out.println("2. Staff   - 5000 KZT");
        System.out.println("0. No discount");
    }

    private static String formatPrice(double price) {
        return String.format(Locale.US, "%,.0f", price);
    }

    public int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private Meal createBaseMeal(String name) {
        MealFactory factory;
        switch (name.toLowerCase(Locale.ROOT)) {
            case "pizza":
                factory = new PizzaMealFactory();
                break;
            case "burger":
                factory = new BurgerMealFactory();
                break;
            default:
                throw new IllegalArgumentException("Invalid meal name: " + name);
        }
        return factory.createMeal();
    }

    /** Adds a meal with the selected toppings; intended for graphical interfaces. */
    public Meal addCustomizedMeal(String name, boolean cheese, boolean bacon, boolean sauce) {
        Meal meal = createBaseMeal(name);
        if (cheese) {
            meal = new CheeseDecorator(meal);
        }
        if (bacon) {
            meal = new BaconDecorator(meal);
        }
        if (sauce) {
            meal = new SauceDecorator(meal);
        }
        builder.addMeal(meal);
        return meal;
    }

    /** Read-only view of the current cart. */
    public List<Meal> getCurrentMeals() {
        return builder.getMeals();
    }

    public boolean removeMeal(int index) {
        return builder.removeMeal(index);
    }

    public double getSubtotal() {
        return builder.getSubtotal();
    }

    public double getFinalTotal() {
        return discount.process(getSubtotal());
    }

    public double getDiscountAmount() {
        return getSubtotal() - getFinalTotal();
    }

    public void setNoDiscount() {
        discount.setDiscount(new NoDiscount());
    }

    public void setStudentDiscount() {
        discount.setDiscount(new StudentDiscount(0.30, 2000.0));
    }

    public void setStaffDiscount() {
        discount.setDiscount(new StaffDiscount(5000.0));
    }

    public void addObserver(Observer observer) {
        subject.attach(observer);
    }

    public void addMeal(String name) {
        Meal meal = createBaseMeal(name);
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

        builder.addMeal(meal);
        System.out.println("Added to your order: " + meal.getName() + " - "
                + formatPrice(meal.getPrice()) + " KZT\n");
    }

    public void printOrder() {
        if (builder.getMeals().isEmpty()) {
            System.out.println("Your order is empty.\n");
            return;
        }

        int itemNumber = 1;
        for (Meal meal : builder.getMeals()) {
            System.out.printf(Locale.US, "%d. %s - %,.0f KZT%n",
                    itemNumber, meal.getName(), meal.getPrice());
            itemNumber++;
        }
        System.out.printf(Locale.US, "Subtotal: %,.0f KZT%n%n", builder.getSubtotal());
    }

    public void selectDiscount() {
        boolean choosing = true;
        while (choosing) {
            printDiscountMenu();
            int choice = readInt("Choose a discount: ");

            switch (choice) {
                case 1:
                    discount.setDiscount(new StudentDiscount(0.30, 2000.0));
                    choosing = false;
                    System.out.println("Student discount selected.\n");
                    break;
                case 2:
                    discount.setDiscount(new StaffDiscount(5000.0));
                    choosing = false;
                    System.out.println("Staff discount selected.\n");
                    break;
                case 0:
                    discount.setDiscount(new NoDiscount());
                    choosing = false;
                    System.out.println("No discount selected.\n");
                    break;
                default:
                    System.out.println("Please choose a listed option.\n");
            }
        }
    }

    /** Completes the current order, notifies observers and empties the cart. */
    public Order completeOrder() {
        if (builder.getMeals().isEmpty()) {
            throw new IllegalStateException("There are no items to check out.");
        }

        Order order = builder.build();
        subject.setStatus("Order #" + order.getId() + " has been received and is being prepared.");
        builder.clear();
        return order;
    }

    /**
     * Console checkout retained for the optional command-line version.
     * @return true when checkout succeeds; false when the cart is empty.
     */
    public boolean checkout() {
        System.out.println("\n--- Checkout ---");
        if (builder.getMeals().isEmpty()) {
            System.out.println("There are no items to check out.\n");
            return false;
        }

        double subtotal = getSubtotal();
        double total = discount.process(subtotal);
        double saved = subtotal - total;
        Order order = completeOrder();

        printOrder(order);
        System.out.printf(Locale.US, "Subtotal: %,.0f KZT%n", subtotal);
        System.out.printf(Locale.US, "Discount: %,.0f KZT%n", saved);
        System.out.printf(Locale.US, "Final total: %,.0f KZT%n", total);
        System.out.println("Thank you for your order!");
        return true;
    }

    private void printOrder(Order order) {
        System.out.println("--- Your Order ---");
        int itemNumber = 1;
        for (Meal meal : order.getMeals()) {
            System.out.printf(Locale.US, "%d. %s - %,.0f KZT%n",
                    itemNumber++, meal.getName(), meal.getPrice());
        }
        System.out.printf(Locale.US, "Subtotal: %,.0f KZT%n", order.getSubtotal());
    }
}
