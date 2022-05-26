package by.bsu.cookingportal.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRequest {
  private String fullName;
  private String userName;
  private String email;
  private String password;
  private List<String> roles;
}
