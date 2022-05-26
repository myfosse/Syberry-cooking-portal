package by.bsu.cookingportal.service;

import by.bsu.cookingportal.entity.AWSFile;

/** @author Andrey Egorov */
public interface AWSFileService {

  AWSFile save(final AWSFile file);

  AWSFile findById(final Long id);

  AWSFile findByExternalId(final String externalId);

  void deleteById(final Long id);

  void deleteByExternalId(final String externalId);
}
