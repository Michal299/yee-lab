package pl.edu.pg.s180564.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, K> {
    Optional<T> find(K id);
    List<T> findAll();
    K create(T entity);
    void delete(T entity);
    void update(T entity);
    void detach(T entity);
}
