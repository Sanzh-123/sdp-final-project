import facade.RestaurantFacade;

public class Main {
    public static void main(String[] args) {
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
                    facade.checkout();
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
    }
}
