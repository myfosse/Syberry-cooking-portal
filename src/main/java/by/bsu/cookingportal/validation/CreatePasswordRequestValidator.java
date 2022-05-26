package by.bsu.cookingportal.validation;

import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import by.bsu.cookingportal.entity.PasswordResetCode;
import by.bsu.cookingportal.payload.request.CreatePasswordRequest;
import by.bsu.cookingportal.service.PasswordResetCodeService;
import by.bsu.cookingportal.service.UserService;

@Component
public class CreatePasswordRequestValidator implements Validator {

  private final PasswordResetCodeService passwordResetCodeService;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  public CreatePasswordRequestValidator(
      PasswordResetCodeService passwordResetCodeService,
      UserService userService,
      PasswordEncoder passwordEncoder) {
    this.passwordResetCodeService = passwordResetCodeService;
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return CreatePasswordRequest.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    CreatePasswordRequest createPasswordRequest = (CreatePasswordRequest) o;
    PasswordResetCode passwordResetCode =
        passwordResetCodeService
            .findByUserEmail(createPasswordRequest.getEmail())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Password reset code for user with email "
                            + createPasswordRequest.getEmail()
                            + " was not found"));

    if (!passwordResetCode.getResetCode().equals(createPasswordRequest.getCode())) {
      errors.rejectValue("code", "", "Password reset codes don't match");
    }

    if (passwordResetCode.getExpirationCodeDate().isBefore(LocalDate.now())) {
      errors.rejectValue("code", "", "Code is expired! Try to get new "
              + "code");
    }

    if (!createPasswordRequest
        .getPassword()
        .equals(createPasswordRequest.getPasswordConfirmation())) {
      errors.rejectValue("password", "", "Password and password "
              + "confirmation don't match");
    } else if (passwordEncoder.matches(
        createPasswordRequest.getPassword(),
        userService.findByEmail(createPasswordRequest.getEmail()).getPassword())) {
      errors.rejectValue(
          "password", "", "Your new password must be different from "
                      + "your previous password.");
    }
  }
}
