package by.bsu.cookingportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.bsu.cookingportal.entity.ERole;
import by.bsu.cookingportal.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  boolean existsByName(final ERole name);

  Optional<Role> findByName(final ERole name);
}
