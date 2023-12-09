package db.Repository;

import db.Entities.User;

import java.util.List;

public interface UserRepository extends Repository<User> {
    User getByEmail(String email);
    User getByUsername(String username);
    List<User> getAllActive();
    List<User> getAllInactive();
}
