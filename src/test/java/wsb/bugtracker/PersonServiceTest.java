package wsb.bugtracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import wsb.bugtracker.models.Person;
import wsb.bugtracker.repositories.PersonRepository;
import wsb.bugtracker.services.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Sprawdzam, czy metoda findAll() zwraca poprawną listę wszystkich osób.
    @Test
    void findAll_ShouldReturnAllPersons() {

        // Arrange
        List<Person> persons = new ArrayList<>();
        persons.add(new Person());
        persons.add(new Person());
        when(personRepository.findAll()).thenReturn(persons);

        // Act
        List<Person> result = personService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(personRepository, times(1)).findAll();
    }

    // Sprawdzam, czy metoda findById() zwraca poprawną osobę dla istniejącego id.
    @Test
    void findById_ExistingId_ShouldReturnPerson() {

        // Arrange
        Long personId = 1L;
        Person person = new Person();
        person.setId(personId);
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));

        // Act
        Person result = personService.findById(personId);

        // Assert
        assertEquals(personId, result.getId());
        verify(personRepository, times(1)).findById(personId);
    }

    // Sprawdzam, czy metoda findById() zwraca null dla nieistniejącego id.
    @Test
    void findById_NonExistingId_ShouldReturnNull() {

        // Arrange
        Long personId = 1L;
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        // Act
        Person result = personService.findById(personId);

        // Assert
        assertNull(result);
        verify(personRepository, times(1)).findById(personId);
    }

    // Sprawdzam, czy metoda save() zapisuje nową osobę.
    @Test
    void save_NewPerson_ShouldSavePerson() {

        // Arrange
        Person person = new Person();

        // Act
        personService.save(person);

        // Assert
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void delete_ExistingId_ShouldDeletePerson() {

        // Arrange
        Long personId = 1L;

        // Act
        personService.delete(personId);

        // Assert
        verify(personRepository, times(1)).deleteById(personId);
    }
}
