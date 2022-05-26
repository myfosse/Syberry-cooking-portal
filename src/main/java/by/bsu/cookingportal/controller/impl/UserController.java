package by.bsu.cookingportal.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.bsu.cookingportal.controller.CreateController;
import by.bsu.cookingportal.controller.DeleteController;
import by.bsu.cookingportal.controller.ReadController;
import by.bsu.cookingportal.controller.UpdateController;
import by.bsu.cookingportal.dto.UserDtoRequest;
import by.bsu.cookingportal.dto.UserDtoResponse;
import by.bsu.cookingportal.service.impl.UserServiceI;

@RestController
@RequestMapping("/api/v1/user")
public class UserController
    implements CreateController<UserDtoResponse, UserDtoRequest>,
        ReadController<UserDtoResponse>,
        UpdateController<UserDtoResponse, UserDtoRequest>,
        DeleteController<UserDtoResponse> {

  private final UserServiceI userService;

  @Autowired
  public UserController(UserServiceI userService) {
    this.userService = userService;
  }

  @GetMapping()
  @Override
  public ResponseEntity<List<UserDtoResponse>> getAllEntities() {
    return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
  }

  @PostMapping()
  @Override
  public ResponseEntity<UserDtoResponse> saveEntity(@RequestBody UserDtoRequest dto) {
    return new ResponseEntity<>(userService.save(dto), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  @Override
  public ResponseEntity<UserDtoResponse> getEntity(@PathVariable Long id) {
    UserDtoResponse foundUser = userService.getById(id);
    return new ResponseEntity<>(foundUser, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  @Override
  public ResponseEntity<UserDtoResponse> updateEntity(
      @PathVariable Long id, @RequestBody UserDtoRequest dto) {
    UserDtoResponse updatedUser = userService.update(id, dto);
    return new ResponseEntity<>(updatedUser, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  @Override
  public ResponseEntity<UserDtoResponse> deleteEntity(@PathVariable Long id) {
    userService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
