package by.bsu.cookingportal.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import by.bsu.cookingportal.payload.request.SignupRequest;
import by.bsu.cookingportal.service.UserService;

/** @author Dmitry Kazakeivch */
@Component
public class SignupRequestValidator implements Validator {

  private final UserService userService;

  public SignupRequestValidator(UserService userService) {
    this.userService = userService;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return SignupRequest.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    SignupRequest signUpRequest = (SignupRequest) target;

    if (userService.existsByUsername(signUpRequest.getUsername())) {
      errors.rejectValue("username", "", "Username is already taken!");
    }

    if (userService.existsByEmail(signUpRequest.getEmail())) {
      errors.rejectValue("email", "", "Email is already in use!");
    }

    if (!signUpRequest.getPassword().equals(signUpRequest.getPasswordConfirmation())) {
      errors.rejectValue("password", "", "Passwords don't match!");
    }

    if (signUpRequest.getPassword().equals(signUpRequest.getUsername())) {
      errors.rejectValue("password", "", "Password must not match username!");
    }
  }
}
