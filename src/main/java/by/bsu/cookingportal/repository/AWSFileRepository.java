package by.bsu.cookingportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.bsu.cookingportal.entity.AWSFile;

/** @author Andrey Egorov */
@Repository
public interface AWSFileRepository extends JpaRepository<AWSFile, Long> {

  Optional<AWSFile> findByExternalId(final String externalId);

  void deleteByExternalId(final String externalId);
}
