package org.vh.generics.model;

import org.junit.Test;
import org.vh.generics.HumanIO;
import org.vh.generics.model.humans.Fireman;
import org.vh.generics.model.humans.Human;
import org.vh.generics.model.humans.Policeman;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HumanIOTest {
    @Test
    public void shouldSaveAndLoad() throws Exception {
        // given
        final List<Human> humans = Arrays.asList(
                new Human(),
                new Human(),
                new Policeman(),
                new Fireman()
        );

        Path fileName = Paths.get("temp.txt");

        // when
        HumanIO.save(humans, fileName);
        List<Human> actualHumans = HumanIO.load(fileName);

        // then
        assertEquals(humans.size(), actualHumans.size());
    }
}
