import facade.RestaurantFacade;
import ui.RestaurantWindow;

import javax.swing.SwingUtilities;
import java.awt.GraphicsEnvironment;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && "--console".equalsIgnoreCase(args[0])) {
            runConsoleApplication();
            return;
        }

        if (GraphicsEnvironment.isHeadless()) {
            System.err.println("A graphical environment is not available. Starting console mode.");
            runConsoleApplication();
            return;
        }

        SwingUtilities.invokeLater(() -> {
            RestaurantWindow window = new RestaurantWindow(new RestaurantFacade());
            window.setVisible(true);
        });
    }

    private static void runConsoleApplication() {
        RestaurantFacade facade = new RestaurantFacade();
        System.out.println("=== Restaurant Ordering System ===");

        boolean running = true;
        while (running) {
            facade.printMenu();
            int choice = facade.readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    facade.addMeal("pizza");
                    break;
                case 2:
                    facade.addMeal("burger");
                    break;
                case 3:
                    facade.printOrder();
                    break;
                case 4:
                    facade.selectDiscount();
                    break;
                case 5:
                    if (facade.checkout()) {
                        running = false;
                    }
                    break;
                case 0:
                    System.out.println("Order cancelled. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Please choose a listed option.\n");
            }
        }
    }
}
