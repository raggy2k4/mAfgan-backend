package com.example.mafgan.domain.person;

import com.example.mafgan.error.PersonNotFoundException;
import com.example.mafgan.error.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PersonService {
    private final PersonRepository personRepository;

    @SneakyThrows
    public Person addPerson(final Person person) {
        if (person.getId() == null) {
            throw new PersonNotFoundException("Person to save should not exist in DB");
        }
        return personRepository.save(person);
    }

    public List<Person> findAllPerson() {
        return personRepository.findAll();
    }

    public Optional<Person> findPersonById(final Long id) {
        return personRepository.findById(id);
    }

    public Person updatePerson(final Person person) {
//        return personRepository.save(person);
        return personRepository.findById(person.getId())
                .map(p -> personRepository.save(person))
                .orElse(null);
    }

//    public boolean existsById(final Long id) {
//        return personRepository.existsById(id);
//    }
    public Optional<Void> deletePerson(final Long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
        }
        return Optional.empty();
    }
}
