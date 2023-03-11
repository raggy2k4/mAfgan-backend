package com.example.mafgan.api.user;

import com.example.mafgan.domain.user.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toDomain(UserDto userDto);
    List<UserDto> toListDto(List<User> users);
}
