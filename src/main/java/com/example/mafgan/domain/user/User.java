package com.example.mafgan.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private String login;
    private String password;
    private Set<Role> roles = new HashSet<>();

    public void addRole(final Role role) {
        if (!this.roles.contains(role)) {
            this.roles.add(role);
        }
    }

    public void removeRole(final Role role) {
        if (this.roles.contains(role)) {
            this.roles.remove(role);
        }
    }
}
