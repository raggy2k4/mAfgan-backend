package com.example.mafgan.api.user;

import com.example.mafgan.domain.user.User;
import com.example.mafgan.domain.user.UserRepository;
import com.example.mafgan.domain.user.UserRole;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllUser() throws Exception {
        //given
        List<User> userList = List.of(
                new User(1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN)),
                new User(2L, "loginUSER", "passwordUSER", Set.of(UserRole.USER))
        );

        userRepository.saveAll(userList);

        //when
        final var response = mockMvc
                .perform(MockMvcRequestBuilders.get("/users")
                        .contentType("application/json")
                        .accept("application/json")
                )
                .andDo(print())
                .andExpect(status().isOk());

        final var userFormDBInJSON = response.andReturn().getResponse().getContentAsString();
        List<UserDto> responseBody = objectMapper.readValue(userFormDBInJSON, new TypeReference<>() {
        });

        //then
        assertEquals(userList.size(), responseBody.size());
        assertEquals(userList.get(0).getIdUser(), responseBody.get(0).getIdUser());
        assertEquals(userList.get(0).getLogin(), responseBody.get(0).getLogin());
        assertEquals(userList.get(0).getPassword(), responseBody.get(0).getPassword());
        assertEquals(userList.get(0).getUserRoles(), responseBody.get(0).getUserRoles());
        assertEquals(userList.get(1).getIdUser(), responseBody.get(1).getIdUser());
        assertEquals(userList.get(1).getLogin(), responseBody.get(1).getLogin());
        assertEquals(userList.get(1).getPassword(), responseBody.get(1).getPassword());
        assertEquals(userList.get(1).getUserRoles(), responseBody.get(1).getUserRoles());
    }

    @Test
    void shouldFindUserById() throws Exception {
        //given
        var SOME_ID = 1L;
        User user = new User(
                1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN)
        );

        userRepository.save(user);

        //when
        final var response = mockMvc
                .perform(MockMvcRequestBuilders.get("/users/" + SOME_ID)
                        .contentType("application/json")
                        .accept("application/json")
                )
                .andDo(print())
                .andExpect(status().isOk());

        final var userFormDBInJSON = response.andReturn().getResponse().getContentAsString();
        UserDto responseBody = objectMapper.readValue(userFormDBInJSON, new TypeReference<>() {
        });

        //then
        assertEquals(user.getIdUser(), responseBody.getIdUser());
        assertEquals(user.getLogin(), responseBody.getLogin());
        assertEquals(user.getPassword(), responseBody.getPassword());
        assertEquals(user.getUserRoles(), responseBody.getUserRoles());
    }

    @Test
    void shouldAddUsers() throws Exception {
        //given
        UserDto userDto = new UserDto(
                1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN)
        );

        String requestJson = objectMapper.writeValueAsString(userDto);

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
        UserDto responseBody = objectMapper.readValue(userFormDBInJSON, new TypeReference<>() {
        });

        //then
        assertEquals(userDto.getIdUser(), responseBody.getIdUser());
        assertEquals(userDto.getLogin(), responseBody.getLogin());
        assertEquals(userDto.getPassword(), responseBody.getPassword());
        assertEquals(userDto.getUserRoles(), responseBody.getUserRoles());
    }

    @Test
    @Transactional
    void shouldDeleteUser() throws Exception {
        //given
        var SOME_ID = 1L;
        List<User> userList = List.of(
                new User(1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN)),
                new User(2L, "loginUSER", "passwordUSER", Set.of(UserRole.USER))
        );

        userRepository.saveAll(userList);

        //when
        final var result = mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/users/" + SOME_ID)
                        .contentType("application/json")
                        .accept("application/json")
                )
                .andDo(print())
                .andExpect(status().isNoContent());//.andExpect(status().is(204));

        List<User> response = userRepository.findAll();

        //then
        assertEquals(1, response.size());
    }

    @Test
    @Transactional
    void shouldChosenUserDelete() throws Exception {
        //given
        List<User> userList = List.of(
                new User(1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN)),
                new User(2L, "loginUSER", "passwordUSER", Set.of(UserRole.USER))
        );

        userRepository.saveAll(userList);

        //when
        final var result = mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/users/delete/" + userList.get(0).getIdUser())
                        .contentType("application/json")
                        .accept("application/json")
                )
                .andDo(print())
                .andExpect(status().isNoContent());//.andExpect(status().is(204));

        List<User> response = userRepository.findAll();

        //then
        result.andExpect(status().is(204)).andDo(print());
        assertEquals(1, response.size());
    }


    @Test
    void shouldUpdateUser() throws Exception {
        //given
        UserDto userDto = new UserDto(
                1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN)
        );

        String requestJson = objectMapper.writeValueAsString(userDto);

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
        UserDto responseBody = objectMapper.readValue(userFormDBInJSON, new TypeReference<>() {
        });

        //then
        assertEquals(userDto.getIdUser(), responseBody.getIdUser());
        assertEquals(userDto.getLogin(), responseBody.getLogin());
        assertEquals(userDto.getPassword(), responseBody.getPassword());
        assertEquals(userDto.getUserRoles(), responseBody.getUserRoles());
    }

    @Test
    void addRoleToUser() {
        //given
        var SOME_ID = 1L;
        AddRoleRequest addRoleRequest = new AddRoleRequest(UserRole.ADMIN.name());

        //when


        //then

    }
}