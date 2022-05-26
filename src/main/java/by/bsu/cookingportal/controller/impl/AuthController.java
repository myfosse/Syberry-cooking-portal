package by.bsu.cookingportal.controller.impl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import by.bsu.cookingportal.exception.ValidationException;
import by.bsu.cookingportal.payload.request.RefreshTokenRequest;
import by.bsu.cookingportal.payload.request.SigninRequest;
import by.bsu.cookingportal.payload.request.SignupRequest;
import by.bsu.cookingportal.service.AuthService;
import by.bsu.cookingportal.validation.SignupRequestValidator;

/**
 * @author Andrey Egorov
 * @author Dmitry Kazakevich
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;
  private final SignupRequestValidator signupRequestValidator;

  @Autowired
  public AuthController(AuthService authService, SignupRequestValidator signupRequestValidator) {
    this.authService = authService;
    this.signupRequestValidator = signupRequestValidator;
  }

  @PostMapping("/sign-in")
  public @ResponseBody ResponseEntity<?> authenticateUser(
      @Valid @RequestBody final SigninRequest loginRequest, BindingResult errors) {

    if (errors.hasErrors()) {
      throw new ValidationException(errors);
    }

    return ResponseEntity.ok(authService.authenticate(loginRequest, loginRequest.getRememberMe()));
  }

  @PostMapping("/sign-up")
  public @ResponseBody ResponseEntity<?> registerUser(
      @Valid @RequestBody final SignupRequest signUpRequest, BindingResult errors) {

    signupRequestValidator.validate(signUpRequest, errors);

    if (errors.hasErrors()) {
      throw new ValidationException(errors);
    }

    authService.register(signUpRequest);

    return new ResponseEntity<>(authService.authenticate(signUpRequest, false), HttpStatus.CREATED);
  }

  @PostMapping("/refresh")
  public ResponseEntity<?> refreshTokens(@RequestBody RefreshTokenRequest refreshTokenRequest) {
    return ResponseEntity.ok(authService.generateNewTokensPair(refreshTokenRequest));
  }
}
