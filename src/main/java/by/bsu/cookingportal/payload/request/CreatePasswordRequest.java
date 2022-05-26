package by.bsu.cookingportal.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author Andrey Egorov */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePasswordRequest {

  @NotBlank private String code;

  @NotBlank
  @Size(max = 60)
  @Email(message = "Email not valid")
  private String email;

  @NotBlank
  @Size(min = 8, max = 60)
  @Pattern(
      regexp = "((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$",
      message = "Password not secure. Please try to use dots, special symbols and numbers")
  private String password;

  @NotBlank
  @Size(min = 8, max = 60)
  @Pattern(
      regexp = "((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$",
      message = "Password not secure. Please try to use dots, special symbols and numbers")
  private String passwordConfirmation;
}
