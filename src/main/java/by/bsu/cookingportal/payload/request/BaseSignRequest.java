package by.bsu.cookingportal.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/** @author Dmitry Kazakeivch */
@Data
public class BaseSignRequest {
  @NotBlank
  @Email(message = "Email not valid")
  protected String email;

  @NotBlank protected String password;
}
