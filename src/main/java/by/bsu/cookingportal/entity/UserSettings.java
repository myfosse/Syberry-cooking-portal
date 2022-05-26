package by.bsu.cookingportal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "usersettings")
public class UserSettings {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private boolean followersCanSeeRecipes;

  @Column(nullable = false)
  private boolean followersCanSeeProfiles;

  @Column(nullable = false)
  private boolean newFollowerNotification;

  @Column(nullable = false)
  private boolean newMessageNotification;
}
