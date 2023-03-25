package com.example.mafgan.domain.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User save(@NonNull final User user) {
        user.setIdUser(null);
//        if (user.getIdUser() != null){ //TODO Piotrek niech ocenni co jest zgrabniejsze
//            throw new RuntimeException("User to save should not have user id");
//        } else {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(final Long id) {
        return userRepository.findById(id);
    }

    public boolean existsById(final Long id) {
        return userRepository.existsById(id);
    }

    public User update(final User user) {
        return userRepository.save(user);
//        return  userRepository.findById(user.getIdUser())
//                .map(u -> userRepository.save(user))
//                .get();
    }

    public Optional<Void> deleteById(final Long id) {
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
        }// else {
//            throw new RuntimeException("User not exist");
//        }
        return Optional.empty();
    }
}
