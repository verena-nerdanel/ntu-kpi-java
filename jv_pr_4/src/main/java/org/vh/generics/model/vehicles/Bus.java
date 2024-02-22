package org.vh.generics.model.vehicles;

import org.vh.generics.model.humans.Human;

public class Bus extends Vehicle<Human> {

    public Bus(int maxSeatsCount) {
        super(maxSeatsCount);
    }
}
