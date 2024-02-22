
package org.vh.generics.model.vehicles;

import org.junit.Test;
import org.vh.generics.model.humans.Policeman;

import static org.junit.Assert.assertEquals;

public class PoliceCarTest {

    @Test
    // міліцейська машина – тільки міліціонерів
    public void shouldAddPassenger() {
        // given
        PoliceCar policeCar = new PoliceCar(5);

        // when
        policeCar.addPassenger(new Policeman());
        policeCar.addPassenger(new Policeman());
        policeCar.addPassenger(new Policeman());

        // then
        assertEquals(5, policeCar.getMaxSeatsCount());
        assertEquals(3, policeCar.getPassengersCount());
    }
}
