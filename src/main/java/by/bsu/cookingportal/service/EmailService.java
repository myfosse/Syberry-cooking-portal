package by.bsu.cookingportal.service;

import java.util.Map;

import javax.mail.MessagingException;

/** @author Andrey Egorov */
public interface EmailService {

  void sendMessageUsingThymeleafTemplate(
      String to, String subject, Map<String, Object> templateModel, String template)
      throws MessagingException;
}
