package telran.citizens.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import telran.citizens.dao.Citizens;
import telran.citizens.dao.CitizensImpl;
import telran.citizens.model.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CitizensImplTest {
    private Citizens citizens;
    private static final LocalDate now = LocalDate.now();

    @BeforeEach
    void setUp() {
        citizens = new CitizensImpl();

        citizens.add(new Person(1,"Anton","Antonov", now.minusYears(20)));
        citizens.add(new Person(2,"Oleg","Olegov", now.minusYears(25)));
        citizens.add(new Person(3,"Stephan","Stephanov", now.minusYears(30)));
        citizens.add(new Person(4,"Andrei","Andreev", now.minusYears(35)));
        citizens.add(new Person(5,"Semyon","Semyonov", now.minusYears(40)));
        citizens.add(new Person(6,"Ivan","Andreev", now.minusYears(45)));
    }

    @Test
    void testAdd() {
        assertFalse(citizens.add(null));
        Person person = new Person(6,"a","b",now);
        assertFalse(citizens.add(person));
        person = new Person(7,"a","b",now);
        assertTrue(citizens.add(person));
        assertEquals(7,citizens.size());
    }

    @Test
    void testRemove() {
        assertFalse(citizens.remove(10));
        assertEquals(6,citizens.size());
        assertTrue(citizens.remove(6));
        assertEquals(5,citizens.size());
    }

    @Test
    void testFindById() {
        Person person = new Person(1,"Anton","Antonov", now.minusYears(20));
        assertEquals(person,citizens.find(1));
        assertNull(citizens.find(7));
    }

    @Test
    void testFindBetweenAge() {
        Iterable<Person> actual = citizens.find(20,30);
        ArrayList<Person> expected = new ArrayList<>();
        expected.add(citizens.find(1));
        expected.add(citizens.find(2));
        expected.add(citizens.find(3));
        assertIterableEquals(expected,actual);
    }

    @Test
    void testFindByLastName() {
        Iterable<Person> actual = citizens.find("Andreev");
        ArrayList<Person> expected = new ArrayList<>();
        expected.add(citizens.find(4));
        expected.add(citizens.find(6));
        assertIterableEquals(expected,actual);
    }

    @Test
    void testGetAllPersonsSortedById() {
        List<Person> expected = List.of(
                new Person(1, "Anton", "Antonov", now.minusYears(20)),
                new Person(2, "Oleg", "Olegov", now.minusYears(25)),
                new Person(3, "Stephan", "Stephanov", now.minusYears(30)),
                new Person(4, "Andrei", "Andreev", now.minusYears(35)),
                new Person(5, "Semyon", "Semyonov", now.minusYears(40)),
                new Person(6, "Ivan", "Andreev", now.minusYears(45))
        );

        assertIterableEquals(expected, citizens.getAllPersonsSortedById());
    }

    @Test
    void testGetAllPersonsSortedByAge() {
        List<Person> expected = List.of(
                new Person(1, "Anton", "Antonov", now.minusYears(20)),
                new Person(2, "Oleg", "Olegov", now.minusYears(25)),
                new Person(3, "Stephan", "Stephanov", now.minusYears(30)),
                new Person(4, "Andrei", "Andreev", now.minusYears(35)),
                new Person(5, "Semyon", "Semyonov", now.minusYears(40)),
                new Person(6, "Ivan", "Andreev", now.minusYears(45))
        );

        assertIterableEquals(expected, citizens.getAllPersonsSortedByAge());
    }

    @Test
    void testGetAllPersonsSortedByLastName() {
        List<Person> expected = List.of(
                new Person(4, "Andrei", "Andreev", now.minusYears(35)),
                new Person(6, "Ivan", "Andreev", now.minusYears(45)),
                new Person(1, "Anton", "Antonov", now.minusYears(20)),
                new Person(2, "Oleg", "Olegov", now.minusYears(25)),
                new Person(5, "Semyon", "Semyonov", now.minusYears(40)),
                new Person(3, "Stephan", "Stephanov", now.minusYears(30))
        );

        assertIterableEquals(expected, citizens.getAllPersonsSortedByLastName());
    }


    @Test
    void testSize() {
        assertEquals(6,citizens.size());
    }
}