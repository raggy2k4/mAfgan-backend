package com.example.mafgan.api.user;

import com.example.mafgan.domain.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    Long idUser;
    String login;
    String password;
    Set<UserRole> userRoles = new HashSet<>();
}
