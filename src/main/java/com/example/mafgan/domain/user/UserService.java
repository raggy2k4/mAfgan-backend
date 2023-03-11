package com.example.mafgan.domain.user;

import com.example.mafgan.external.user.UserEntity;
import com.example.mafgan.external.user.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserEntityRepository userEntityRepository;

    public List<UserEntity> findAllUser() {
        return userEntityRepository.findAll();
    }

    public UserEntity findUserById(Long id) {
        return userEntityRepository.findById(id).orElse(null);
    }

    public UserEntity addUser(UserEntity userEntity) {
        return userEntityRepository.save(userEntity);
    }

    public UserEntity updateUser(
            Long id,
            UserEntity userEntity
    ) {
        userEntity.setId(id);
        return userEntityRepository.save(userEntity);
    }

    public void deleteUser(Long id) {
        userEntityRepository.deleteById(id);
    }

    public void addRoleToUser(Long id, String role) {
        UserEntity user = userEntityRepository.findById(id).get();
        user.addRole(Role.valueOf(role));
    }
}
