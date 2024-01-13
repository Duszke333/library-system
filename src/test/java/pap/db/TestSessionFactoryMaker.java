package pap.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class TestSessionFactoryMaker {
    private TestSessionFactoryMaker() {}
    private static SessionFactory sessionFactory=null;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");
                configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5433/pap");
                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}

