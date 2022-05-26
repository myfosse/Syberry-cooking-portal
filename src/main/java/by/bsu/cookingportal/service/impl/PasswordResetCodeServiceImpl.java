package by.bsu.cookingportal.service.impl;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import by.bsu.cookingportal.entity.PasswordResetCode;
import by.bsu.cookingportal.entity.User;
import by.bsu.cookingportal.payload.request.CreatePasswordRequest;
import by.bsu.cookingportal.repository.PasswordResetCodeRepository;
import by.bsu.cookingportal.repository.UserRepository;
import by.bsu.cookingportal.service.PasswordResetCodeService;

/** @author Andrey Egorov */
@Service
public class PasswordResetCodeServiceImpl implements PasswordResetCodeService {

  private final PasswordResetCodeRepository passwordResetCodeRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder encoder;

  @Autowired
  public PasswordResetCodeServiceImpl(
      final PasswordResetCodeRepository passwordResetCodeRepository,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder) {
    this.passwordResetCodeRepository = passwordResetCodeRepository;
    this.userRepository = userRepository;
    this.encoder = passwordEncoder;
  }

  @Override
  public PasswordResetCode save(final PasswordResetCode passwordResetCode) {
    return passwordResetCodeRepository.save(passwordResetCode);
  }

  @Override
  public boolean existsByUserEmail(final String email) {
    return passwordResetCodeRepository.existsByUserEmail(email);
  }

  @Override
  public Optional<PasswordResetCode> findByUserEmail(final String email) {
    return passwordResetCodeRepository.findByUserEmail(email);
  }

  @Override
  @Transactional
  public void deleteByResetCode(final String resetCode) {
    passwordResetCodeRepository.deleteByResetCode(resetCode);
  }

  @Override
  @Transactional
  public void createNewPassword(CreatePasswordRequest createPasswordRequest) {
    User user =
        userRepository
            .findByEmail(createPasswordRequest.getEmail())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "User with email " + createPasswordRequest.getEmail() + " was not found"));

    user.setPassword(encoder.encode(createPasswordRequest.getPassword()));
    userRepository.save(user);
    deleteByResetCode(createPasswordRequest.getCode());
  }
}
