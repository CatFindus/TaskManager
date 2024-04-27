package utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionManager {
    private static final SessionManager INSTANCE = new SessionManager();
    private SessionFactory factory;

    private SessionManager() {
        try {
            factory = new Configuration().configure().buildSessionFactory();
            System.out.println("SessionFactory created");
        } catch (Throwable e) {
            System.err.println("SessionFactory creation failed: " + e);
        }


    }

    public static SessionManager getInstance() {
        return INSTANCE;
    }

    public Session get() {
        return factory.getCurrentSession();
    }

    public void  closeFactory() {
        if (!factory.isClosed()) factory.close();
    }
}
