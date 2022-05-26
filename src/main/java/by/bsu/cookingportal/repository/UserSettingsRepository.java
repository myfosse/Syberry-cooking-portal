package by.bsu.cookingportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.bsu.cookingportal.entity.UserSettings;

@Repository
public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> {}
