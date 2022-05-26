package by.bsu.cookingportal.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.bsu.cookingportal.converter.UserDtoConverter;
import by.bsu.cookingportal.dto.UserDtoRequest;
import by.bsu.cookingportal.dto.UserDtoResponse;
import by.bsu.cookingportal.entity.ERole;
import by.bsu.cookingportal.entity.Role;
import by.bsu.cookingportal.entity.User;
import by.bsu.cookingportal.repository.RoleRepository;
import by.bsu.cookingportal.repository.UserRepository;
import by.bsu.cookingportal.service.CreateService;
import by.bsu.cookingportal.service.DeleteService;
import by.bsu.cookingportal.service.ReadService;
import by.bsu.cookingportal.service.UpdateService;

@Service
public class UserServiceI
    implements CreateService<UserDtoResponse, UserDtoRequest>,
        ReadService<UserDtoResponse>,
        UpdateService<UserDtoResponse, UserDtoRequest>,
        DeleteService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final UserDtoConverter userDtoConverter = new UserDtoConverter();

  @Autowired
  public UserServiceI(UserRepository userRepository, RoleRepository roleRepository) {

    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  @Override
  public UserDtoResponse save(UserDtoRequest dto) {
    Set<Role> roles =
        dto.getRoles().stream()
            .map(
                role ->
                    roleRepository
                        .findByName(ERole.valueOf(role))
                        .orElseThrow(
                            () -> new EntityNotFoundException("Role " + role + " was not found")))
            .collect(Collectors.toSet());

    User user = userDtoConverter.convertToUser(dto, roles);
    return userDtoConverter.convertToUserDtoResponse(userRepository.save(user));
  }

  @Override
  public List<UserDtoResponse> getAll() {
    return userRepository.findAll().stream()
        .map(userDtoConverter::convertToUserDtoResponse)
        .collect(Collectors.toList());
  }

  @Override
  public UserDtoResponse getById(Long id) {
    Optional<User> foundUser = userRepository.findById(id);
    if (foundUser.isPresent()) {
      return userDtoConverter.convertToUserDtoResponse(foundUser.get());
    }
    throw new EntityNotFoundException("User with id" + id + "was not found");
  }

  @Override
  public UserDtoResponse update(Long id, UserDtoRequest dto) {
    Set<Role> roles =
        dto.getRoles().stream()
            .map(
                role ->
                    roleRepository
                        .findByName(ERole.valueOf(role))
                        .orElseThrow(
                            () -> new EntityNotFoundException("Role " + role + " was not found")))
            .collect(Collectors.toSet());

    User foundUser =
        userRepository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " was not found"));

    foundUser.setFullName(dto.getFullName());
    foundUser.setUsername(dto.getUserName());
    foundUser.setEmail(dto.getEmail());
    foundUser.setPassword(dto.getPassword());
    foundUser.setRoles(roles);

    return userDtoConverter.convertToUserDtoResponse(userRepository.save(foundUser));
  }

  @Override
  public void delete(Long id) {
    Optional<User> foundUser = userRepository.findById(id);
    if (foundUser.isPresent()) {
      userRepository.deleteById(id);
    } else {
      throw new EntityNotFoundException("User with id " + id + " was not found");
    }
  }
}
