package org.vh.generics.model.vehicles;

import org.vh.generics.model.humans.Human;

public class Taxi extends Car<Human> {

    public Taxi(int maxSeatsCount) {
        super(maxSeatsCount);
    }
}
