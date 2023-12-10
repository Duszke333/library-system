package db.Repository.Interface;

import db.Entities.User;

import java.util.List;

public interface IUserRepository extends IRepository<User> {
    User getByEmail(String email);
    User getByUsername(String username);
    List<User> getAllActive();
    List<User> getAllInactive();
}
