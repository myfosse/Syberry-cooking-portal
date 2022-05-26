package by.bsu.cookingportal.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

/** @author Andrey Egorov */
@Entity
@Data
@NoArgsConstructor
public class PasswordResetCode {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String resetCode;

  @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "user_id")
  private User user;

  @Column(nullable = false)
  private LocalDate expirationCodeDate;

  public PasswordResetCode(final User user, String resetCode) {
    this.user = user;
    expirationCodeDate = LocalDate.now().plusDays(1);
    this.resetCode = resetCode;
  }
}
