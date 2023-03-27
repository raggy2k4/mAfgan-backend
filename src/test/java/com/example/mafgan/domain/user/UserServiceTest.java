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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void findAllUsersMethodShouldReturnAllUsers() {
        //given
        List<User> userList = List.of(
                new User(1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN)),
                new User(2L, "loginUSER", "passwordUSER", Set.of(UserRole.USER))
        );

        given(userRepository.findAll()).willReturn(userList);

        //when
        List<User> result = userService.findAll();

        //then
        assertNotNull(result);
        assertEquals(userList.size(), result.size());
    }

    @Test
    void findByIdMethodShouldFindUserById() {
        //given
        var userIdToFind = 1L;
        final User userFromDb = new User(1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN));

        given(userRepository.findById(userIdToFind)).willReturn(Optional.of(userFromDb));

        //when
        User result = userService.findById(userIdToFind).get();

        //then
        assertNotNull(result);
        assertEquals(userFromDb.getId(), result.getId());
        assertEquals(userFromDb.getLogin(), result.getLogin());
        assertEquals(userFromDb.getPassword(), result.getPassword());
        assertEquals(userFromDb.getUserRole(), result.getUserRole());
    }

    @Test
    void saveMethodShouldSaveNewUserWhenUserDoesNotExist() {
        //given
       // final User userToSave = new User(null, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN));
        final User savedUser = new User(1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN));

        given(userRepository.save(savedUser)).willReturn(savedUser);

        //when
        User result = userService.save(savedUser);

        //then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(savedUser.getLogin(), result.getLogin());
        assertEquals(savedUser.getPassword(), result.getPassword());
        assertEquals(savedUser.getUserRole(), result.getUserRole());
//        assertEquals(userToSave.getLogin(), result.getLogin());
//        assertEquals(userToSave.getPassword(), result.getPassword());
//        assertEquals(userToSave.getUserRoles(), result.getUserRoles());
    }

    @Test
    void saveMethodShouldThrowNullPointerExceptionWhenUserIsNull() {
        //given
        final User userToSave = null;

        //when
        //then
        assertThrows(NullPointerException.class, () -> userService.save(userToSave));
    }

    @Test
    void updateMethodShouldUpdateUserWhenUserExist() {
        //given
        var userIdToFind = 1L;
        User user = new User(1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN));
        given(userRepository.save(user)).willReturn(user);
        given(userRepository.findById(userIdToFind)).willReturn(Optional.of(user));

        //when
        User result = userService.update(user);

        //then
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getLogin(), result.getLogin());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getUserRole(), result.getUserRole());
    }

    @Test
    void updateMethodShouldNotUpdateUserWhenUserNotExist() {
        //given
        var userIdToFind = 1L;
        User user = new User(1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN));
        given(userRepository.findById(userIdToFind)).willReturn(Optional.empty());

        //when
        User result = userService.update(user);

        //then
        assertNull(result);
        verify(userRepository, times(0)).save(user);
    }

    @Test
    void deleteByIdMethodShouldDeleteUserIfExist() {
        //given
        var userIdToFind = 1L;
        given(userRepository.existsById(userIdToFind)).willReturn(true);
        doNothing().when(userRepository).deleteById(userIdToFind);

        //when
        userService.deleteById(userIdToFind);

        //then
        assertDoesNotThrow(() -> userService.deleteById(userIdToFind));
        verify(userRepository, times(2)).deleteById(userIdToFind);
    }

    @Test
    void deleteByIdMethodShouldThrowExceptionIfNotExist() {
        //given
        var userIdToFind = 1L;
        given(userRepository.existsById(userIdToFind)).willReturn(false);

        //when
        Optional<Void> result = userService.deleteById(userIdToFind);

        //then
        assertEquals(Optional.empty(), result);
    }


    @Test
    void existsByIdMethodShouldCheckUserIfExist() {
        //given
        var userIdToFind = 1L;
        given(userRepository.existsById(userIdToFind)).willReturn(true);

        //when
        boolean result = userService.existsById(userIdToFind);

        //then
        assertTrue(result);
        verify(userRepository, times(1)).existsById(userIdToFind);
    }
}
