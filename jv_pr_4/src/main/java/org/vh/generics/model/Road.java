package org.vh.generics.model;

import org.vh.generics.model.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Road {
    public List<Vehicle<?>> vehicles = new ArrayList<>();

    public void add(Vehicle<?> vehicle) {
        vehicles.add(vehicle);
    }

    public int getCountOfHumans() {
        return vehicles.stream()
                .mapToInt(Vehicle::getPassengersCount)
                .sum();
    }
}
