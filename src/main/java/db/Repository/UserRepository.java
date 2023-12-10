package db.Repository;

import db.Entities.User;
import db.DAO.UserDAO;
import db.Repository.Interface.IUserRepository;

import java.util.List;

public class UserRepository implements IUserRepository {
    UserDAO userDAO = new UserDAO();
    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }

    @Override
    public User getById(int id) {
        return userDAO.read(id);
    }

    @Override
    public void create(User entity) {
        userDAO.create(entity);
    }

    @Override
    public void update(User entity) {
        userDAO.update(entity);
    }

    @Override
    public void delete(User entity) {
        userDAO.delete(entity);
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
