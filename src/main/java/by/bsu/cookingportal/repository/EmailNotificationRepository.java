package by.bsu.cookingportal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import by.bsu.cookingportal.entity.EEmailStatus;
import by.bsu.cookingportal.entity.EmailNotification;

public interface EmailNotificationRepository extends JpaRepository<EmailNotification, Long> {

    Optional<EmailNotification> findById(int id);

    List<Optional<EmailNotification>> findAllByStatus(EEmailStatus status);
}
