package by.bsu.cookingportal.service;

import by.bsu.cookingportal.entity.User;

public interface UserService {

  User save(final User user);

  User findByUsername(final String username);

  User findByEmail(final String email);

  boolean existsByUsername(final String username);

  boolean existsByEmail(final String email);
}
