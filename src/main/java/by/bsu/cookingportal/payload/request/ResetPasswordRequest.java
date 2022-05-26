package by.bsu.cookingportal.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author Andrey Egorov */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {

  @NotBlank
  @Size(max = 60)
  @Email(message = "Email not valid")
  private String email;
}
