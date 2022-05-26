package by.bsu.cookingportal.event;

import javax.mail.MessagingException;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import by.bsu.cookingportal.entity.EmailNotification;
import by.bsu.cookingportal.service.NotificationService;

@Component
public class GenericEventListener {

  private final NotificationService notificationService;

  public GenericEventListener(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @Async
  @EventListener
  public void handleEmailNotificationEvent(EmailNotification event) throws MessagingException {
    notificationService.saveAndSendEmailNotification(event);
  }
}
