package by.bsu.cookingportal.exception;

import org.springframework.validation.BindingResult;

/** @author Dmitry Kazakevich */
public class ValidationException extends RuntimeException {

  private final BindingResult bindingResult;

  public ValidationException(BindingResult bindingResult) {
    this.bindingResult = bindingResult;
  }

  public BindingResult getBindingResult() {
    return this.bindingResult;
  }
}
