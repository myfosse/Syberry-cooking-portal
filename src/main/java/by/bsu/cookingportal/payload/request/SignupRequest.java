package by.bsu.cookingportal.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/** @author Andrey Egorov */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest extends BaseSignRequest {

  @NotBlank
  @Size(min = 2, max = 100)
  @Pattern(
      regexp = "^[a-zA-Z ,.'-]+$",
      message =
          "Name not valid. Use only latin alphabet, hyphen, space, comma, period, with length from 2 to 100 characters")
  private String fullName;

  @NotBlank
  @Size(min = 2, max = 60)
  @Pattern(
      regexp = "^[a-zA-Z][a-zA-Z \\-,.]+$",
      message =
          "Username not valid. Use only latin alphabet, hyphen, space, comma, period, with length from 2 to 60 characters")
  private String username;

  @Size(max = 60)
  private String email;

  @Size(min = 8, max = 60)
  @Pattern(
      regexp = "((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$",
      message = "Password not secure. Please try to use dots and special symbols")
  private String password;

  @NotBlank
  @Size(min = 8, max = 60)
  @Pattern(
      regexp = "((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$",
      message = "Password not secure. Please try to use dots and special symbols")
  private String passwordConfirmation;

  public void setPassword(String password) {
    this.password = password;
    super.password = password;
  }

  public void setEmail(String email) {
    this.email = email;
    super.email = email;
  }
}
