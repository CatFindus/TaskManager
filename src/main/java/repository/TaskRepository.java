package repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import model.Task;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.*;

import static constants.ApplicationConstants.*;


public class TaskRepository extends BaseRepository<Task> {

    public TaskRepository() {
        super(Task.class);
    }

    public List<Task> findTasksForUser(Long userId, int limit, int offset) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Task> cq = cb.createQuery(Task.class);
        Root<Task> root = cq.from(Task.class);
        cq.select(root).where(cb.equal(root.get("userId"), userId));
        cq.orderBy(cb.asc(root.get("id")));
        Query<Task> query = session.createQuery(cq);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public Long getTaskCountForUserId(Long userId) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Task> root = cq.from(Task.class);
        cq.select(cb.count(root)).where(cb.equal(root.get("userId"), userId));
        Query<Long> query = session.createQuery(cq);
        return query.getSingleResult();
    }

    public Long findTaskCountForUserByParams(Long currentUserId, Map<String, Object> parameterMap) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Task> root = cq.from(Task.class);
        List<Predicate> predicates = getPredicateFromParams(cb, root, parameterMap);
        predicates.add(cb.equal(root.get(FIELD_USER).get(FIELD_ID), currentUserId));
        cq.select(cb.count(root)).where(predicates.toArray(Predicate[]::new));
        Query<Long> query = session.createQuery(cq);
        return query.getSingleResult();
    }

    public List<Task> findTasksForUserByParams(Long currentUserId, Map<String, Object> parameterMap) {
        int limit = parameterMap.get(FIELD_LIMIT) == null ? 10 : (Integer) parameterMap.get(FIELD_LIMIT);
        int offset = parameterMap.get(FIELD_OFFSET) == null ? 0 : (Integer) parameterMap.get(FIELD_OFFSET);
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Task> cq = cb.createQuery(Task.class);
        Root<Task> root = cq.from(Task.class);
        List<Predicate> predicates = getPredicateFromParams(cb, root, parameterMap);
        predicates.add(cb.equal(root.get(FIELD_USER).get(FIELD_ID), currentUserId));
        cq.select(root).where(predicates.toArray(Predicate[]::new));
        Query<Task> query = session.createQuery(cq);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    private List<Predicate> getPredicateFromParams(CriteriaBuilder cb, Root<Task> root, Map<String, Object> parameterMap) {
        List<Predicate> predicates = new ArrayList<>();
        for (String key : parameterMap.keySet()) {
            Object value = parameterMap.get(key);
            switch (key) {
                case FIELD_ID -> predicates.add(cb.equal(root.get(FIELD_ID), value));
                case FIELD_STATUS -> predicates.add(cb.equal(root.get(FIELD_STATUS), value));
                case FIELD_TITLE -> predicates.add(cb.like(root.get(FIELD_TITLE), "%" + value.toString() + "%"));
                case FIELD_PLANNED_START ->
                        predicates.add(cb.greaterThanOrEqualTo(root.get(FIELD_PLANNED_START), (LocalDateTime) value));
                case FIELD_PLANNED_END ->
                        predicates.add(cb.lessThanOrEqualTo(root.get(FIELD_PLANNED_END), (LocalDateTime) value));
                case FIELD_TAG ->
                        predicates.add(cb.like(root.get(FIELD_TAG).get(FIELD_TITLE), "%" + value.toString() + "%"));
            }
        }
        return predicates;
    }
}
