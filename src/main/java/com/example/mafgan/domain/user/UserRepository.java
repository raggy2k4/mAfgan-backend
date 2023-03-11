package com.example.mafgan.domain.user;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    @EntityGraph(attributePaths = {"userRoles"})
    List<User> findAll();

//    @Query("select u from User u where u.idUser = ?1") //JPQL
//    Optional<User> findById(Long idUser);

//    @Modifying
//    @Transactional
//    @Query(nativeQuery = true, value = "insert into User(id_user, login, password, user_roles) values(?, ?, ?, ?)")
//    @Query(nativeQuery = true, value = "INSERT INTO User u (id_user, login, password, user_roles) VALUES (:id_user, :login, :password, :user_roles)")
//    User save(User user);

//    @Override
//    @Query(nativeQuery = true, value = "insert into User(id_user, login, password, user_roles) values(id_user, login, password, user_roles) ")
//    User save(@Param("id_user") Long id, @Param("login") String login, @Param("password") String password, @Param("user_roles") Set<UserRole> userRoles);
}
