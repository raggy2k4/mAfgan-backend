package com.example.mafgan.domain.user;

import com.example.mafgan.error.UserNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @SneakyThrows
    public User save(@NonNull final User user) {
        if (user.getId() == null) {
//            throw new RuntimeException("User to save should not exist in DB");//TODO wlasny exception
            throw new UserNotFoundException("User to save should not exist in DB");//TODO wlasny exception
        }
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

    public User update(@NonNull final User user) {

        return userRepository.findById(user.getId())
                .map(u -> userRepository.save(user))
                .orElse(null);
    }

    public Optional<Void> deleteById(final Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
        return Optional.empty();
    }
}
