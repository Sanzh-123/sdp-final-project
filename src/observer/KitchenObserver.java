package observer;

public class KitchenObserver implements Observer {
    public void update(String s) {
        System.out.println("[Kitchen] " + s);
    }
}
