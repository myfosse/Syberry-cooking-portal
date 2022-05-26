package by.bsu.cookingportal.service;

public interface UpdateService<T, M> {
  T update(Long id, M dto);
}
