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

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userMapper.toListDto(userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable final Long id) {
        User user = userService.findById(id).get();
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping()
    public ResponseEntity<UserDto> addUser(@RequestBody final UserDto newUser) {
        User user = userService.save(userMapper.toDomain(newUser));
        return ResponseEntity.ok(userMapper.toDto(user));
    }
    @PutMapping()
    public ResponseEntity<UserDto> updateUser(@RequestBody final UserDto userUpdate) {
       User userToUpdate = userService.update(userMapper.toDomain(userUpdate));
       return ResponseEntity.ok(userMapper.toDto(userToUpdate));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable final Long id) {
        userService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
