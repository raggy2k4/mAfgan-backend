package com.example.mafgan.domain.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

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
        assertNotNull(result);
        assertEquals(userList.size(), result.size());
    }

    @Test
    void shouldFindById() {
        //given
        Long userIdToFind = 1L;
        final User userFromDb = new User(1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN));

//        given(userRepository.findById(userIdToFind)).willReturn(Optional.of(userFromDb));
        when(userRepository.findById(userIdToFind)).thenReturn(Optional.of(userFromDb));

        //when
        User result = userService.findById(userIdToFind).get();

        //then
        assertEquals(userFromDb.getIdUser(), result.getIdUser());
        assertEquals(userFromDb.getLogin(), result.getLogin());
        assertEquals(userFromDb.getPassword(), result.getPassword());
        assertEquals(userFromDb.getUserRoles(), result.getUserRoles());
    }



    // TODO do przegadania z piotrkiem czy to jakis wielki blad
    @Test
    void shouldAdd() {
        //given
        User user = new User(1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN));

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
    void addMethodShouldSaveNewUserWhenUserDoesNotExist() {
        //given
        final User userToSave = new User(null, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN));
        final User userSaved = new User(1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN));

        given(userRepository.save(userToSave)).willReturn(userSaved);

        //when
        User result = userService.add(userToSave);

        //then
        assertNotNull(userSaved);
        assertNull(userToSave.getIdUser());
        assertEquals(1L, result.getIdUser());
        assertEquals(userToSave.getLogin(), result.getLogin());
        assertEquals(userToSave.getPassword(), result.getPassword());
        assertEquals(userToSave.getUserRoles(), result.getUserRoles());
    }

    @Test
    void addMethodShouldThrowNullPointerExceptionWhenUserIsNull() {
        //given
        final User userToSave = null;

        //when
        //then
        assertThrows(NullPointerException.class, () -> userService.add(userToSave));
    }

    // TODO update powinien zwracac usera do aktualzacji ktory zostal wyslany
    // TODO update powinien zwrocic optional empty jesli usera ni ma

    @Test
    void shouldUpdate() {
        //given
        User user = new User(1L, "loginADMIN", "passwordADMIN", Set.of(UserRole.ADMIN));

        given(userRepository.save(any())).willReturn(user);

        //when
        User result = userService.update(user);

        //then
        assertEquals(user.getIdUser(), result.getIdUser());
        assertEquals(user.getLogin(), result.getLogin());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getUserRoles(), result.getUserRoles());
    }

    //TODO nazwa do poprawy
    //TODO metada nie rzuca wyjatku jesli user jest
    //TODO metda rzuca jakis tam wyjatek jesli usera ni ma
    //TODO
    @Test
    void shouldDeleteById() {
        //given
        var SOME_ID = 1L; //TODO duze lietery??? private static final sie pisze tak tylko

        doNothing().when(userRepository).deleteById(anyLong());

        //when
        userService.deleteById(SOME_ID);

        //then
        assertDoesNotThrow(() -> userService.deleteById(SOME_ID));
        verify(userRepository, times(2)).deleteById(SOME_ID);
    }


    @Test
    void shouldCheckUserExistsById() {
        //given
        var SOME_ID = 1L;
        given(userRepository.existsById(SOME_ID)).willReturn(true);

        //when
        boolean result = userService.existsById(SOME_ID);

        //then
        assertTrue(result);
        verify(userRepository, times(1)).existsById(SOME_ID);

    }

}
