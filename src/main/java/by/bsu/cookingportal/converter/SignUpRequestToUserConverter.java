package by.bsu.cookingportal.converter;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;

import by.bsu.cookingportal.entity.Role;
import by.bsu.cookingportal.entity.User;
import by.bsu.cookingportal.entity.UserSettings;
import by.bsu.cookingportal.payload.request.SignupRequest;

/** @author Andrey Egorov */
public class SignUpRequestToUserConverter {

  public static User convertSignUpRequestToUser(
      final SignupRequest signUpRequest, final Role role, final PasswordEncoder encoder) {
    return User.builder()
        .fullName(signUpRequest.getFullName())
        .username(signUpRequest.getUsername())
        .email(signUpRequest.getEmail())
        .password(encoder.encode(signUpRequest.getPassword()))
        .isBlock(false)
        .roles(Set.of(role))
        .userSettings(new UserSettings())
        .build();
  }
}
