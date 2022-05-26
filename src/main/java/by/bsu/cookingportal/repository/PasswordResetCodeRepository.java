package by.bsu.cookingportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.bsu.cookingportal.entity.PasswordResetCode;

/** @author Andrey Egorov */
@Repository
public interface PasswordResetCodeRepository extends JpaRepository<PasswordResetCode, Long> {

  boolean existsByUserEmail(final String email);

  Optional<PasswordResetCode> findByUserEmail(final String email);

  void deleteByResetCode(final String resetCode);
}
