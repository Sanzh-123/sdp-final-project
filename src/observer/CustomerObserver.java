package org.example.observer;

public class CustomerObserver implements Observer {
    private String name;
    public CustomerObserver(String name) {
        this.name = name;
    }
    public void update(String s) {
        System.out.println("[Customer " + name + "] " + s);
    }
}
