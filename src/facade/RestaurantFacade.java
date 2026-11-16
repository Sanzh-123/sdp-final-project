package facade;

import observer.*;
import builder.*;
import factory.*;
import context.*;
import models.*;
import decorator.*;
import strategy.*;

import java.util.Scanner;
import java.util.Locale;

public class RestaurantFacade {
    private OrderSubject subject;
    private OrderBuilder builder;
    private MealFactory factory;
    private Context discount;
    private Scanner scanner = new Scanner(System.in);
    
    public RestaurantFacade() {
        subject = new OrderSubject();
        subject.attach(new KitchenObserver());
        subject.attach(new CustomerObserver("Ali"));
        
        builder = new OrderBuilder();
        discount = new Context();
    }
    
    public void orderMeal(String meal) { // Obsolete
        subject.setStatus("Order received: " + meal);
        subject.setStatus("Preparing " + meal);
        subject.setStatus(meal + " is ready!");
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
    
    public void addMeal(String name) {
        switch (name) {
            case "pizza":
                factory = RestaurantMealFactory.pizzaFactory();
                break;
            case "burger":
                factory = RestaurantMealFactory.burgerFactory();
                break;
            default:
                throw new IllegalArgumentException("Invalid meal name: " + name);
        }
        
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

        builder.addMeal(meal);
        System.out.println("Added to your order: " + meal.getName()
                + " - " + formatPrice(meal.getPrice()) + " KZT\n");
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

            choosing = false;
            switch (choice) {
                case 1:
                    discount.setDiscount(new StudentDiscount(0.3, 2000.0));
                    break;
                case 2:
                    discount.setDiscount(new StaffDiscount(5000.0));
                    break;
                case 0:
                    discount.setDiscount(new NoDiscount());
                    break;
                default:
                    System.out.println("Please choose a listed option.\n");
                    choosing = true;
            }
        }
    }
    
    public void checkout() {
        System.out.println("\n--- Checkout ---");
        if (builder.getMeals().isEmpty()) {
            System.out.println("There are no items to check out.");
            return;
        }
        
        printOrder();
        subject.setStatus("Preparing");
        
        Order order = builder.build();
        System.out.printf(Locale.US, "Thank you! Your total is %,.0f KZT.%n", discount.process(order.getSubtotal()));
    }
}
