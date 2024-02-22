package org.vh.generics.model.vehicles;

import org.junit.Test;
import org.vh.generics.model.humans.Fireman;
import org.vh.generics.model.humans.Human;
import org.vh.generics.model.humans.Policeman;

import static org.junit.Assert.assertEquals;

public class TaxiTest {

    @Test
    // Автобус та таксі можуть перевозити будь-яких пасажирів
    public void shouldAddPassenger() {
        // given
        Taxi taxi = new Taxi(5);

        // when
        taxi.addPassenger(new Human());
        taxi.addPassenger(new Fireman());
        taxi.addPassenger(new Policeman());

        // then
        assertEquals(5, taxi.getMaxSeatsCount());
        assertEquals(3, taxi.getPassengersCount());
    }
}
