package service;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import repository.*;
import utils.SessionManager;

@Getter
@Setter
public abstract class BaseService {
    protected Session session;
    protected final UserRepository userRepo;
    protected final CommentRepository commentRepo;
    protected final TagRepository tagRepo;
    protected final TaskRepository taskRepo;

    protected BaseService() {
        userRepo = new UserRepository();
        commentRepo = new CommentRepository();
        tagRepo = new TagRepository();
        taskRepo = new TaskRepository();
    }

    protected void updateSession() {
        session = SessionManager.getInstance().get();
        userRepo.setSession(session);
        commentRepo.setSession(session);
        tagRepo.setSession(session);
        taskRepo.setSession(session);

    }
}
