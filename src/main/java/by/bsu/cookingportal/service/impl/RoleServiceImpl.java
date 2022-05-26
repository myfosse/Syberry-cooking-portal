package by.bsu.cookingportal.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.bsu.cookingportal.entity.ERole;
import by.bsu.cookingportal.entity.Role;
import by.bsu.cookingportal.repository.RoleRepository;
import by.bsu.cookingportal.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;

  @Autowired
  public RoleServiceImpl(final RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public boolean existsByName(final ERole name) {
    return roleRepository.existsByName(name);
  }

  @Override
  public Optional<Role> findByName(final ERole name) {
    return roleRepository.findByName(name);
  }
}
