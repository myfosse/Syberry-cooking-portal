package by.bsu.cookingportal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import by.bsu.cookingportal.entity.EEmailStatus;
import by.bsu.cookingportal.entity.ENotification;
import by.bsu.cookingportal.entity.EmailNotification;
import by.bsu.cookingportal.entity.Notification;
import by.bsu.cookingportal.entity.User;
import by.bsu.cookingportal.repository.EmailNotificationRepository;
import by.bsu.cookingportal.repository.NotificationRepository;
import by.bsu.cookingportal.repository.UserRepository;
import by.bsu.cookingportal.service.EmailService;
import by.bsu.cookingportal.service.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Service
@EnableScheduling
@Slf4j
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;
  private final EmailNotificationRepository emailNotificationRepository;
  private final UserRepository userRepository;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final EmailService emailService;
  private static final String TEMPLATE_NAME = "notification-email";

  @Autowired
  public NotificationServiceImpl(
      NotificationRepository notificationRepository,
      EmailNotificationRepository emailNotificationRepository,
      UserRepository userRepository,
      ApplicationEventPublisher applicationEventPublisher,
      EmailService emailService) {
    this.notificationRepository = notificationRepository;
    this.emailNotificationRepository = emailNotificationRepository;
    this.userRepository = userRepository;
    this.applicationEventPublisher = applicationEventPublisher;
    this.emailService = emailService;
  }

  @Override
  public Notification createNotification(
      String notificationType, String email, String title, String text, boolean isViewed) {
    User user =
        userRepository
            .findByEmail(email)
            .orElseThrow(
                () -> new EntityNotFoundException("User with email " + email + " was not found"));

    Notification notification =
        Notification.builder()
            .type(ENotification.valueOf(notificationType))
            .title(title)
            .text(text)
            .user(user)
            .isViewed(isViewed)
            .build();

    notificationRepository.save(notification);
    return notification;
  }

  @Override
  @Scheduled(fixedRate = 10000)
  public void sendAllNotifications() {
    List<Optional<EmailNotification>> emailNotifications =
        emailNotificationRepository.findAllByStatus(EEmailStatus.WAIT_FOR_SENDING);

    for (Optional<EmailNotification> emailNotificationOptional : emailNotifications) {
      EmailNotification emailNotification =
          emailNotificationOptional.orElseThrow(
              () -> new EntityNotFoundException("Email notification was not found"));
      sendEmailNotification(emailNotification);
      emailNotificationRepository.save(emailNotification);
    }
  }

  @Override
  public void sendNotification(Notification notification, String email) {
    EmailNotification emailNotification =
        EmailNotification.builder().notification(notification).build();

    applicationEventPublisher.publishEvent(emailNotification);
  }

  public void saveAndSendEmailNotification(EmailNotification emailNotification) {
    sendEmailNotification(emailNotification);
    emailNotificationRepository.save(emailNotification);
  }

  private void sendEmailNotification(EmailNotification emailNotification) {
    Notification notification = emailNotification.getNotification();
    User user = notification.getUser();

    Map<String, Object> templateModel = new HashMap<>();
    templateModel.put("email", user.getEmail());
    templateModel.put("title", notification.getTitle());
    templateModel.put("text", notification.getText());

    try {
      emailService.sendMessageUsingThymeleafTemplate(
          user.getEmail(), notification.getTitle(), templateModel, TEMPLATE_NAME);

      emailNotification.setStatus(EEmailStatus.SUCCESSFULLY_SENT);
    } catch (MessagingException e) {
      log.error(e.getMessage());
    }

    emailNotification.setNumberOfAttempts(emailNotification.getNumberOfAttempts() + 1);
  }
}
