package com.example.mafgan.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(
            Long id,
            User toUpdate
    ) {
        toUpdate.setIdUser(id);
        return userRepository.save(toUpdate);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void addRoleToUser(Long id, String role) {
        User user = userRepository.findById(id).get();
        user.addRole(UserRole.valueOf(role));
    }
}
