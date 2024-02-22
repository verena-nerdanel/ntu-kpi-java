package org.vh.generics.model.vehicles;

import org.vh.generics.model.humans.Fireman;

public class FireTruck extends Car<Fireman> {

    public FireTruck(int maxSeatsCount) {
        super(maxSeatsCount);
    }
}
