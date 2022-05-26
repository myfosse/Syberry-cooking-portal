package by.bsu.cookingportal.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDtoResponse {

  private String fullName;

  private String userName;

  private String email;

  private List<String> roles;
}
