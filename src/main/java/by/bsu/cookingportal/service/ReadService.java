package by.bsu.cookingportal.service;

import java.util.List;

public interface ReadService<T> {
  List<T> getAll();

  T getById(Long id);
}
