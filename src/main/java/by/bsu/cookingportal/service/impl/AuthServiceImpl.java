package by.bsu.cookingportal.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import by.bsu.cookingportal.converter.SignUpRequestToUserConverter;
import by.bsu.cookingportal.entity.ERole;
import by.bsu.cookingportal.entity.Role;
import by.bsu.cookingportal.exception.JwtAuthenticationException;
import by.bsu.cookingportal.payload.request.BaseSignRequest;
import by.bsu.cookingportal.payload.request.RefreshTokenRequest;
import by.bsu.cookingportal.payload.request.SignupRequest;
import by.bsu.cookingportal.repository.RoleRepository;
import by.bsu.cookingportal.repository.UserRepository;
import by.bsu.cookingportal.security.jwt.JwtUtils;
import by.bsu.cookingportal.service.AuthService;

/** @author Dmitry Kazakeivch */
@Service
public class AuthServiceImpl implements AuthService {

  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder encoder;
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  public AuthServiceImpl(
      RoleRepository roleRepository,
      UserRepository userRepository,
      PasswordEncoder encoder,
      AuthenticationManager authenticationManager,
      JwtUtils jwtUtils) {
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
    this.encoder = encoder;
    this.authenticationManager = authenticationManager;
    this.jwtUtils = jwtUtils;
  }

  @Override
  public void register(SignupRequest signUpRequest) {
    Role userRole =
        roleRepository
            .findByName(ERole.ROLE_USER)
            .orElseThrow(() -> new EntityNotFoundException("Error: Role is not found."));

    userRepository.save(
        SignUpRequestToUserConverter.convertSignUpRequestToUser(signUpRequest, userRole, encoder));
  }

  @Override
  public Map<String, Object> authenticate(BaseSignRequest baseSignRequest, boolean rememberMe) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                baseSignRequest.getEmail(), baseSignRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    String accessToken = jwtUtils.generateJwtToken(userPrincipal);
    String refreshToken = jwtUtils.generateJwtRefreshToken(userPrincipal, rememberMe);

    Map<String, Object> response =
        createResponse(
            accessToken,
            jwtUtils.getExpiration(accessToken).getTime(),
            refreshToken,
            jwtUtils.getExpiration(refreshToken).getTime());
    response.put("user", userPrincipal);

    return response;
  }

  @Override
  public Map<String, Object> generateNewTokensPair(RefreshTokenRequest refreshTokenRequest) {
    String oldRefreshToken = refreshTokenRequest.getRefreshToken();

    if (jwtUtils.validateJwtToken(oldRefreshToken)) {
      UserDetailsImpl userPrincipal =
          (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      String newAccessToken = jwtUtils.generateJwtToken(userPrincipal);
      boolean isRememberUser = jwtUtils.getRememberMe(oldRefreshToken);
      String newRefreshToken = jwtUtils.generateJwtRefreshToken(userPrincipal, isRememberUser);

      return createResponse(
          newAccessToken,
          jwtUtils.getExpiration(newAccessToken).getTime(),
          newRefreshToken,
          jwtUtils.getExpiration(newRefreshToken).getTime());
    } else {
      throw new JwtAuthenticationException("JWT token is expired or invalid");
    }
  }

  private Map<String, Object> createResponse(
      String accessToken, long accessTokenExp, String refreshToken, long refreshTokenExp) {

    Map<String, Object> result = new HashMap<>();
    result.put("token", accessToken);
    result.put("tokenExpiration", accessTokenExp);
    result.put("refreshToken", refreshToken);
    result.put("refreshTokenExpiration", refreshTokenExp);

    return result;
  }
}
