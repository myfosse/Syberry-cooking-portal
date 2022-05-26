package by.bsu.cookingportal.service;

import java.util.Map;

import by.bsu.cookingportal.payload.request.BaseSignRequest;
import by.bsu.cookingportal.payload.request.RefreshTokenRequest;
import by.bsu.cookingportal.payload.request.SignupRequest;

/** @author Dmitry Kazakeivch */
public interface AuthService {

  void register(SignupRequest signUpRequest);

  Map<String, Object> authenticate(BaseSignRequest baseSignRequest, boolean rememberMe);

  Map<String, Object> generateNewTokensPair(RefreshTokenRequest refreshTokenRequest);
}
