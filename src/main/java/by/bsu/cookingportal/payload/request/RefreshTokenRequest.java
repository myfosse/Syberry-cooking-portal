package by.bsu.cookingportal.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/** @author Dmitry Kazakeivch */
@Data
public class RefreshTokenRequest {
  @NotBlank private String refreshToken;
}
