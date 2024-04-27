package repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.Comment;
import org.hibernate.Session;

import java.util.List;

public class CommentRepository extends BaseRepository<Comment> {
    public CommentRepository() {
        super(Comment.class);
    }

    public List<Comment> findAllForTask(Long taskId) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);
        Root<Comment> root = cq.from(Comment.class);
        cq.select(root).where(cb.equal(root.get("task").get("id"), taskId));
        cq.orderBy(cb.desc(root.get("createdAt")));
        return session.createQuery(cq).getResultList();
    }

    public List<Comment> findAllForUser(Long userId) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);
        Root<Comment> root = cq.from(Comment.class);
        cq.select(root).where(cb.equal(root.get("user").get("id"), userId));
        return session.createQuery(cq).getResultList();
    }
}
