package by.bsu.cookingportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.bsu.cookingportal.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(final String username);

  Optional<User> findByEmail(final String email);

  boolean existsByUsername(final String username);

  boolean existsByEmail(final String email);
}
