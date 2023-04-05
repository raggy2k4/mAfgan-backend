package com.example.mafgan.api.person;

import com.example.mafgan.domain.person.Person;
import com.example.mafgan.domain.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(
        path = "/persons",
        consumes = "application/json",
        produces = "application/json"
)
public class PersonController {
    private final PersonService personService;
    private final PersonMapper personMapper;

    @PostMapping()
    public ResponseEntity<PersonDto> addPerson(@RequestBody PersonDto personDto) {
        final Person person = personService.addPerson(personMapper.toDomain(personDto));
        return ResponseEntity.ok(personMapper.toDto(person));
    }

    @GetMapping()
    public ResponseEntity<List<PersonDto>> getAllPerson() {
        return ResponseEntity.ok(personMapper.toListDto(personService.findAllPerson()));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PersonDto> getPersonById(@PathVariable final Long id) {
        return ResponseEntity.ok(personMapper.toDto(personService.findPersonById(id).get()));
    }

    @PutMapping()
    public ResponseEntity<PersonDto> updatePerson(@RequestBody PersonDto personDto) {
        final Person updatePerson = personService.updatePerson(personMapper.toDomain(personDto));
        return ResponseEntity.ok(personMapper.toDto(updatePerson));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable final Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
