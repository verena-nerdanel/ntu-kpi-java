package org.vh.generics.model.vehicles;

import org.junit.Test;
import org.vh.generics.model.humans.Fireman;

import static org.junit.Assert.assertEquals;

public class FireTruckTest {

    @Test
    // пожежна машина – тільки пожежників
    public void shouldAddPassenger() {
        // given
        FireTruck fireTruck = new FireTruck(5);

        // when
        fireTruck.addPassenger(new Fireman());
        fireTruck.addPassenger(new Fireman());
        fireTruck.addPassenger(new Fireman());

        // then
        assertEquals(5, fireTruck.getMaxSeatsCount());
        assertEquals(3, fireTruck.getPassengersCount());
    }
}
