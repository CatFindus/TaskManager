package repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import model.BaseEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
@Setter
public class BaseRepository<E extends BaseEntity> implements Repository<E> {

    protected Session session;
    private final Class<E> entityClass;

    @Override
    public E save(E entity) {
        session.persist(entity);
        session.refresh(entity);
        return entity;
    }

    @Override
    public E update(E entity) {
        entity = session.merge(entity);
        return entity;
    }

    @Override
    public boolean delete(Long id) {
        Optional<E> entity = findById(id);
        if (entity.isPresent()) {
            session.remove(entity.get());
            return true;
        } else return false;
    }

    @Override
    public List<E> findAll(int limit, int offset) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(entityClass);
        Root<E> root = cq.from(entityClass);
        cq.select(root);
        Query<E> query = session.createQuery(cq);
        query.setMaxResults(limit);
        query.setFirstResult(offset);
        return query.list();
    }

    @Override
    public Optional<E> findById(Long id) {
        E entity = session.find(entityClass, id);
        return Optional.ofNullable(entity);
    }

    @Override
    public Long count() {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<E> root = cq.from(entityClass);
        cq.select(cb.count(root));
        return session.createQuery(cq).getSingleResult();
    }


}
