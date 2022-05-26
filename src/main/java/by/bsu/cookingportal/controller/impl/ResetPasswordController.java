package by.bsu.cookingportal.controller.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import by.bsu.cookingportal.entity.PasswordResetCode;
import by.bsu.cookingportal.exception.ValidationException;
import by.bsu.cookingportal.payload.request.CreatePasswordRequest;
import by.bsu.cookingportal.payload.request.ResetPasswordRequest;
import by.bsu.cookingportal.payload.response.MessageResponse;
import by.bsu.cookingportal.service.EmailService;
import by.bsu.cookingportal.service.PasswordResetCodeService;
import by.bsu.cookingportal.service.UserService;
import by.bsu.cookingportal.validation.CreatePasswordRequestValidator;

import lombok.SneakyThrows;

/** @author Andrey Egorov */
@RestController
@RequestMapping("/api/v1/password")
public class ResetPasswordController {

  private final PasswordResetCodeService passwordResetCodeService;
  private final UserService userService;
  private final EmailService emailService;
  private final CreatePasswordRequestValidator createPasswordRequestValidator;
  private static final String TEMPLATE_NAME = "password-reset-mail";

  @Value("${app.createPasswordURL}")
  private String passwordCreateURL;

  @Autowired
  public ResetPasswordController(
      PasswordResetCodeService passwordResetCodeService,
      UserService userService,
      EmailService emailService,
      CreatePasswordRequestValidator createPasswordRequestValidator) {
    this.passwordResetCodeService = passwordResetCodeService;
    this.userService = userService;
    this.createPasswordRequestValidator = createPasswordRequestValidator;
    this.emailService = emailService;
  }

  @SneakyThrows
  @PutMapping("/reset")
  public @ResponseBody MessageResponse resetPassword(
      @Valid @RequestBody final ResetPasswordRequest resetPasswordRequest, BindingResult errors) {

    if (errors.hasErrors()) {
      throw new ValidationException(errors);
    }

    PasswordResetCode passwordResetCode =
        new PasswordResetCode(
            userService.findByEmail(resetPasswordRequest.getEmail()), UUID.randomUUID().toString());

    Map<String, Object> templateModel = new HashMap<>();
    templateModel.put("email", resetPasswordRequest.getEmail());
    templateModel.put("href", passwordCreateURL + passwordResetCode.getResetCode());
    emailService.sendMessageUsingThymeleafTemplate(
        resetPasswordRequest.getEmail(),
        "Cooking portal reset password",
        templateModel,
        TEMPLATE_NAME);

    passwordResetCodeService.save(passwordResetCode);

    return new MessageResponse(
        "Code for reset password sent to your email: " + resetPasswordRequest.getEmail());
  }

  @PutMapping("/create")
  public @ResponseBody MessageResponse createPassword(
      @Valid @RequestBody final CreatePasswordRequest createPasswordRequest, BindingResult errors) {

    createPasswordRequestValidator.validate(createPasswordRequest, errors);

    if (errors.hasErrors()) {
      throw new ValidationException(errors);
    }

    passwordResetCodeService.createNewPassword(createPasswordRequest);

    return new MessageResponse(
        "Successfully created a new password for the user with email: "
            + createPasswordRequest.getEmail());
  }
}
