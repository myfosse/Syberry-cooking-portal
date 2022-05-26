package by.bsu.cookingportal.controller;

import org.springframework.http.ResponseEntity;

public interface UpdateController<T, M> {
  ResponseEntity<T> updateEntity(Long id, M dto);
}
