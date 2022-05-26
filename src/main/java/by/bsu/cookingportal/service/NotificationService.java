package by.bsu.cookingportal.service;

import by.bsu.cookingportal.entity.EmailNotification;
import by.bsu.cookingportal.entity.Notification;

public interface NotificationService {
  Notification createNotification(
      String notificationType, String email, String title, String text, boolean isViewed);

  void sendAllNotifications();

  void sendNotification(Notification notification, String email);

  void saveAndSendEmailNotification(EmailNotification event);
}
