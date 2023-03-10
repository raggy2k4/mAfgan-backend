package com.example.mafgan.api;

import com.example.mafgan.external.UserService;
import com.example.mafgan.external.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(
        path = "/user",
        consumes = "application/json",
        produces = "application/json"
)
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> allUser = userService.findAllUser();
        return ResponseEntity.ok(allUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping()
    public ResponseEntity addUsers(@RequestBody User user) {
        User userToAdd = userService.addUser(user);
        return ResponseEntity.ok(userToAdd);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody User userToUpdate
    ) {
       User user = userService.updateUser(id, userToUpdate);
       return ResponseEntity.ok(user);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity
                .accepted()
                .build();
    }
}
