package com.example.mafgan.api.user;

import com.example.mafgan.domain.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "loginDto", source = "login")
    @Mapping(target = "passwordDto", source = "password")
    @Mapping(target = "userRolesDto", source = "userRoles")
    UserDto toDto(User user);
    User toDomain(UserDto userDto);
}
