package pap.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryMaker {
    private SessionFactoryMaker() {}
    private static SessionFactory sessionFactory;

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory != null) {
            return sessionFactory;
        }
        
        Configuration conf = new Configuration().configure("hibernate.cfg.xml");
        sessionFactory = conf.buildSessionFactory();
        
        return sessionFactory;
    }
}
