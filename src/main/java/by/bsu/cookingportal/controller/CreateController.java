package by.bsu.cookingportal.controller;

import org.springframework.http.ResponseEntity;

public interface CreateController<T, M> {
  ResponseEntity<T> saveEntity(M dto);
}
