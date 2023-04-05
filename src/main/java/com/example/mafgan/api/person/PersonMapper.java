package com.example.mafgan.api.person;

import com.example.mafgan.domain.person.Person;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonDto toDto(Person person);
    Person toDomain(PersonDto personDto);
    List<PersonDto> toListDto(List<Person> persons);
}
