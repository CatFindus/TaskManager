package repository;

import config.AbstractContainerDatabaseTest;
import config.EntityGenerator;
import lombok.Cleanup;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class UserRepositoryTest extends AbstractContainerDatabaseTest {

    @Test
    public void create_getById_test() {
        init();
        @Cleanup Session session = getFactory().openSession();
        UserRepository repo = new UserRepository();
        repo.setSession(session);
        User user = EntityGenerator.getUser();
        Transaction transaction = session.beginTransaction();
        user = repo.save(user);
        transaction.commit();
        session.refresh(user);
        session.evict(user);
        Transaction transaction1 = session.beginTransaction();
        Optional<User> mbUser = repo.findById(1L);
        transaction1.commit();
        assertTrue(mbUser.isPresent());
        assertEquals(user, mbUser.get());
        assertNotNull(user.getId());
        assertNotNull(user.getRegistredAt());
        assertNotNull(mbUser.get().getId());
        assertNotNull(mbUser.get().getRegistredAt());
    }
    @Test
    public void update_test() {
        init();
        @Cleanup Session session = getFactory().openSession();
        UserRepository repo = new UserRepository();
        repo.setSession(session);
        User user = EntityGenerator.getUser();
        Transaction transaction1 = session.beginTransaction();
        user = repo.save(user);
        transaction1.commit();
        session.refresh(user);
        session.evict(user);
        Transaction transaction2 = session.beginTransaction();
        user.setFirstName("new first name");
        user = repo.update(user);
        transaction2.commit();
        session.evict(user);
        Transaction transaction3 = session.beginTransaction();
        Optional<User> mbUser = repo.findById(1L);
        transaction3.commit();
        assertTrue(mbUser.isPresent());
        assertEquals(user, mbUser.get());
        assertEquals("new first name", user.getFirstName());
    }

    @Test
    public void getAll_count_test() {
        init();
        @Cleanup Session session = getFactory().openSession();
        UserRepository repo = new UserRepository();
        repo.setSession(session);
        int entityCount = 2 + (int) (Math.random()*10);
        List<User> users = EntityGenerator.getUserList(entityCount);
        Transaction transaction = session.beginTransaction();
        for (User user : users) repo.save(user);
        transaction.commit();
        session.clear();
        transaction = session.beginTransaction();
        List<User> getUserList = repo.findAll(100,0);
        long userCount = repo.count();
        transaction.commit();
        assertEquals(getUserList.size(), users.size());
        assertEquals(entityCount, userCount);
    }

    @Test
    public void delete_test() {
        init();
        @Cleanup Session session = getFactory().openSession();
        UserRepository repo = new UserRepository();
        repo.setSession(session);
        int count = 5;
        List<User> users = EntityGenerator.getUserList(count);
        Transaction transaction = session.beginTransaction();
        for (User user : users) repo.save(user);
        transaction.commit();
        transaction = session.beginTransaction();
        boolean deleted = repo.delete(1L);
        transaction.commit();
        session.clear();
        transaction = session.beginTransaction();
        Optional<User> mbUser = repo.findById(1L);
        long userCount = repo.count();
        transaction.commit();
        assertTrue(deleted);
        assertFalse(mbUser.isPresent());
        assertEquals(count-1, userCount);
    }

 }