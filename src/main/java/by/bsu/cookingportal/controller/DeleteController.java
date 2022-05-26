package by.bsu.cookingportal.controller;

import org.springframework.http.ResponseEntity;

public interface DeleteController<T> {
  ResponseEntity<T> deleteEntity(Long id);

}
