package sg.edu.nus.iss.d13revision;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import sg.edu.nus.iss.d13revision.models.Person;
import sg.edu.nus.iss.d13revision.services.PersonService;

public class PersonServiceTest {

    @Test
    public void constructor_shouldInitializeDefaultPersons() {
        PersonService service = new PersonService();

        List<Person> persons = service.getPersons();

        assertNotNull(persons);
        assertEquals(2, persons.size());
        assertEquals("Mark", persons.get(0).getFirstName());
        assertEquals("Zuckerberg", persons.get(0).getLastName());
        assertEquals("Elon", persons.get(1).getFirstName());
        assertEquals("Musk", persons.get(1).getLastName());
    }

    @Test
    public void addPerson_shouldAppendNewPerson() {
        PersonService service = new PersonService();
        Person person = new Person("Ada", "Lovelace");

        service.addPerson(person);

        List<Person> persons = service.getPersons();
        assertEquals(3, persons.size());
        assertTrue(persons.stream()
                .anyMatch(p -> "Ada".equals(p.getFirstName()) && "Lovelace".equals(p.getLastName())));
    }

    @Test
    public void updatePerson_shouldReplaceMatchingPersonById() {
        PersonService service = new PersonService();
        Person original = service.getPersons().get(0);
        Person updated = new Person(original.getId(), "Updated", "Person");

        service.updatePerson(updated);

        Person found = service.getPersons().stream()
                .filter(p -> p.getId().equals(original.getId()))
                .findFirst()
                .orElseThrow();

        assertEquals("Updated", found.getFirstName());
        assertEquals("Person", found.getLastName());
    }

    @Test
    public void removePerson_shouldDeleteMatchingPersonById() {
        PersonService service = new PersonService();
        Person personToRemove = service.getPersons().get(0);

        service.removePerson(personToRemove);

        List<Person> persons = service.getPersons();
        assertEquals(1, persons.size());
        assertFalse(persons.stream()
                .anyMatch(p -> p.getId().equals(personToRemove.getId())));
    }
}
