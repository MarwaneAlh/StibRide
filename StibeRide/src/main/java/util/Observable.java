package util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marwa
 */
public class Observable {

    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    protected void notifyObservers() {
        observers.forEach((observer) -> {
            observer.update();
        });
    }
}
