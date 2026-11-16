package org.example.facade;

import org.example.observer.CustomerObserver;
import org.example.observer.KitchenObserver;
import org.example.observer.OrderSubject;

public class RestaurantFacade {
    private OrderSubject subject = new OrderSubject();
    public RestaurantFacade() {
        subject.attach(new KitchenObserver());
        subject.attach(new CustomerObserver("Ali"));
    }
    public void orderMeal(String meal) {
        subject.setStatus("Order received: " + meal);
        subject.setStatus("Preparing " + meal);
        subject.setStatus(meal + " is ready!");
    }
}
