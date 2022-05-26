package by.bsu.cookingportal.service;

import java.util.Optional;

import by.bsu.cookingportal.entity.PasswordResetCode;
import by.bsu.cookingportal.payload.request.CreatePasswordRequest;

/** @author Andrey Egorov */
public interface PasswordResetCodeService {

  PasswordResetCode save(final PasswordResetCode passwordResetCode);

  boolean existsByUserEmail(final String email);

  Optional<PasswordResetCode> findByUserEmail(final String email);

  void deleteByResetCode(final String resetCode);

  void createNewPassword(CreatePasswordRequest createPasswordRequest);
}
