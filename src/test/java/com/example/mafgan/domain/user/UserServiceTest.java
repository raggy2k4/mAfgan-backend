package com.example.mafgan.domain.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @Test
    void shouldFindAllUsers() {
        //given
        List<User> userList = List.of(
                new User(1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN)),
                new User(2L, "loginUSER", "passwordUSER", Set.of(UserRole.USER))
        );

        given(userRepository.findAll()).willReturn(userList);

        //when
        List<User> result = userService.findAll();

        //then
        assertEquals(userList.size(), result.size());
    }

    @Test
    void shouldFindById() {
        //given
        User user = new User(
                1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN)
        );

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        //when
        User result = userService.findById(user.getIdUser());

        //then
        assertEquals(user.getIdUser(), result.getIdUser());
        assertEquals(user.getLogin(), result.getLogin());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getUserRoles(), result.getUserRoles());
    }

    @Test
    void shouldAdd() {
        //given
        User user = new User(
                1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN)
        );

        given(userRepository.save(any())).willReturn(user);

        //when
        User result = userService.add(user);

        //then
        assertEquals(user.getIdUser(), result.getIdUser());
        assertEquals(user.getLogin(), result.getLogin());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getUserRoles(), result.getUserRoles());
    }

    @Test
    void shouldUpdate() {
        //given
        User user = new User(
                1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN)
        );

        given(userRepository.save(any())).willReturn(user);

        //when
        User result = userService.update(user);

        //then
        assertEquals(user.getIdUser(), result.getIdUser());
        assertEquals(user.getLogin(), result.getLogin());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getUserRoles(), result.getUserRoles());
    }

    @Test
    void shouldDeleteById() {
        //given
        var SOME_ID = 1L;

        doNothing().when(userRepository).deleteById(anyLong());

        //when
        userService.deleteById(SOME_ID);

        //then
        assertDoesNotThrow(() -> userService.deleteById(SOME_ID));
        verify(userRepository, times(2)).deleteById(SOME_ID);
    }

    @Test
    void shouldChosenUserDelete() {
        //given
        User user = new User(
                1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN)
        );

        doNothing().when(userRepository).delete(isA(User.class));

        //when
        userService.delete(user);

        //then
        assertDoesNotThrow(() -> userService.delete(user));
        verify(userRepository, times(2)).delete(user);
    }

    @Test
    void shouldCheckUserExistsById() {
        //given
        var SOME_ID = 1L;
        given(userRepository.existsById(anyLong())).willReturn(true);

        //when
        boolean result = userService.existsById(SOME_ID);

        //then
        assertTrue(result);
        verify(userRepository, times(1)).existsById(SOME_ID);

    }

    @Test
    void shouldAddRoleToUser() {
//        //given
//        User user = spy(new User(
//                1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN)
//        ));
//
//        given(userRepository.findById(userArgumentCaptor.capture().getIdUser())).willReturn(Optional.of(user));

        //when
        //userService.addRoleToUser(1L, UserRole.USER.name());

        //then
//        assertEquals(user.getLogin(), contains("loginADMIN"));
//        verify(userRepository, times(1)).findById(anyLong());

    }
}