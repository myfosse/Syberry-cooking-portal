package by.bsu.cookingportal.security.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import by.bsu.cookingportal.exception.JwtAuthenticationException;
import by.bsu.cookingportal.service.impl.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

/** @author Andrey Egorov */
@Component
@Slf4j
public class JwtUtils {

  @Value("${app.jwtSecret}")
  private String jwtSecret;

  @Value("${app.jwtExpirationMs}")
  private int jwtExpirationMs;

  @Value("${app.jwtExpirationRememberMeMs}")
  private int jwtExpirationRememberMeMs;

  public String generateJwtToken(UserDetailsImpl userPrincipal) {

    return Jwts.builder()
        .setSubject((userPrincipal.getUsername()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String generateJwtRefreshToken(UserDetailsImpl userPrincipal, boolean rememberMe) {

    Claims claims = Jwts.claims().setSubject(userPrincipal.getUsername());
    claims.put("rememberMe", rememberMe);

    Date now = new Date();

    Date expiryDate =
        rememberMe
            ? new Date(now.getTime() + jwtExpirationRememberMeMs)
            : new Date(now.getTime() + jwtExpirationMs);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(new Date())
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken) {

    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException
        | IllegalArgumentException
        | UnsupportedJwtException
        | ExpiredJwtException
        | MalformedJwtException e) {
      throw new JwtAuthenticationException(e.getMessage());
    }
  }

  public Date getExpiration(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
  }

  public boolean getRememberMe(String token) {
    return (Boolean)
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("rememberMe");
  }
}
