package org.vh.generics.model.vehicles;

import org.vh.generics.model.humans.Human;

public abstract class Car<T extends Human> extends Vehicle<T> {

    public Car(int maxSeatsCount) {
        super(maxSeatsCount);
    }
}
