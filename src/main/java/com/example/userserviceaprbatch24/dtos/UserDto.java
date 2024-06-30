package com.example.userserviceaprbatch24.dtos;

import com.example.userserviceaprbatch24.models.Role;
import com.example.userserviceaprbatch24.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    private List<Role> roles;

    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setName( user.getName());
        userDto.setEmail( user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
