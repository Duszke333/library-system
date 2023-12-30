package pap.db;

import pap.db.Entities.User;
import pap.db.Repository.UserRepository;

import java.sql.Date;
import java.util.Scanner;



public class TestDatabase {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        /* Create Repository */
        UserRepository userRepository = new UserRepository();

        /* Get user */
        User user = userRepository.getById(1);
        System.out.println(user.getFirstName());

        /* Modify user */
        System.out.print("Enter new user's first name: ");
        String firstName = scanner.nextLine();
        user.setFirstName(firstName);
        userRepository.update(user);

        /* Get new user data */
        User updatedUser = userRepository.getById(1);
        System.out.println(updatedUser.getFirstName());

        /* Create new user */
        User newUser = new User();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setEmail("user@user.user");
        newUser.setPasswordHash("user");
        newUser.setPasswordSalt("user");
        newUser.setDateCreated(new Date(System.currentTimeMillis()));
        newUser.setAddressId(1);
        newUser.setActive(true);
        userRepository.create(newUser);

        /* Get new user data */
        newUser = userRepository.getById(2);
        System.out.println(newUser.getFirstName());

        /* Get all users */
        System.out.println("All users:");
        for (User u : userRepository.getAll()) {
            System.out.println(u.getFirstName());
        }

        User admin = userRepository.getByEmail("admin@admin.admin");
        admin.setActive(false);
        userRepository.update(admin);

        /* Get all active users */
        System.out.println("All active users:");
        for (User u : userRepository.getAllActive()) {
            System.out.println(u.getFirstName());
        }
    }
}
