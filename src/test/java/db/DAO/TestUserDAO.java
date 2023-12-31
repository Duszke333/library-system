package db.DAO;

import db.HelperMethods;
import db.RandomEntityGenerator;
import db.TestSessionFactoryMaker;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pap.db.DAO.EntityDAO.AddressDAO;
import pap.db.DAO.EntityDAO.UserDAO;
import pap.db.Entities.Address;
import pap.db.Entities.User;

public class TestUserDAO extends TestGenericDAO<User, UserDAO>{

    private AddressDAO addressDAO = new AddressDAO(factory);

    @Override
    protected User createEntity() {
        Address address = RandomEntityGenerator.generateAddress();
        addressDAO.create(address);
        User user = RandomEntityGenerator.generateUser(address);
        return user;
    }

    @Override
    protected UserDAO createDAO(SessionFactory factory) {
        return new UserDAO(factory);
    }

    @Test
    public void create() {
        UserDAO userDAO = createDAO(factory);
        User user = createEntity();
        userDAO.create(user);
        Assertions.assertEquals(HelperMethods.getId(user), user.getAccountId());
    }

    @Test
    public void update() {
        UserDAO userDAO = createDAO(factory);
        User user = createEntity();
        userDAO.create(user);
        user.setFirstName("Test2");
        userDAO.update(user);
        User newUser = userDAO.read(HelperMethods.getId(user));
        Assertions.assertEquals(user.getFirstName(), newUser.getFirstName());
    }

    @Test
    public void delete() {
        UserDAO userDAO = createDAO(factory);
        User user = createEntity();
        userDAO.create(user);
        userDAO.delete(user);
        Assertions.assertNull(userDAO.read(HelperMethods.getId(user)));
    }
}
