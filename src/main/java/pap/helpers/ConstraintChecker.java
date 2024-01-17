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
     * A class that checks if the given data is valid
     * before the program tries to put it into / update it in the database.
     */

    /**
     * A method that checks if the given book data is valid before it is uploaded to / updated in  the database.
     * @param book the book object to be checked
     * @throws ConstraintViolationException upon encounter of an incorrect value of any field
     */
    public static void checkBook(Book book) throws ConstraintViolationException {
        if (book.getIsbn().length() > 32) throw new ConstraintViolationException("ISBN too long");
        if (book.getTitle().length() > 128) throw new ConstraintViolationException("Title too long");
        if (book.getAuthor().length() > 128) throw new ConstraintViolationException("Author name too long");
        if (book.getGenre().length() > 128) throw new ConstraintViolationException("Genre name too long");
        if (book.getPublisher().length() > 128) throw new ConstraintViolationException("Publisher name too long");
        if (book.getLanguage().length() > 128) throw new ConstraintViolationException("Language name too long");
        if (!book.getStatus().equals(Book.BookStatus.Available) && !book.getStatus().equals(Book.BookStatus.Unavailable)
                && !book.getStatus().equals(Book.BookStatus.Rented) && !book.getStatus().equals(Book.BookStatus.Reserved)
                && !book.getStatus().equals(Book.BookStatus.ReadyForPickup)) {
            throw new ConstraintViolationException("Invalid book status");
        }
        if (book.getPublicationYear() > Year.now().getValue()) throw new ConstraintViolationException("Invalid publication year");
        if (book.getPageCount() <= 0) throw new ConstraintViolationException("Invalid page count");
        if (book.getCover().length() > 256) throw new ConstraintViolationException("Cover path too long");
    }

    /**
     * A method that checks if the given address data is valid before it is uploaded to / updated in the database.
     * @param address the address object to be checked
     * @throws ConstraintViolationException upon encounter of an incorrect value of any field
     */
    public static void checkAddress(Address address) throws ConstraintViolationException {
        if (address.getCountry().length() > 64) throw new ConstraintViolationException("Country name too long");
        if (address.getPostalCode().length() > 16) throw new ConstraintViolationException("Postal code too long");
        if (address.getCity().length() > 64) throw new ConstraintViolationException("City name too long");
        if (address.getStreet().length() > 64) throw new ConstraintViolationException("Street name too long");
        if (address.getHouseNumber().length() > 16) throw new ConstraintViolationException("House number too long");
        if (address.getFlatNumber().length() > 16) throw new ConstraintViolationException("Flat number too long");
    }

    /**
     * A method that checks if the given user data is valid before it is uploaded to the database.
     * @param user the user object to be checked
     * @param userRepo the user repository object used to check if the email is already in use
     * @throws ConstraintViolationException upon encounter of an incorrect value of any field
     */
    public static void checkUser(User user, UserRepository userRepo) throws ConstraintViolationException {
        if (user.getFirstName().length() > 64) throw new ConstraintViolationException("Name too long");
        if (user.getLastName().length() > 64) throw new ConstraintViolationException("Surname too long");
        if (user.getEmail().length() > 64) throw new ConstraintViolationException("Email too long");
        if (!user.getEmail().matches(".*@.*\\..*")) throw new ConstraintViolationException("Invalid email");
        if (userRepo.getByEmail(user.getEmail()) != null) throw new ConstraintViolationException("Email already in use");
    }

    /**
     * A method that checks if the given employee data is valid before it is uploaded to the database.
     * @param emp the employee object to be checked
     * @param empRepo the employee repository object used to check if the username or tied user account is already in use
     * @throws ConstraintViolationException upon encounter of an incorrect value of any field
     */
    public static void checkEmployee(Employee emp, EmployeeRepository empRepo) throws ConstraintViolationException {
        if (emp.getUsername().length() > 128) throw new ConstraintViolationException("Username too long");
        if (emp.getRole().length() > 64) throw new ConstraintViolationException("Role name too long");
        if (empRepo.getByUsername(emp.getUsername()) != null) throw new ConstraintViolationException("Username already in use");
        if (empRepo.getByUserID(emp.getUserID()) != null) throw new ConstraintViolationException("User account already in use");
    }
}
