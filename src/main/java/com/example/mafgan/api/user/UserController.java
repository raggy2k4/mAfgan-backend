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
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<List<UserDto>> getAllUser() {
        return ResponseEntity.ok(userMapper.toListDto(userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping()
    public ResponseEntity<UserDto> addUsers(@RequestBody UserDto newUser) {
        User user = userService.add(userMapper.toDomain(newUser));
        return ResponseEntity.ok(userMapper.toDto(user));
    }
    @PutMapping()
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userUpdate) {
       User userToUpdate = userService.update(userMapper.toDomain(userUpdate));
       return ResponseEntity.ok(userMapper.toDto(userToUpdate));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteChosenUser(@RequestBody UserDto deleteUser) {
        userService.delete(userMapper.toDomain(deleteUser));
        return ResponseEntity
                .noContent()
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

    @DeleteMapping(value = "/roles")
    public ResponseEntity<String> deleteRole(
            @RequestParam Long id,
            @RequestBody AddRoleRequest addRoleRequest
    ) {
        userService.deleteRoleInUser(id, addRoleRequest.role);
        return ResponseEntity.ok("Usunięto role!");
    }
}
