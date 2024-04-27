package repository;

import model.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface Repository<E extends BaseEntity> {
    E save(E entity);

    E update(E entity);

    boolean delete(Long id);

    List<E> findAll(int limit, int offset);

    Optional<E> findById(Long id);

    Long count();
}
