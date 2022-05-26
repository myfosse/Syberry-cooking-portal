package by.bsu.cookingportal.exception;

import org.springframework.security.core.AuthenticationException;

/** @author Dmitry Kazakevich */
public class JwtAuthenticationException extends AuthenticationException {
  public JwtAuthenticationException(String msg, Throwable t) {
    super(msg, t);
  }

  public JwtAuthenticationException(String msg) {
    super(msg);
  }
}
