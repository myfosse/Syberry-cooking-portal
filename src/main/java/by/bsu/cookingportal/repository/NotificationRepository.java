package by.bsu.cookingportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import by.bsu.cookingportal.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findById(int id);
}
