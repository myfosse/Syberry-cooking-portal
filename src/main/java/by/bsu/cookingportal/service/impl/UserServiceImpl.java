package by.bsu.cookingportal.service.impl;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import by.bsu.cookingportal.entity.User;
import by.bsu.cookingportal.repository.UserRepository;
import by.bsu.cookingportal.service.UserService;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User save(final User user) {
    return userRepository.save(user);
  }

  @Override
  public User findByUsername(final String username) {
    return userRepository
        .findByUsername(username)
        .orElseThrow(
            () -> new EntityNotFoundException("Error: user with such username does not exist!"));
  }

  @Override
  public User findByEmail(final String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(
            () -> new EntityNotFoundException("Error: user with such email does not exist!"));
  }

  @Override
  public boolean existsByUsername(final String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  public boolean existsByEmail(final String email) {
    return userRepository.existsByEmail(email);
  }
}
