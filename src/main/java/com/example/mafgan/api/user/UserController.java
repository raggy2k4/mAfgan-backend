package com.example.mafgan.api.user;

import com.example.mafgan.domain.user.User;
import com.example.mafgan.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        path = "/users",
        consumes = "application/json",
        produces = "application/json"
)
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    //TODO UserEntity -> UserDto -> wysyłam przez Mapper do serwisu, usunuć metody po zrobieniu mappera z UserEntity
    //TODO Dodaj endpoint do dodawania roli
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser() {
        List<UserDto> userDtoList = userService.findAllUser().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping()
    public ResponseEntity<UserDto> addUsers(@RequestBody User newUser) {
        User user = userService.addUser(newUser);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @RequestBody User userUpdate
    ) {
       User userToUpdate = userService.updateUser(id, userUpdate);
       return ResponseEntity.ok(userMapper.toDto(userToUpdate));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping(path = "/{id}/roles")
    public ResponseEntity<String> addRoleToUser(
            @PathVariable Long id,
            @RequestBody AddRoleRequest addRoleRequest
    ) {
        userService.addRoleToUser(id, addRoleRequest.role);
        return ResponseEntity.ok("Dodano role");
    }
}
