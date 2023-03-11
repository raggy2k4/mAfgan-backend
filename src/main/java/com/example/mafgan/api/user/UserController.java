package com.example.mafgan.api.user;

import com.example.mafgan.domain.user.UserService;
import com.example.mafgan.external.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(
        path = "/users",
        consumes = "application/json",
        produces = "application/json"
)
public class UserController {
    private final UserService userService;

    //TODO UserEntity -> UserDto -> wysyłam przez Mapper do serwisu, usunuć metody po zrobieniu mappera z UserEntity
    //TODO Dodaj endpoint do dodawania roli
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUser() {
        List<UserEntity> allUserEntity = userService.findAllUser();
        return ResponseEntity.ok(allUserEntity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        UserEntity userEntity = userService.findUserById(id);
        return ResponseEntity.ok(userEntity);
    }

    @PostMapping(path = "/{id}/roles")
    public ResponseEntity addRoleToUser(
            @PathVariable Long id,
            @RequestBody AddRoleRequest addRoleRequest
    ) {
        userService.addRoleToUser(id, addRoleRequest.role);
        return ResponseEntity.ok("Dodano role");
    }

    @PostMapping()
    public ResponseEntity addUsers(@RequestBody UserEntity userEntity) {
        UserEntity userEntityToAdd = userService.addUser(userEntity);
        return ResponseEntity.ok(userEntityToAdd);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UserEntity> updateUser(
            @PathVariable Long id,
            @RequestBody UserEntity userEntityToUpdate
    ) {
       UserEntity userEntity = userService.updateUser(id, userEntityToUpdate);
       return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity
                .accepted()
                .build();
    }
}
