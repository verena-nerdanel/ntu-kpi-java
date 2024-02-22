package org.vh.generics.model.vehicles;

import org.vh.generics.model.humans.Human;

import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle<T extends Human> {
    protected final List<T> passengers = new ArrayList<>();
    protected final int maxSeatsCount;

    public Vehicle(int maxSeatsCount) {
        this.maxSeatsCount = maxSeatsCount;
    }

    public void addPassenger(T passenger) {
        if (passengers.size() >= maxSeatsCount) {
            throw new IllegalArgumentException("Out of capacity");
        }

        passengers.add(passenger);
    }

    public void removePassenger(T passenger) {
        if (!passengers.contains(passenger)) {
            throw new IllegalArgumentException("Passenger not present in vehicle");
        }

        passengers.remove(passenger);
    }

    public int getPassengersCount() {
        return passengers.size();
    }

    public int getMaxSeatsCount() {
        return maxSeatsCount;
    }
}
