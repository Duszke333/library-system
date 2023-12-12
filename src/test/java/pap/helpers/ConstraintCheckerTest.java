package pap.helpers;

import org.junit.jupiter.api.Test;
import pap.db.Entities.Address;
import pap.db.Entities.Book;
import pap.db.Entities.Employee;
import pap.db.Entities.User;
import pap.db.Repository.EmployeeRepository;
import pap.db.Repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConstraintCheckerTest {
    @Test
    void testCheckBookValid() {
        Book book = new Book();
        book.setIsbn("123-4567-890-12-31242");
        book.setTitle("Test title");
        book.setAuthor("Test author");
        book.setGenre("Test genre");
        book.setPublicationYear(2020);
        book.setLanguage("Test language");
        book.setPageCount(100);
        book.setPublisher("Test publisher");
        book.setStatus("Available");

        assert(ConstraintChecker.checkBook(book) == -1);
    }

    @Test
    void testCheckBookInvalidIsbn() {
        Book book = new Book();
        book.setIsbn(">32signs_xxxxxxxxxxxxxxxxxxxxxxxx");
        book.setTitle("Test title");
        book.setAuthor("Test author");
        book.setGenre("Test genre");
        book.setPublicationYear(2020);
        book.setLanguage("Test language");
        book.setPageCount(100);
        book.setPublisher("Test publisher");
        book.setStatus("Available");

        assert(ConstraintChecker.checkBook(book) == ConstraintChecker.BookErrors.ISBN_TOO_LONG.ordinal());
    }

    @Test
    void testCheckBookInvalidTitle() {
        Book book = new Book();
        book.setIsbn("123-4567-890-12-31242");
        book.setTitle(">128_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        book.setAuthor("Test author");
        book.setGenre("Test genre");
        book.setPublicationYear(2020);
        book.setLanguage("Test language");
        book.setPageCount(100);
        book.setPublisher("Test publisher");
        book.setStatus("Available");

        assert (ConstraintChecker.checkBook(book) == ConstraintChecker.BookErrors.TITLE_TOO_LONG.ordinal());
    }

    @Test
    void testCheckBookInvalidAuthor() {
        Book book = new Book();
        book.setIsbn("123-4567-890-12-31242");
        book.setTitle("Test title");
        book.setAuthor(">128_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        book.setGenre("Test genre");
        book.setPublicationYear(2020);
        book.setLanguage("Test language");
        book.setPageCount(100);
        book.setPublisher("Test publisher");
        book.setStatus("Available");

        assert (ConstraintChecker.checkBook(book) == ConstraintChecker.BookErrors.AUTHOR_TOO_LONG.ordinal());
    }

    @Test
    void testCheckBookInvalidGenre() {
        Book book = new Book();
        book.setIsbn("123-4567-890-12-31242");
        book.setTitle("Test title");
        book.setAuthor("Test author");
        book.setGenre(">128_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        book.setPublicationYear(2020);
        book.setLanguage("Test language");
        book.setPageCount(100);
        book.setPublisher("Test publisher");
        book.setStatus("Available");

        assert(ConstraintChecker.checkBook(book) == ConstraintChecker.BookErrors.GENRE_TOO_LONG.ordinal());
    }

    @Test
    void testCheckBookInvalidPublicationYear() {
        Book book = new Book();
        book.setIsbn("123-4567-890-12-31242");
        book.setTitle("Test title");
        book.setAuthor("Test author");
        book.setGenre("Test genre");
        book.setPublicationYear(2026);
        book.setLanguage("Test language");
        book.setPageCount(100);
        book.setPublisher("Test publisher");
        book.setStatus("Available");

        assert(ConstraintChecker.checkBook(book) == ConstraintChecker.BookErrors.WRONG_PUBLICATION_YEAR.ordinal());
    }

    @Test
    void testCheckBookInvalidLanguage() {
        Book book = new Book();
        book.setIsbn("123-4567-890-12-31242");
        book.setTitle("Test title");
        book.setAuthor("Test author");
        book.setGenre("Test genre");
        book.setPublicationYear(2020);
        book.setLanguage(">128_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        book.setPageCount(100);
        book.setPublisher("Test publisher");
        book.setStatus("Available");

        assert(ConstraintChecker.checkBook(book) == ConstraintChecker.BookErrors.LANGUAGE_TOO_LONG.ordinal());
    }

    @Test
    void testCheckBookInvalidPageCount() {
        Book book = new Book();
        book.setIsbn("123-4567-890-12-31242");
        book.setTitle("Test title");
        book.setAuthor("Test author");
        book.setGenre("Test genre");
        book.setPublicationYear(2020);
        book.setLanguage("Test language");
        book.setPageCount(0);
        book.setPublisher("Test publisher");
        book.setStatus("Available");

        assert(ConstraintChecker.checkBook(book) == ConstraintChecker.BookErrors.WRONG_PAGE_COUNT.ordinal());
        book.setPageCount(-3);
        assert(ConstraintChecker.checkBook(book) == ConstraintChecker.BookErrors.WRONG_PAGE_COUNT.ordinal());
    }

    @Test
    void testCheckBookInvalidPublisher() {
        Book book = new Book();
        book.setIsbn("123-4567-890-12-31242");
        book.setTitle("Test title");
        book.setAuthor("Test author");
        book.setGenre("Test genre");
        book.setPublicationYear(2020);
        book.setLanguage("Test language");
        book.setPageCount(100);
        book.setPublisher(">128_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        book.setStatus("Available");

        assert(ConstraintChecker.checkBook(book) == ConstraintChecker.BookErrors.PUBLISHER_TOO_LONG.ordinal());
    }

    @Test
    void testCheckBookInvalidStatus() {
        Book book = new Book();
        book.setIsbn("123-4567-890-12-31242");
        book.setTitle("Test title");
        book.setAuthor("Test author");
        book.setGenre("Test genre");
        book.setPublicationYear(2020);
        book.setLanguage("Test language");
        book.setPageCount(100);
        book.setPublisher("Test publisher");
        book.setStatus("Unrecognized status");

        assert(ConstraintChecker.checkBook(book) == ConstraintChecker.BookErrors.WRONG_STATUS.ordinal());
    }

    @Test
    void testCheckAddressValid() {
        Address address = new Address();
        address.setCountry("Test country");
        address.setPostalCode("12-345");
        address.setCity("Test city");
        address.setStreet("Test street");
        address.setHouseNumber("12");
        address.setFlatNumber("12");

        assert(ConstraintChecker.checkAddress(address) == -1);
    }

    @Test
    void testCheckAddressInvalidCountry() {
        Address address = new Address();
        address.setCountry(">64_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        address.setPostalCode("12-345");
        address.setCity("Test city");
        address.setStreet("Test street");
        address.setHouseNumber("12");
        address.setFlatNumber("12");

        assert(ConstraintChecker.checkAddress(address) == ConstraintChecker.AddressErrors.COUNTRY_TOO_LONG.ordinal());
    }

    @Test
    void testCheckAddressInvalidPostalCode() {
        Address address = new Address();
        address.setCountry("Test country");
        address.setPostalCode(">16_xxxxxxxxxxxxxxxx");
        address.setCity("Test city");
        address.setStreet("Test street");
        address.setHouseNumber("12");
        address.setFlatNumber("12");

        assert(ConstraintChecker.checkAddress(address) == ConstraintChecker.AddressErrors.POSTAL_CODE_TOO_LONG.ordinal());
    }

    @Test
    void testCheckAddressInvalidCity() {
        Address address = new Address();
        address.setCountry("Test country");
        address.setPostalCode("12-345");
        address.setCity(">64_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        address.setStreet("Test street");
        address.setHouseNumber("12");
        address.setFlatNumber("12");

        assert(ConstraintChecker.checkAddress(address) == ConstraintChecker.AddressErrors.CITY_TOO_LONG.ordinal());
    }

    @Test
    void testCheckAddressInvalidStreet() {
        Address address = new Address();
        address.setCountry("Test country");
        address.setPostalCode("12-345");
        address.setCity("Test city");
        address.setStreet(">64_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        address.setHouseNumber("12");
        address.setFlatNumber("12");

        assert(ConstraintChecker.checkAddress(address) == ConstraintChecker.AddressErrors.STREET_TOO_LONG.ordinal());
    }

    @Test
    void testCheckAddressInvalidHouseNumber() {
        Address address = new Address();
        address.setCountry("Test country");
        address.setPostalCode("12-345");
        address.setCity("Test city");
        address.setStreet("Test street");
        address.setHouseNumber(">16_xxxxxxxxxxxxxxxx");
        address.setFlatNumber("12");

        assert(ConstraintChecker.checkAddress(address) == ConstraintChecker.AddressErrors.HOUSE_NUMBER_TOO_LONG.ordinal());
    }

    @Test
    void testCheckAddressInvalidFlatNumber() {
        Address address = new Address();
        address.setCountry("Test country");
        address.setPostalCode("12-345");
        address.setCity("Test city");
        address.setStreet("Test street");
        address.setHouseNumber("12");
        address.setFlatNumber(">16_xxxxxxxxxxxxxxxx");

        assert(ConstraintChecker.checkAddress(address) == ConstraintChecker.AddressErrors.FLAT_NUMBER_TOO_LONG.ordinal());
    }

    @Test
    void testCheckUserValid() {
        User user = new User();
        user.setFirstName("Test name");
        user.setLastName("Test surname");
        user.setEmail("test@test.test");

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.getByEmail("test@test.test")).thenReturn(null);

        assert(ConstraintChecker.checkUser(user, userRepo) == -1);
    }

    @Test
    void testCheckUserInvalidName() {
        User user = new User();
        user.setFirstName(">64_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        user.setLastName("Test surname");
        user.setEmail("test@test.test");

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.getByEmail("test@test.test")).thenReturn(null);

        assert(ConstraintChecker.checkUser(user, userRepo) == ConstraintChecker.UserErrors.NAME_TOO_LONG.ordinal());
    }

    @Test
    void testCheckUserInvalidSurname() {
        User user = new User();
        user.setFirstName("Test name");
        user.setLastName(">64_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        user.setEmail("test@test.test");

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.getByEmail("test@test.test")).thenReturn(null);

        assert(ConstraintChecker.checkUser(user, userRepo) == ConstraintChecker.UserErrors.SURNAME_TOO_LONG.ordinal());
    }

    @Test
    void testCheckUserInvalidEmail() {
        User user = new User();
        user.setFirstName("Test name");
        user.setLastName("Test surname");
        user.setEmail(">64_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.getByEmail("test@test.test")).thenReturn(null);

        assert(ConstraintChecker.checkUser(user, userRepo) == ConstraintChecker.UserErrors.EMAIL_TOO_LONG.ordinal());
    }

    @Test
    void testCheckUserEmailNotRegex() {
        User user = new User();
        user.setFirstName("Test name");
        user.setLastName("Test surname");
        user.setEmail("test.test");

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.getByEmail("test@test.test")).thenReturn(null);

        assert(ConstraintChecker.checkUser(user, userRepo) == ConstraintChecker.UserErrors.EMAIL_INVALID.ordinal());

        user.setEmail("test@test");
        assert(ConstraintChecker.checkUser(user, userRepo) == ConstraintChecker.UserErrors.EMAIL_INVALID.ordinal());

        user.setEmail("test");
        assert(ConstraintChecker.checkUser(user, userRepo) == ConstraintChecker.UserErrors.EMAIL_INVALID.ordinal());
    }

    @Test
    void testCheckUserEmailAlreadyUsed() {
        User user = new User();
        user.setFirstName("Test name");
        user.setLastName("Test surname");
        user.setEmail("test@test.test");

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.getByEmail("test@test.test")).thenReturn(user);

        assert (ConstraintChecker.checkUser(user, userRepo) == ConstraintChecker.UserErrors.EMAIL_ALREADY_USED.ordinal());
    }

    @Test
    void testCheckEmployeeValid() {
        Employee emp = new Employee();
        emp.setUsername("Test username");
        emp.setRole("Test role");
        emp.setUserID(1);

        EmployeeRepository empRepo = mock(EmployeeRepository.class);
        when(empRepo.getByUsername("Test username")).thenReturn(null);
        when(empRepo.getByUserID(1)).thenReturn(null);

        assert (ConstraintChecker.checkEmployee(emp, empRepo) == -1);
    }

    @Test
    void testCheckEmployeeInvalidUsername() {
        Employee emp = new Employee();
        emp.setUsername(">128_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        emp.setRole("Test role");
        emp.setUserID(1);

        EmployeeRepository empRepo = mock(EmployeeRepository.class);
        when(empRepo.getByUsername("Test username")).thenReturn(null);
        when(empRepo.getByUserID(1)).thenReturn(null);

        assert (ConstraintChecker.checkEmployee(emp, empRepo) == ConstraintChecker.EmployeeErrors.USERNAME_TOO_LONG.ordinal());
    }

    @Test
    void testCheckEmployeeInvalidRole() {
        Employee emp = new Employee();
        emp.setUsername("Test username");
        emp.setRole(">64_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        emp.setUserID(1);

        EmployeeRepository empRepo = mock(EmployeeRepository.class);
        when(empRepo.getByUsername("Test username")).thenReturn(null);
        when(empRepo.getByUserID(1)).thenReturn(null);

        assert (ConstraintChecker.checkEmployee(emp, empRepo) == ConstraintChecker.EmployeeErrors.ROLE_TOO_LONG.ordinal());
    }

    @Test
    void testCheckEmployeeInvalidUsernameAlreadyUsed() {
        Employee emp = new Employee();
        emp.setUsername("Test username");
        emp.setRole("Test role");
        emp.setUserID(1);

        EmployeeRepository empRepo = mock(EmployeeRepository.class);
        when(empRepo.getByUsername("Test username")).thenReturn(emp);
        when(empRepo.getByUserID(1)).thenReturn(null);

        assert (ConstraintChecker.checkEmployee(emp, empRepo) == ConstraintChecker.EmployeeErrors.USERNAME_ALREADY_USED.ordinal());
    }

    @Test
    void testCheckEmployeeInvalidUserAccountAlreadyInUse() {
        Employee emp = new Employee();
        emp.setUsername("Test username");
        emp.setRole("Test role");
        emp.setUserID(1);

        EmployeeRepository empRepo = mock(EmployeeRepository.class);
        when(empRepo.getByUsername("Test username")).thenReturn(null);
        when(empRepo.getByUserID(1)).thenReturn(emp);

        assert (ConstraintChecker.checkEmployee(emp, empRepo) == ConstraintChecker.EmployeeErrors.USER_ACCOUNT_ALREADY_IN_USE.ordinal());
    }
}