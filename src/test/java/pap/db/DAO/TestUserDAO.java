package pap.db.DAO;

import pap.db.RandomEntityGenerator;
import org.hibernate.SessionFactory;
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
    protected int getId(User user) {
        int id;
        try (org.hibernate.Session session = factory.openSession()) {
            session.beginTransaction();
            java.util.List<User> users = session.createNativeQuery(
                    "SELECT * FROM pap.users " +
                            "WHERE email = '" + user.getEmail() + "'"
                    , User.class).list();
            if (users.isEmpty()) {
                return -1;
            }
            id = users.get(0).getAccountId();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error while getting id of user " + user.getEmail());
            return -1;
        }
        return id;
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
        Assertions.assertEquals(getId(user), user.getAccountId());
    }

    @Test
    public void update() {
        UserDAO userDAO = createDAO(factory);
        User user = createEntity();
        userDAO.create(user);
        user.setFirstName("Test2");
        userDAO.update(user);
        User newUser = userDAO.read(getId(user));
        Assertions.assertEquals(user.getFirstName(), newUser.getFirstName());
    }

    @Test
    public void delete() {
        UserDAO userDAO = createDAO(factory);
        User user = createEntity();
        userDAO.create(user);
        userDAO.delete(user);
        Assertions.assertNull(userDAO.read(getId(user)));
    }
}
