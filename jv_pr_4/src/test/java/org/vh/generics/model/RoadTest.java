package org.vh.generics.model;

import org.junit.Test;
import org.vh.generics.model.humans.Fireman;
import org.vh.generics.model.humans.Human;
import org.vh.generics.model.humans.Policeman;
import org.vh.generics.model.vehicles.Bus;
import org.vh.generics.model.vehicles.FireTruck;
import org.vh.generics.model.vehicles.PoliceCar;
import org.vh.generics.model.vehicles.Taxi;

import static org.junit.Assert.assertEquals;

public class RoadTest {

    // Додатково реалізувати функцію підрахунку кількості людей, які перевозяться
    // на автомобілями на дорозі. Варіант коду наданий нижче. Дописати код до
    // працездатного. Обов’язково використовувати generics та wildcard (де це потрібно).
    @Test
    public void shouldGetCountOfHumans() {
        // given
        Road road = new Road();

        Bus bus = new Bus(25);
        bus.addPassenger(new Human());
        bus.addPassenger(new Human());
        bus.addPassenger(new Fireman());
        bus.addPassenger(new Policeman());
        road.add(bus);

        Taxi taxi = new Taxi(4);
        taxi.addPassenger(new Human());
        road.add(taxi);

        FireTruck fireTruck = new FireTruck(2);
        fireTruck.addPassenger(new Fireman());
        fireTruck.addPassenger(new Fireman());
        road.add(fireTruck);

        PoliceCar policeCar = new PoliceCar(5);
        policeCar.addPassenger(new Policeman());
        policeCar.addPassenger(new Policeman());
        road.add(policeCar);

        // when
        int actualCount = road.getCountOfHumans();

        // then
        assertEquals(9, actualCount);
    }
}
