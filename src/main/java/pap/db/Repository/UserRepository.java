package pap.db.Repository;

import pap.db.DAO.GenericDAO;
import pap.db.Entities.User;
import pap.db.DAO.EntityDAO.UserDAO;
import pap.db.Repository.Interface.IUserRepository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserRepository extends GenericRepository<User> implements IUserRepository {
    UserDAO userDAO = new UserDAO();

    public UserRepository() {
        super(User.class, new UserDAO());
    }

    @Override
    public User getByEmail(String email) throws NullPointerException {
        String sql = "SELECT * FROM pap.users WHERE email = '" + email + "'";
        List<User> users = userDAO.query(sql);
        if (users.size() == 0) {
            throw new NullPointerException("User with email " + email + " not found");
        }
        return users.get(0);
    }

    @Override
    public User getByUsername(String username) throws NullPointerException {
        String sql = "SELECT * FROM pap.users WHERE username = '" + username + "'";
        List<User> users = userDAO.query(sql);
        if (users.size() == 0) {
            throw new NullPointerException("User with username " + username + " not found");
        }
        return users.get(0);
    }

    @Override
    public List<User> getAllActive() throws NullPointerException {
        String sql = "SELECT * FROM pap.users WHERE active = true";
        List<User> users = userDAO.query(sql);
        if (users.size() == 0) {
            throw new NullPointerException("No active users found");
        }
        return users;
    }

    @Override
    public List<User> getAllInactive() throws NullPointerException {
        String sql = "SELECT * FROM pap.users WHERE active = false";
        List<User> users =  userDAO.query(sql);
        if (users.size() == 0) {
            throw new NullPointerException("No inactive users found");
        }
        return users;
    }
}
