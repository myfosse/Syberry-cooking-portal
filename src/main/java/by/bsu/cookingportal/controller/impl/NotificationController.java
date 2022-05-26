package by.bsu.cookingportal.controller.impl;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import by.bsu.cookingportal.entity.Notification;
import by.bsu.cookingportal.exception.ValidationException;
import by.bsu.cookingportal.payload.request.CreateNotificationRequest;
import by.bsu.cookingportal.payload.response.MessageResponse;
import by.bsu.cookingportal.service.NotificationService;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

  private final NotificationService notificationService;

  public NotificationController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @PostMapping("/create")
  public @ResponseBody MessageResponse createNotification(
      @Valid @RequestBody final CreateNotificationRequest createNotificationRequest,
      BindingResult errors) {

    if (errors.hasErrors()) {
      throw new ValidationException(errors);
    }

    notificationService.createNotification(
        "NOTIFICATION_BASE",
        createNotificationRequest.getEmail(),
        createNotificationRequest.getTitle(),
        createNotificationRequest.getText(),
        false);

    return new MessageResponse(
        "Successfully created a new notification for the user with email: "
            + createNotificationRequest.getEmail());
  }

  @PostMapping("/create-and-send")
  public @ResponseBody MessageResponse createAndSendNotification(
      @Valid @RequestBody final CreateNotificationRequest createNotificationRequest,
      BindingResult errors) {

    if (errors.hasErrors()) {
      throw new ValidationException(errors);
    }

    Notification notification =
        notificationService.createNotification(
            "NOTIFICATION_EMAIL",
            createNotificationRequest.getEmail(),
            createNotificationRequest.getTitle(),
            createNotificationRequest.getText(),
            false);

    notificationService.sendNotification(notification, createNotificationRequest.getEmail());

    return new MessageResponse(
        "Successfully created and sent a new notification for the user with email: "
            + createNotificationRequest.getEmail());
  }
}
