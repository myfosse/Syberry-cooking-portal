package by.bsu.cookingportal.service.impl;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.bsu.cookingportal.converter.UserSettingsDTOConverter;
import by.bsu.cookingportal.dto.UserSettingsDTO;
import by.bsu.cookingportal.entity.User;
import by.bsu.cookingportal.entity.UserSettings;
import by.bsu.cookingportal.repository.UserRepository;
import by.bsu.cookingportal.repository.UserSettingsRepository;
import by.bsu.cookingportal.service.UpdateService;

@Service
public class UserSettingsService implements UpdateService<UserSettingsDTO, UserSettingsDTO> {

  private final UserSettingsRepository userSettingsRepository;
  private final UserRepository userRepository;
  private final UserSettingsDTOConverter userSettingsDTOConverter = new UserSettingsDTOConverter();

  @Autowired
  public UserSettingsService(
      UserSettingsRepository userSettingsRepository, UserRepository userRepository) {
    this.userSettingsRepository = userSettingsRepository;
    this.userRepository = userRepository;
  }

  @Override
  public UserSettingsDTO update(Long id, UserSettingsDTO dto) {
    Optional<User> foundUser = userRepository.findById(id);
    if (foundUser.isPresent()) {
      UserSettings userSettings = foundUser.get().getUserSettings();
      userSettings.setFollowersCanSeeProfiles(dto.isFollowersCanSeeProfiles());
      userSettings.setFollowersCanSeeRecipes(dto.isFollowersCanSeeRecipes());
      userSettings.setNewFollowerNotification(dto.isNewFollowerNotification());
      userSettings.setNewMessageNotification(dto.isNewMessageNotification());
      return userSettingsDTOConverter.convertToUserSettingsDTO(
          userSettingsRepository.save(userSettings));
    }
    throw new EntityNotFoundException("Entity not found");
  }
}
