package observer;

import java.util.ArrayList;
import java.util.List;

public class OrderSubject {
    private List<Observer> observers = new ArrayList<>();
    private String status;
    public void attach(Observer o) {
        observers.add(o);
    }
    public void setStatus(String s) {
        status = s;
        for (Observer o : observers)
            o.update(status);
    }
}
