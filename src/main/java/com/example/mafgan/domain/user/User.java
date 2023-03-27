package com.example.mafgan.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @ElementCollection
    private Set<UserRole> userRole = new HashSet<>();

    public void addRole(final UserRole userRole) {
        if (!this.userRole.contains(userRole)) {
            this.userRole.add(userRole);
        }
    }

    public void deleteRole(final UserRole userRole) {
        if (this.userRole.contains(userRole)) {
            this.userRole.remove(userRole);
        }
    }
}
