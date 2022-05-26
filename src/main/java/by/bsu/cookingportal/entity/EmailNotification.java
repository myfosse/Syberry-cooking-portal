package by.bsu.cookingportal.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "email_notification")
public class EmailNotification {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date sent_at;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  @Builder.Default
  private EEmailStatus status = EEmailStatus.WAIT_FOR_SENDING;

  @Builder.Default private Integer numberOfAttempts = 0;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "notification_id", referencedColumnName = "id")
  private Notification notification;
}
