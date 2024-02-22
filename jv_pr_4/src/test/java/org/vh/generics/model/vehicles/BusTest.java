package org.vh.generics.model.vehicles;

import org.junit.Test;
import org.vh.generics.model.humans.Fireman;
import org.vh.generics.model.humans.Human;
import org.vh.generics.model.humans.Policeman;

import static org.junit.Assert.assertEquals;

public class BusTest {

    // Автобус та таксі можуть перевозити будь-яких пасажирів
    @Test
    public void shouldAddPassenger() {
        // given
        Bus bus = new Bus(5);

        // when
        bus.addPassenger(new Human());
        bus.addPassenger(new Fireman());
        bus.addPassenger(new Policeman());

        // then
        assertEquals(5, bus.getMaxSeatsCount());
        assertEquals(3, bus.getPassengersCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAddPassenger() {
        // given
        Bus bus = new Bus(2);

        // when
        bus.addPassenger(new Human());
        bus.addPassenger(new Human());
        bus.addPassenger(new Human());

        // then
        // exception expected
    }

    // Висадка пасажира із транспортного засобу
    @Test
    public void shouldRemovePassenger() {
        // given
        Bus bus = new Bus(25);
        Human passenger1 = new Human();
        Human passenger2 = new Policeman();
        Human passenger3 = new Human();

        bus.addPassenger(passenger1);
        bus.addPassenger(passenger2);
        bus.addPassenger(passenger3);

        // when
        bus.removePassenger(passenger1);

        // then
        assertEquals(2, bus.getPassengersCount());
    }

    // Висадка пасажира із транспортного засобу. Функція повинна ініціювати виключну
    // ситуацію, якщо вказаний пасажир «не сидить» у транспортному засобі.
    @Test(expected = IllegalArgumentException.class)
    public void shouldNotRemovePassenger() {
        // given
        Bus bus = new Bus(25);
        bus.addPassenger(new Human());
        bus.addPassenger(new Policeman());
        bus.addPassenger(new Human());

        // when
        bus.removePassenger(new Fireman());

        // then
        // exception expected
    }
}
