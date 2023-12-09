package pap;

import java.util.Scanner;

import db.DAO.UserDAO;
import db.Entities.Address;
import db.Entities.User;
import db.Entities.Book;


public class Database {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        /* Create DAOs */
        UserDAO userDAO = new UserDAO();

        /* Get user */
        User user = userDAO.read(1);
        System.out.println(user.getFirstName());

        /* Modify user */
        System.out.print("Enter new user's first name: ");
        String firstName = scanner.nextLine();
        user.setFirstName(firstName);
        userDAO.update(user);

        /* Get new user data */
        User newUser = userDAO.read(1);
        System.out.println(newUser.getFirstName());
    }
}
