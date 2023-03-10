package com.example.mafgan.external.repository;

import com.example.mafgan.external.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
