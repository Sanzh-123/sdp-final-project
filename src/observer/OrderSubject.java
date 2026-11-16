package observer;

import java.util.ArrayList;
import java.util.List;

/** Subject that notifies observers whenever the order status changes. */
public class OrderSubject {
    private final List<Observer> observers = new ArrayList<>();
    private String status;

    public void attach(Observer observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Observer cannot be null.");
        }
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Order status cannot be empty.");
        }
        this.status = status;
        for (Observer observer : new ArrayList<>(observers)) {
            observer.update(status);
        }
    }
}
