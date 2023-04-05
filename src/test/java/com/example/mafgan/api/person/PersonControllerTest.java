package com.example.mafgan.api.person;

import com.example.mafgan.domain.person.Person;
import com.example.mafgan.domain.person.PersonRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PersonControllerTest {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("should add person")
    void shouldAddPerson() throws Exception {
        //given
        Person person = new Person(1L, "nickPerson1", "teamPerson1", "locationPerson1");
        String requestJson = objectMapper.writeValueAsString(person);

        //when
        final var response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/persons")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(requestJson)
                )
                .andDo(print())
                .andExpect(status().isOk());

        final var personFormDBInJSON = response.andReturn().getResponse().getContentAsString();
        Person responseBody = objectMapper.readValue(personFormDBInJSON, new TypeReference<>() {
        });

        //then
        assertEquals(person.getId(), responseBody.getId());
        assertEquals(person.getNick(), responseBody.getNick());
        assertEquals(person.getTeam(), responseBody.getTeam());
        assertEquals(person.getLocation(), responseBody.getLocation());
    }

    @Test
    @DisplayName("should get all persons")
    void shouldGetAllPerson() throws Exception {
        //given
        final List<Person> personList = Arrays.asList(
                new Person(1L, "nickPerson1", "teamPerson1", "locationPerson1"),
                new Person(2L, "nickPerson2", "teamPerson2", "locationPerson2")
        );

        //when
        final var response = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/persons")
                        .contentType("application/json")
                        .accept("application/json")
                )
                .andDo(print())
                .andExpect(status().isOk());

        final var personFormDBInJSON = response.andReturn().getResponse().getContentAsString();
        List<Person> responseBody = objectMapper.readValue(personFormDBInJSON, new TypeReference<>() {
        });

        //then
        assertEquals(personList.size(), 2);
        assertEquals(personList.get(0).getId(), responseBody.get(0).getId());
        assertEquals(personList.get(0).getNick(), responseBody.get(0).getNick());
        assertEquals(personList.get(0).getTeam(), responseBody.get(0).getTeam());
        assertEquals(personList.get(0).getLocation(), responseBody.get(0).getLocation());
        assertEquals(personList.get(1).getId(), responseBody.get(1).getId());
        assertEquals(personList.get(1).getNick(), responseBody.get(1).getNick());
        assertEquals(personList.get(1).getTeam(), responseBody.get(1).getTeam());
        assertEquals(personList.get(1).getLocation(), responseBody.get(1).getLocation());
    }

    @Test
    @DisplayName("should find person by id")
    void shouldGetPersonById() throws Exception {
        //given
        var userIdToFind = 1L;
        Person person = new Person(1L, "nickPerson1", "teamPerson1", "locationPerson1");

        //when
        final var response = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/persons/" + userIdToFind)
                        .contentType("application/json")
                        .accept("application/json")
                )
                .andDo(print())
                .andExpect(status().isOk());

        final var personFormDBInJSON = response.andReturn().getResponse().getContentAsString();
        Person responseBody = objectMapper.readValue(personFormDBInJSON, new TypeReference<>() {
        });

        //then
        assertEquals(person.getId(), responseBody.getId());
        assertEquals(person.getNick(), responseBody.getNick());
        assertEquals(person.getTeam(), responseBody.getTeam());
        assertEquals(person.getLocation(), responseBody.getLocation());
    }

    @Test
    @DisplayName("should update person")
    void shouldUpdatePerson() throws Exception {
        //given
        Person person = new Person(1L, "nickPersonUpdate", "teamPersonUpdate", "locationPersonUpdate");
        String requestJson = objectMapper.writeValueAsString(person);

        //when
        final var response = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/persons")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(requestJson)
                )
                .andDo(print())
                .andExpect(status().isOk());

        final var personFormDBInJSON = response.andReturn().getResponse().getContentAsString();
        Person responseBody = objectMapper.readValue(personFormDBInJSON, new TypeReference<>() {
        });

        //then
        assertEquals(person.getId(), responseBody.getId());
        assertEquals(person.getNick(), responseBody.getNick());
        assertEquals(person.getTeam(), responseBody.getTeam());
        assertEquals(person.getLocation(), responseBody.getLocation());
    }

    @Test
    @DisplayName("should delete person")
    void shouldDeletePerson() throws Exception {
        //given
        var userIdToFind = 1L;

        //when
        final var result = mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/persons/" + userIdToFind)
                        .contentType("application/json")
                        .accept("application/json")
                )
                .andDo(print())
                .andExpect(status().isNoContent());

        List<Person> response = personRepository.findAll();

        //then
        assertDoesNotThrow(() -> personRepository.findAll());
        assertEquals(1, response.size());
    }
}