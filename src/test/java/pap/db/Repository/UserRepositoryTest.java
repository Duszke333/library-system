package pap.db.Repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pap.db.Entities.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    UserRepository userRepository = new UserRepository();
    @Test
    void getAll() {
        List<User> users = userRepository.getAll();
        Assertions.assertNotNull(users);
    }

    @Test
    void getById() {
        User user = userRepository.getById(1);
        Assertions.assertNotNull(user);
    }
}