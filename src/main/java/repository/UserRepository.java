package repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.User;
import org.hibernate.query.Query;

import java.util.Optional;

public class UserRepository extends BaseRepository<User> {
    public UserRepository() {
        super(User.class);
    }

    public Optional<User> findByLoginAndPass(String userName, String pass) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root).where(cb.equal(root.get("userName"), userName), cb.equal(root.get("passwordHash"), pass));
        Query<User> query = session.createQuery(cq);
        return query.uniqueResultOptional();
    }
}
