package by.bsu.cookingportal.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.bsu.cookingportal.controller.UpdateController;
import by.bsu.cookingportal.dto.UserSettingsDTO;
import by.bsu.cookingportal.service.impl.UserSettingsService;

@RestController
@RequestMapping("/api/v1/user/settings")
public class UserSettingsController implements UpdateController<UserSettingsDTO, UserSettingsDTO> {
  private final UserSettingsService userSettingsService;

  @Autowired
  public UserSettingsController(UserSettingsService userSettingsService) {
    this.userSettingsService = userSettingsService;
  }

  @PutMapping("/{id}")
  @Override
  public ResponseEntity<UserSettingsDTO> updateEntity(
      @PathVariable Long id, @RequestBody UserSettingsDTO dto) {
    UserSettingsDTO foundUserSettings = userSettingsService.update(id, dto);
    return new ResponseEntity<>(foundUserSettings, HttpStatus.OK);
  }
}
