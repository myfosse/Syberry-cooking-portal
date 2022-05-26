package by.bsu.cookingportal.exception;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/** @author Dmitry Kazakevich */
@EnableWebMvc
@RestControllerAdvice
public class ExceptionsHandlingManager extends ResponseEntityExceptionHandler {
  @ExceptionHandler(ValidationException.class)
  public final ResponseEntity<Object> handleUserValidationException(
      ValidationException ex, WebRequest request) {

    Map<String, Object> body = new HashMap<>();

    List<Map<String, String>> errors = new LinkedList<>();

    for (FieldError el : ex.getBindingResult().getFieldErrors()) {
      Map<String, String> error = new LinkedHashMap<>();
      error.put(el.getField(), el.getDefaultMessage());
      errors.add(error);
    }

    body.put("errors", errors);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(JwtAuthenticationException.class)
  public final ResponseEntity<Object> handleJwtAuthException(
      JwtAuthenticationException ex, WebRequest request) {

    Map<String, Object> body = new HashMap<>();

    body.put("error", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public final ResponseEntity<Object> handleBadCredentialsException(
      BadCredentialsException ex, WebRequest request) {

    Map<String, Object> body = new HashMap<>();

    body.put("error", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public final ResponseEntity<Object> handleEntityNotFoundException(
      EntityNotFoundException ex, WebRequest request) {

    Map<String, Object> body = new HashMap<>();

    body.put("error", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ImageUploadException.class)
  public final ResponseEntity<Object> handleImageUploadException(
      ImageUploadException ex, WebRequest request) {

    Map<String, Object> body = new HashMap<>();

    body.put("error", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.CONFLICT);
  }
}
