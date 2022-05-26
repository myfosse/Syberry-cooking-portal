package by.bsu.cookingportal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface ReadController<T> {
  ResponseEntity<List<T>> getAllEntities();

  ResponseEntity<T> getEntity(Long id);

}
