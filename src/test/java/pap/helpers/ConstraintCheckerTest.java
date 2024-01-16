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
        book.setCover(Book.CoverData.DefaultCover);

        assertDoesNotThrow(() -> ConstraintChecker.checkBook(book));
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
        book.setCover(Book.CoverData.DefaultCover);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkBook(book));
        assertTrue(e.getMessage().contains("ISBN too long"));
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
        book.setCover(Book.CoverData.DefaultCover);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkBook(book));
        assertTrue(e.getMessage().contains("Title too long"));
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
        book.setCover(Book.CoverData.DefaultCover);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkBook(book));
        assertTrue(e.getMessage().contains("Author name too long"));
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
        book.setCover(Book.CoverData.DefaultCover);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkBook(book));
        assertTrue(e.getMessage().contains("Genre name too long"));
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
        book.setCover(Book.CoverData.DefaultCover);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkBook(book));
        assertTrue(e.getMessage().contains("Invalid publication year"));
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
        book.setCover(Book.CoverData.DefaultCover);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkBook(book));
        assertTrue(e.getMessage().contains("Language name too long"));
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
        book.setCover(Book.CoverData.DefaultCover);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkBook(book));
        assertTrue(e.getMessage().contains("Invalid page count"));
        book.setPageCount(-3);
        e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkBook(book));
        assertTrue(e.getMessage().contains("Invalid page count"));
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
        book.setCover(Book.CoverData.DefaultCover);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkBook(book));
        assertTrue(e.getMessage().contains("Publisher name too long"));
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
        book.setCover(Book.CoverData.DefaultCover);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkBook(book));
        assertTrue(e.getMessage().contains("Invalid book status"));
    }

    @Test
    void testCheckBookInvalidCover() {
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
        book.setCover(">256_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkBook(book));
        assertTrue(e.getMessage().contains("Cover path too long"));
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

        assertDoesNotThrow(() -> ConstraintChecker.checkAddress(address));
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

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkAddress(address));
        assertTrue(e.getMessage().contains("Country name too long"));
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

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkAddress(address));
        assertTrue(e.getMessage().contains("Postal code too long"));
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

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkAddress(address));
        assertTrue(e.getMessage().contains("City name too long"));
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

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkAddress(address));
        assertTrue(e.getMessage().contains("Street name too long"));
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

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkAddress(address));
        assertTrue(e.getMessage().contains("House number too long"));
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

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkAddress(address));
        assertTrue(e.getMessage().contains("Flat number too long"));
    }

    @Test
    void testCheckUserValid() {
        User user = new User();
        user.setFirstName("Test name");
        user.setLastName("Test surname");
        user.setEmail("test@test.test");

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.getByEmail("test@test.test")).thenReturn(null);

        assertDoesNotThrow(() -> ConstraintChecker.checkUser(user, userRepo));
    }

    @Test
    void testCheckUserInvalidName() {
        User user = new User();
        user.setFirstName(">64_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        user.setLastName("Test surname");
        user.setEmail("test@test.test");

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.getByEmail("test@test.test")).thenReturn(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkUser(user, userRepo));
        assertTrue(e.getMessage().contains("Name too long"));
    }

    @Test
    void testCheckUserInvalidSurname() {
        User user = new User();
        user.setFirstName("Test name");
        user.setLastName(">64_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        user.setEmail("test@test.test");

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.getByEmail("test@test.test")).thenReturn(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkUser(user, userRepo));
        assertTrue(e.getMessage().contains("Surname too long"));
    }

    @Test
    void testCheckUserInvalidEmail() {
        User user = new User();
        user.setFirstName("Test name");
        user.setLastName("Test surname");
        user.setEmail(">64_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.getByEmail("test@test.test")).thenReturn(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkUser(user, userRepo));
        assertTrue(e.getMessage().contains("Email too long"));
    }

    @Test
    void testCheckUserEmailNotRegex() {
        User user = new User();
        user.setFirstName("Test name");
        user.setLastName("Test surname");
        user.setEmail("test.test");

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.getByEmail("test@test.test")).thenReturn(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkUser(user, userRepo));
        assertTrue(e.getMessage().contains("Invalid email"));

        user.setEmail("test@test");
        e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkUser(user, userRepo));
        assertTrue(e.getMessage().contains("Invalid email"));

        user.setEmail("test");
        e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkUser(user, userRepo));
        assertTrue(e.getMessage().contains("Invalid email"));
    }

    @Test
    void testCheckUserEmailAlreadyUsed() {
        User user = new User();
        user.setFirstName("Test name");
        user.setLastName("Test surname");
        user.setEmail("test@test.test");

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.getByEmail("test@test.test")).thenReturn(user);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkUser(user, userRepo));
        assertTrue(e.getMessage().contains("Email already in use"));
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

        assertDoesNotThrow(() -> ConstraintChecker.checkEmployee(emp, empRepo));
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

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkEmployee(emp, empRepo));
        assertTrue(e.getMessage().contains("Username too long"));
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

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkEmployee(emp, empRepo));
        assertTrue(e.getMessage().contains("Role name too long"));
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

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkEmployee(emp, empRepo));
        assertTrue(e.getMessage().contains("Username already in use"));
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

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ConstraintChecker.checkEmployee(emp, empRepo));
        assertTrue(e.getMessage().contains("User account already in use"));
    }
}