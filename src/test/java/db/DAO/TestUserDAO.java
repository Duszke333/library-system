package db.DAO;

import db.HelperMethods;
import db.TestSessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pap.db.DAO.AddressDAO;
import pap.db.DAO.UserDAO;
import pap.db.Entities.Address;
import pap.db.Entities.User;

import java.util.List;

public class TestUserDAO {
    private SessionFactory factory = TestSessionFactoryMaker.getSessionFactory();

    @Before
    public void setup() {
        HelperMethods.clearTable("USERS");
    }

    @After
    public void teardown() {
        HelperMethods.clearTable("USERS");
    }

    @Test
    public void create() {
        UserDAO userDAO = new UserDAO(factory);
        AddressDAO addressDAO = new AddressDAO(factory);

        Address address = new Address();
        address.setStreet("Test");
        address.setCountry("Test");
        address.setCity("Test");
        address.setPostalCode("Test");
        address.setHouseNumber("1");
        address.setFlatNumber("1");

        addressDAO.create(address);

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEmail("test@test");
        user.setPasswordHash("test");
        user.setPasswordSalt("test");
        user.setDateCreated(new java.sql.Date(System.currentTimeMillis()));
        user.setActive(true);
        user.setHasUnpaidPenalty(false);
        user.setAddressId(HelperMethods.getId(address));

        userDAO.create(user);

        User newUser = userDAO.read(HelperMethods.getId(user));
        Assertions.assertEquals(user.getFirstName(), newUser.getFirstName());
        Assertions.assertEquals(user.getLastName(), newUser.getLastName());
    }

    @Test
    public void update() {
        UserDAO userDAO = new UserDAO(factory);
        AddressDAO addressDAO = new AddressDAO(factory);

        Address address = new Address();
        address.setStreet("Test");
        address.setCountry("Test");
        address.setCity("Test");
        address.setPostalCode("Test");
        address.setHouseNumber("1");
        address.setFlatNumber("1");

        addressDAO.create(address);

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEmail("test@test");
        user.setPasswordHash("test");
        user.setPasswordSalt("test");
        user.setDateCreated(new java.sql.Date(System.currentTimeMillis()));
        user.setActive(true);
        user.setHasUnpaidPenalty(false);
        user.setAddressId(HelperMethods.getId(address));

        userDAO.create(user);

        user.setFirstName("Test2");
        userDAO.update(user);

        User newUser = userDAO.read(HelperMethods.getId(user));
        Assertions.assertEquals(user.getFirstName(), newUser.getFirstName());
    }

    @Test
    public void delete() {
        UserDAO userDAO = new UserDAO(factory);
        AddressDAO addressDAO = new AddressDAO(factory);

        Address address = new Address();
        address.setStreet("Test");
        address.setCountry("Test");
        address.setCity("Test");
        address.setPostalCode("Test");
        address.setHouseNumber("1");
        address.setFlatNumber("1");

        addressDAO.create(address);

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEmail("test@test");
        user.setPasswordHash("test");
        user.setPasswordSalt("test");
        user.setDateCreated(new java.sql.Date(System.currentTimeMillis()));
        user.setActive(true);
        user.setHasUnpaidPenalty(false);
        user.setAddressId(HelperMethods.getId(address));

        userDAO.create(user);

        userDAO.delete(user);

        User newUser = userDAO.read(HelperMethods.getId(user));
        Assertions.assertNull(newUser);
    }
}
