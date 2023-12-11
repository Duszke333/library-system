package db.DAO;

import db.TestSessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import pap.db.DAO.UserDAO;
import pap.db.Entities.User;

public class TestUserDAO {
    private SessionFactory factory = TestSessionFactoryMaker.getSessionFactory();

    @BeforeEach
    public void setup() {

        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE pap.USERS CASCADE").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @AfterEach
    public void teardown() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE pap.USERS CASCADE").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    public void createAndRead() {
        UserDAO userDAO = new UserDAO(factory);

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEmail("test@test");
        user.setPasswordHash("test");
        user.setPasswordSalt("test");
        user.setDateCreated(new java.sql.Date(System.currentTimeMillis()));
        user.setActive(true);
        user.setAddressId(1);

        userDAO.create(user);

        User newUser = userDAO.read(1);
        Assertions.assertEquals(user.getFirstName(), newUser.getFirstName());
        Assertions.assertEquals(user.getLastName(), newUser.getLastName());
    }

    @Test
    public void update() {
        UserDAO userDAO = new UserDAO(factory);

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEmail("test@test");
        user.setPasswordHash("test");
        user.setPasswordSalt("test");
        user.setDateCreated(new java.sql.Date(System.currentTimeMillis()));
        user.setActive(true);
        user.setAddressId(1);

        userDAO.create(user);

        user.setFirstName("Test2");
        userDAO.update(user);

        User newUser = userDAO.read(1);
        Assertions.assertEquals(user.getFirstName(), newUser.getFirstName());
    }

    @Test
    public void delete() {
        UserDAO userDAO = new UserDAO(factory);

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEmail("test@test");
        user.setPasswordHash("test");
        user.setPasswordSalt("test");
        user.setDateCreated(new java.sql.Date(System.currentTimeMillis()));
        user.setActive(true);
        user.setAddressId(1);

        userDAO.create(user);

        userDAO.delete(user);

        User newUser = userDAO.read(1);
        Assertions.assertNull(newUser);
    }
}
