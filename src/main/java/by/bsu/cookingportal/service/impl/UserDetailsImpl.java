package by.bsu.cookingportal.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import by.bsu.cookingportal.entity.User;

import lombok.Getter;

/** @author Andrey Egorov */
@Getter
public class UserDetailsImpl implements UserDetails {

  private static final long serialVersionUID = 1L;

  private final Long id;
  private final String fullName;
  private final String username;
  private final String email;
  private final Boolean isBlock;
  @JsonIgnore private final String password;

  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(
      Long id,
      String fullName,
      String username,
      String email,
      String password,
      Boolean isBlock,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.fullName = fullName;
    this.username = username;
    this.email = email;
    this.password = password;
    this.isBlock = isBlock;
    this.authorities = authorities;
  }

  public static UserDetailsImpl build(User user) {
    List<GrantedAuthority> authorities =
        user.getRoles().stream()
            .flatMap(
                role ->
                    role.getPermissions().stream()
                        .map(permission -> new SimpleGrantedAuthority(permission.getName().name())))
            .collect(Collectors.toList());

    return new UserDetailsImpl(
        user.getId(),
        user.getFullName(),
        user.getUsername(),
        user.getEmail(),
        user.getPassword(),
        user.getIsBlock(),
        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonLocked() {
    return !isBlock;
  }

  @Override
  @JsonIgnore
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
