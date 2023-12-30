package pap.db.DAO.EntityDAO;

import pap.db.DAO.GenericDAO;
import pap.db.Entities.User;
import pap.db.SessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDAO extends GenericDAO<User> {

    public UserDAO() {
        super(User.class, "pap.users");
    }

    public UserDAO(SessionFactory factory) {
        super(User.class, "pap.users", factory);
    }
}
