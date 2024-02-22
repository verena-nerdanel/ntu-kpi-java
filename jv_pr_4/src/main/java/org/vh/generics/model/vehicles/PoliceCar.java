package org.vh.generics.model.vehicles;

import org.vh.generics.model.humans.Policeman;

public class PoliceCar extends Car<Policeman> {

    public PoliceCar(int maxSeatsCount) {
        super(maxSeatsCount);
    }
}
