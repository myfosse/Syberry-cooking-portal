package by.bsu.cookingportal.service;

import java.util.Optional;

import by.bsu.cookingportal.entity.ERole;
import by.bsu.cookingportal.entity.Role;

public interface RoleService {

  boolean existsByName(final ERole name);

  Optional<Role> findByName(final ERole name);
}
