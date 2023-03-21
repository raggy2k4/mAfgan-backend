package com.example.mafgan.api.user;

import com.example.mafgan.domain.user.UserRole;
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
public class UserDto {
    private Long idUser;
    private String loginDto;
    private String passwordDto;
    private Set<UserRole> userRolesDto = new HashSet<>();
}
