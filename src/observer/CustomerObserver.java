package observer;

public class CustomerObserver implements Observer {
    private final String name;

    public CustomerObserver(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        }
        this.name = name;
    }

    @Override
    public void update(String status) {
        System.out.println("[Customer " + name + "] " + status);
    }
}
