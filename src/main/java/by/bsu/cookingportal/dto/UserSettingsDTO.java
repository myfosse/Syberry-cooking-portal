package by.bsu.cookingportal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSettingsDTO {
  private boolean followersCanSeeRecipes;
  private boolean followersCanSeeProfiles;
  private boolean newFollowerNotification;
  private boolean newMessageNotification;
}
