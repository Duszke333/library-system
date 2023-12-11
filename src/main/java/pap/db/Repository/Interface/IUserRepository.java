package pap.db.Repository.Interface;

import pap.db.Entities.User;

import java.util.List;

public interface IUserRepository extends IRepository<User> {
    User getByEmail(String email);
    User getByUsername(String username);
    List<User> getAllActive();
    List<User> getAllInactive();
}
