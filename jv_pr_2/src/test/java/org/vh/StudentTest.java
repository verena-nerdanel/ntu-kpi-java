package org.vh;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class StudentTest {

    @Test
    public void shouldBeEqual() {
        // given
        Student s1 = new Student();
        s1.setName("Vira");
        s1.setSurname("Gaidar");
        s1.setBirthday(LocalDate.of(1990, 6, 18));
        s1.setGroup("ЗП21");

        Student s2 = new Student();
        s2.setName("Vira");
        s2.setSurname("Gaidar");
        s2.setBirthday(LocalDate.of(1990, 6, 18));
        s2.setGroup("ЗП21");

        // when / then
        assertEquals(s1, s2);
    }

    @Test
    public void shouldBeEqualJSON() throws Exception {
        // given
        Student s1 = new Student();
        s1.setName("Vira");
        s1.setSurname("Gaidar");
        s1.setBirthday(LocalDate.of(1990, 6, 18));
        s1.setGroup("ЗП21");

        Student s2 = cloneViaJSON(s1);

        // when / then
        assertEquals(s1, s2);
    }

    @Test
    public void shouldComplyEqualsContract() {
        // given / when / then
        EqualsVerifier.simple().forClass(Student.class).verify();
    }

    private static Student cloneViaJSON(Student student) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        String data = mapper.writeValueAsString(student);
        return mapper.readValue(data, Student.class);
    }

    @Test
    public void shouldNotBeEqual() {
        // given
        Student s1 = new Student();
        s1.setName("Vira");
        s1.setSurname("Gaidar");
        s1.setBirthday(LocalDate.of(1990, 6, 18));
        s1.setGroup("ЗП21");

        Student s2 = new Student();
        s2.setName("Markus");
        s2.setSurname("Murkoth");
        s2.setBirthday(LocalDate.of(2021, 4, 10));
        s2.setGroup("Home");

        // when / then
        assertNotEquals(s1, s2);
    }
}
