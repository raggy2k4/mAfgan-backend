package com.example.mafgan.domain.person;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @Mock
    PersonRepository personRepository;
    @InjectMocks
    PersonService personService;

    @Test
    void saveMethodShouldSaveNewPersonWhenPersonDoesNotExist() {
        //given
        Person person = new Person(1L, "nickPerson", "teamPerson", "locationPerson");
        given(personRepository.save(person)).willReturn(person);

        //when
        Person result = personService.addPerson(person);

        //then
        assertNotNull(result);
        assertEquals(person.getId(), result.getId());
    }

    @Test
    void saveMethodShouldThrowNullPointerExceptionWhenPersonIsNull() {
        //given
        final Person personToSave = null;

        //when
        //then
        assertThrows(NullPointerException.class, () -> personService.addPerson(personToSave));
    }

    @Test
    void findAllPersonMethodShouldReturnAllPerson() {
        //given
        List<Person> person = List.of(
                new Person(1L, "nickPerson1", "teamPerson1", "locationPerson1"),
                new Person(2L, "nickPerson2", "teamPerson2", "locationPerson2")
        );
        given(personRepository.findAll()).willReturn(person);

        //when
        List<Person> result = personService.findAllPerson();

        //then
        assertNotNull(result);
        assertEquals(person.size(), result.size());
    }

    @Test
    void findByIdMethodShouldFindPersonById() {
        //given
        var personIdToFind = 1L;
        final Person person = new Person(1L, "nickPerson1", "teamPerson1", "locationPerson1");
        given(personRepository.findById(person.getId())).willReturn(Optional.of(person));

        //when
        Person result = personService.findPersonById(personIdToFind).get();

        //then
        assertNotNull(result);
        assertEquals(person.getId(), result.getId());
        assertEquals(person.getNick(), result.getNick());
        assertEquals(person.getTeam(), result.getTeam());
        assertEquals(person.getLocation(), result.getLocation());
    }

    @Test
    void updateMethodShouldUpdatePersonWhenPersonExist() {
        //given
        final Person person = new Person(1L, "nickPerson", "teamPerson", "locationPerson");
        given(personRepository.save(person)).willReturn(person);
        given(personRepository.findById(person.getId())).willReturn(Optional.of(person));

        //then
        Person result = personService.updatePerson(person);

        //when
        assertNotNull(result);
        assertEquals(person.getId(), result.getId());
        assertEquals(person.getNick(), result.getNick());
        assertEquals(person.getTeam(), result.getTeam());
        assertEquals(person.getLocation(), result.getLocation());
    }

    @Test
    void updateMethodShouldNotUpdatePersonWhenPersonNotExist() {
        //given
        final Person person = new Person(1L, "nickPerson", "teamPerson", "locationPerson");
        given(personRepository.findById(person.getId())).willReturn(Optional.of(person));

        //then
        Person result = personService.updatePerson(person);

        //when
        assertNull(result);
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void deleteByIdMethodShouldDeletePersonIfExist() {
        //given
        var userIdToFind = 1L;
//        final Person person = spy(new Person(1L, "nickPerson", "teamPerson", "locationPerson"));
        given(personRepository.existsById(userIdToFind)).willReturn(true);
        doNothing().when(personRepository).deleteById(userIdToFind);

        //when
        personService.deletePerson(userIdToFind);

        //then
        assertDoesNotThrow(() -> personService.deletePerson(userIdToFind));
        verify(personRepository, times(2)).deleteById(userIdToFind);
        verify(personRepository, times(2)).existsById(userIdToFind);
    }
}