package by.bsu.cookingportal.converter;

import java.util.Set;
import java.util.stream.Collectors;

import by.bsu.cookingportal.dto.UserDtoRequest;
import by.bsu.cookingportal.dto.UserDtoResponse;
import by.bsu.cookingportal.entity.Role;
import by.bsu.cookingportal.entity.User;
import by.bsu.cookingportal.entity.UserSettings;

public class UserDtoConverter {
  public UserDtoResponse convertToUserDtoResponse(User user) {
    return UserDtoResponse.builder()
        .userName(user.getUsername())
        .fullName(user.getFullName())
        .email(user.getEmail())
        .roles(
            user.getRoles().stream()
                .map(role -> role.getName().toString())
                .collect(Collectors.toList()))
        .build();
  }

  public User convertToUser(UserDtoRequest userDto, Set<Role> roles) {
    return User.builder()
        .username(userDto.getUserName())
        .fullName(userDto.getFullName())
        .email(userDto.getEmail())
        .password(userDto.getPassword())
        .isBlock(false)
        .roles(roles)
        .userSettings(new UserSettings())
        .build();
  }
}
