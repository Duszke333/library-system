package pap.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;

public class SessionFactoryMaker {
    private SessionFactoryMaker() {}
    private static SessionFactory sessionFactory;

    /**
     * @return
     *     The session factory
     *     This method is used to create a session factory
     *     It is used to connect to the database using hibernate
     */
    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory != null) {
            return sessionFactory;
        }
        
        Configuration conf = new Configuration().configure("hibernate.cfg.xml");
        sessionFactory = conf.buildSessionFactory();
        
        return sessionFactory;
    }

    public static synchronized Connection getConnection() throws SQLException {
        return sessionFactory.getSessionFactoryOptions().getServiceRegistry().getService(ConnectionProvider.class).getConnection();
    }
}
