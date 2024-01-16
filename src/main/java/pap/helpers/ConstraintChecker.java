package pap.helpers;

import pap.db.Entities.Address;
import pap.db.Entities.Book;
import pap.db.Entities.Employee;
import pap.db.Entities.User;
import pap.db.Repository.EmployeeRepository;
import pap.db.Repository.UserRepository;

import java.time.Year;

public class ConstraintChecker {
    /**
     * A class that checks if the given data is valid before the program tries to put it into the database.
     */

    /**
     * A method that checks if the given book data is valid.
     * @param book the book object to be checked
     * @throws IllegalArgumentException upon encounter of an incorrect value of any field
     */
    public static void checkBook(Book book) throws IllegalArgumentException {
        if (book.getIsbn().length() > 32) throw new IllegalArgumentException("ISBN too long");
        if (book.getTitle().length() > 128) throw new IllegalArgumentException("Title too long");
        if (book.getAuthor().length() > 128) throw new IllegalArgumentException("Author name too long");
        if (book.getGenre().length() > 128) throw new IllegalArgumentException("Genre name too long");
        if (book.getPublisher().length() > 128) throw new IllegalArgumentException("Publisher name too long");
        if (book.getLanguage().length() > 128) throw new IllegalArgumentException("Language name too long");
        if (!book.getStatus().equals(Book.BookStatus.Available) && !book.getStatus().equals(Book.BookStatus.Unavailable)
                && !book.getStatus().equals(Book.BookStatus.Rented) && !book.getStatus().equals(Book.BookStatus.Reserved)
                && !book.getStatus().equals(Book.BookStatus.ReadyForPickup)) {
            throw new IllegalArgumentException("Invalid book status");
        }
        if (book.getPublicationYear() > Year.now().getValue()) throw new IllegalArgumentException("Invalid publication year");
        if (book.getPageCount() <= 0) throw new IllegalArgumentException("Invalid page count");
        if (book.getCover().length() > 256) throw new IllegalArgumentException("Cover path too long");
    }

    /**
     * A method that checks if the given address data is valid.
     * @param address the address object to be checked
     * @throws IllegalArgumentException upon encounter of an incorrect value of any field
     */
    public static void checkAddress(Address address) throws IllegalArgumentException {
        if (address.getCountry().length() > 64) throw new IllegalArgumentException("Country name too long");
        if (address.getPostalCode().length() > 16) throw new IllegalArgumentException("Postal code too long");
        if (address.getCity().length() > 64) throw new IllegalArgumentException("City name too long");
        if (address.getStreet().length() > 64) throw new IllegalArgumentException("Street name too long");
        if (address.getHouseNumber().length() > 16) throw new IllegalArgumentException("House number too long");
        if (address.getFlatNumber().length() > 16) throw new IllegalArgumentException("Flat number too long");
    }

    /**
     * A method that checks if the given user data is valid.
     * @param user the user object to be checked
     * @param userRepo the user repository object used to check if the email is already in use
     * @throws IllegalArgumentException upon encounter of an incorrect value of any field
     */
    public static void checkUser(User user, UserRepository userRepo) throws IllegalArgumentException {
        if (user.getFirstName().length() > 64) throw new IllegalArgumentException("Name too long");
        if (user.getLastName().length() > 64) throw new IllegalArgumentException("Surname too long");
        if (user.getEmail().length() > 64) throw new IllegalArgumentException("Email too long");
        if (!user.getEmail().matches(".*@.*\\..*")) throw new IllegalArgumentException("Invalid email");
        if (userRepo.getByEmail(user.getEmail()) != null) throw new IllegalArgumentException("Email already in use");
    }

    /**
     * A method that checks if the given employee data is valid.
     * @param emp the employee object to be checked
     * @param empRepo the employee repository object used to check if the username or tied user account is already in use
     * @throws IllegalArgumentException upon encounter of an incorrect value of any field
     */
    public static void checkEmployee(Employee emp, EmployeeRepository empRepo) throws IllegalArgumentException {
        if (emp.getUsername().length() > 128) throw new IllegalArgumentException("Username too long");
        if (emp.getRole().length() > 64) throw new IllegalArgumentException("Role name too long");
        if (empRepo.getByUsername(emp.getUsername()) != null) throw new IllegalArgumentException("Username already in use");
        if (empRepo.getByUserID(emp.getUserID()) != null) throw new IllegalArgumentException("User account already in use");
    }
}
