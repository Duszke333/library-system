package pap.db.Repository;

import pap.db.DAO.GenericDAO;
import pap.db.Entities.User;
import pap.db.DAO.EntityDAO.UserDAO;
import pap.db.Repository.Interface.IUserRepository;

import java.util.List;

public class UserRepository extends GenericRepository<User> implements IUserRepository {
    UserDAO userDAO = new UserDAO();

    public UserRepository() {
        super(User.class, new UserDAO());
    }

    @Override
    public User getByEmail(String email) {
        String sql = "SELECT * FROM pap.users WHERE email = '" + email + "'";
        List<User> users = userDAO.query(sql);
        if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public User getByUsername(String username) {
        String sql = "SELECT * FROM pap.users WHERE username = '" + username + "'";
        List<User> users = userDAO.query(sql);
        if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public List<User> getAllActive() {
        String sql = "SELECT * FROM pap.users WHERE active = true";
        return userDAO.query(sql);
    }

    @Override
    public List<User> getAllInactive() {
        String sql = "SELECT * FROM pap.users WHERE active = false";
        return userDAO.query(sql);
    }
}
