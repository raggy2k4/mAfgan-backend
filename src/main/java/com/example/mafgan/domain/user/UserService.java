package com.example.mafgan.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(final Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User add(final User user) {
        return userRepository.save(user);
    }

    public User update(
            User toUpdate
    ) {
        return userRepository.save(toUpdate);
    }

    public void deleteById(final Long id) {
        userRepository.deleteById(id);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public boolean existsById(final Long id) {
        return userRepository.existsById(id);
    }

    public void addRoleToUser(Long id, String role) {
        User user = userRepository.findById(id).get();
        user.addRole(UserRole.valueOf(role));
    }

    public void deleteRoleInUser(Long id, String role) {
        User user = userRepository.findById(id).get();
        user.deleteRole(UserRole.valueOf(role));
    }
}
