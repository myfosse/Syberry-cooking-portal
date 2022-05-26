package by.bsu.cookingportal.service;

public interface CreateService<T, M> {
  T save(M dto);
}
