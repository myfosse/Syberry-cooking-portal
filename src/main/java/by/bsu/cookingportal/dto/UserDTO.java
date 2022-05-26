package by.bsu.cookingportal.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author Andrey Egorov */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  private Long id;
  private String fullName;
  private String username;
  private String email;
  private Boolean isBlock;
  private Set<String> roles = new HashSet<>();
}
