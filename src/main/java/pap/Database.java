package pap;

import java.sql.Date;
import java.util.Scanner;

import db.Repository.UserRepository;
import db.Entities.Address;
import db.Entities.User;
import db.Entities.Book;


public class Database {
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
        newUser.setEmail("user@user");
        newUser.setLogin("user");
        newUser.setPasswordHash("user");
        newUser.setPasswordSalt("user");
        newUser.setDateCreated(Date.valueOf("2021-01-01"));
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
    }
}
