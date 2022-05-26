package by.bsu.cookingportal.converter;

import by.bsu.cookingportal.dto.UserSettingsDTO;
import by.bsu.cookingportal.entity.UserSettings;

public class UserSettingsDTOConverter {

  public UserSettingsDTO convertToUserSettingsDTO(UserSettings userSettings) {
    return UserSettingsDTO.builder()
        .followersCanSeeProfiles(userSettings.isFollowersCanSeeProfiles())
        .followersCanSeeRecipes(userSettings.isFollowersCanSeeRecipes())
        .newFollowerNotification(userSettings.isNewFollowerNotification())
        .newMessageNotification(userSettings.isNewMessageNotification())
        .build();
  }
}
