package com.example.mafgan.domain.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service // TODO ZROB METODY W KOLEJNOSCI CRUD
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(final Long id) {
        return userRepository.findById(id);
    }

    public User add(@NonNull final User user) { // TODO SAVE
        user.setIdUser(null);
//        if (user.getIdUser() != null){ //TODO Piotrek niech ocenni co jest zgrabniejsze
//            throw new RuntimeException("User to save should not have user id");
//        } else {
        return userRepository.save(user); }

    public User update(final User user) { //TODO SPRAWDZ CZY JEST USER najlepiej bez ifa
        return userRepository.save(user);
    }

    public void deleteById(final Long id) {
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not exist");
        }
    }

    public boolean existsById(final Long id) {
        return userRepository.existsById(id);
    }

}
