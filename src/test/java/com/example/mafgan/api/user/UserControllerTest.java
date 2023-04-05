package com.example.mafgan.api.user;

import com.example.mafgan.domain.user.User;
import com.example.mafgan.domain.user.UserRepository;
import com.example.mafgan.domain.user.UserRole;
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

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//TODO wywalic testy ktore podnosza kostekst springa z folderu test np do folderu integrationtest
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("should add user")
    void shouldAddUser() throws Exception {
        //given
        final User user = new User(1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN));
        String requestJson = objectMapper.writeValueAsString(user);

        //when
        final var response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(requestJson)
                )
                .andDo(print())
                .andExpect(status().isOk());

        final var userFormDBInJSON = response.andReturn().getResponse().getContentAsString();
        User responseBody = objectMapper.readValue(userFormDBInJSON, new TypeReference<>() {
        });

        //then
        assertEquals(user.getId(), responseBody.getId());
        assertEquals(user.getLogin(), responseBody.getLogin());
        assertEquals(user.getPassword(), responseBody.getPassword());
        assertEquals(user.getUserRole(), responseBody.getUserRole());
    }

    @Test
    @DisplayName("should get all users")
    void shouldGetAllUsers() throws Exception {
        //given
        final List<User> userList = List.of(
                new User(1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN)),
                new User(2L, "loginUSER", "passwordUSER", Set.of(UserRole.USER))
        );

        //when
        final var response = mockMvc
                .perform(MockMvcRequestBuilders.get("/users")
                        .contentType("application/json")
                        .accept("application/json")
                )
                .andDo(print())
                .andExpect(status().isOk());

        final var userFormDBInJSON = response.andReturn().getResponse().getContentAsString();
        List<User> responseBody = objectMapper.readValue(userFormDBInJSON, new TypeReference<>() {
        });

        //then
        assertEquals(userList.size(), 2);
        assertEquals(userList.get(0).getId(), responseBody.get(0).getId());
        assertEquals(userList.get(0).getLogin(), responseBody.get(0).getLogin());
        assertEquals(userList.get(0).getPassword(), responseBody.get(0).getPassword());
        assertEquals(userList.get(0).getUserRole(), responseBody.get(0).getUserRole());
        assertEquals(userList.get(1).getId(), responseBody.get(1).getId());
        assertEquals(userList.get(1).getLogin(), responseBody.get(1).getLogin());
        assertEquals(userList.get(1).getPassword(), responseBody.get(1).getPassword());
        assertEquals(userList.get(1).getUserRole(), responseBody.get(1).getUserRole());
    }

    @Test
    @DisplayName("should find user by id")
    void shouldFindUserById() throws Exception {
        //given
        var userIdToFind = 1L;
        final User user = new User(1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN));

        //when
        final var response = mockMvc
                .perform(MockMvcRequestBuilders.get("/users/" + userIdToFind)
                        .contentType("application/json")
                        .accept("application/json")
                )
                .andDo(print())
                .andExpect(status().isOk());

        final var userFormDBInJSON = response.andReturn().getResponse().getContentAsString();
        User responseBody = objectMapper.readValue(userFormDBInJSON, new TypeReference<>() {
        });

        //then
        assertEquals(user.getId(), responseBody.getId());
        assertEquals(user.getLogin(), responseBody.getLogin());
        assertEquals(user.getPassword(), responseBody.getPassword());
        assertEquals(user.getUserRole(), responseBody.getUserRole());
    }

    @Test
    @DisplayName("should update user")
    void shouldUpdateUser() throws Exception {
        //given
        final User user = new User(1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN));
        String requestJson = objectMapper.writeValueAsString(user);

        //when
        final var response = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/users")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(requestJson)
                )
                .andDo(print())
                .andExpect(status().isOk());

        final var userFormDBInJSON = response.andReturn().getResponse().getContentAsString();
        User responseBody = objectMapper.readValue(userFormDBInJSON, new TypeReference<>() {
        });

        //then
        assertEquals(user.getId(), responseBody.getId());
        assertEquals(user.getLogin(), responseBody.getLogin());
        assertEquals(user.getPassword(), responseBody.getPassword());
        assertEquals(user.getUserRole(), responseBody.getUserRole());
    }

    @Test
    @DisplayName("should delete user")
    void shouldDeleteUser() throws Exception {
        //given
        var userIdToFind = 1L;

        //when
        final var result = mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/users/" + userIdToFind)
                        .contentType("application/json")
                        .accept("application/json")
                )
                .andDo(print())
                .andExpect(status().isNoContent());

        List<User> response = userRepository.findAll();

        //then
        assertDoesNotThrow(() -> userRepository.findAll());
        assertEquals(1, response.size());
    }
}
