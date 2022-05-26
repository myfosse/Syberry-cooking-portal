package by.bsu.cookingportal.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author Andrey Egorov */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "file",
    uniqueConstraints = {@UniqueConstraint(columnNames = "externalId")})
public class AWSFile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String externalId;

  public AWSFile(String externalId) {
    this.externalId = externalId;
  }
}
