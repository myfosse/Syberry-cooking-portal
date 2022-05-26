package by.bsu.cookingportal.service.impl;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.bsu.cookingportal.entity.AWSFile;
import by.bsu.cookingportal.repository.AWSFileRepository;
import by.bsu.cookingportal.service.AWSFileService;

/** @author Andrey Egorov */
@Service
public class AWSFileServiceImpl implements AWSFileService {

  private final AWSFileRepository awsFileRepository;

  @Autowired
  public AWSFileServiceImpl(AWSFileRepository awsFileRepository) {
    this.awsFileRepository = awsFileRepository;
  }

  @Override
  public AWSFile save(AWSFile file) {
    return awsFileRepository.save(file);
  }

  @Override
  public AWSFile findById(Long id) {
    return awsFileRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("No such file with id" + id));
  }

  @Override
  public AWSFile findByExternalId(String externalId) {
    return awsFileRepository
        .findByExternalId(externalId)
        .orElseThrow(
            () -> new EntityNotFoundException("No such file with external id" + externalId));
  }

  @Override
  public void deleteById(Long id) {
    awsFileRepository.deleteById(id);
  }

  @Override
  public void deleteByExternalId(String externalId) {
    awsFileRepository.deleteByExternalId(externalId);
  }
}
