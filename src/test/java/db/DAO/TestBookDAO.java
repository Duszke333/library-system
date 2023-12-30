package db.DAO;

import db.TestSessionFactoryMaker;
import db.HelperMethods;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pap.db.DAO.EntityDAO.BookDAO;
import pap.db.Entities.Book;

public class TestBookDAO {
    private SessionFactory factory = TestSessionFactoryMaker.getSessionFactory();

    @Before
    public void setup() {
        HelperMethods.clearTable("BOOKS");
    }

    @After
    public void teardown() {
        HelperMethods.clearTable("BOOKS");
    }

    @Test
    public void create() {
        BookDAO bookDAO = new BookDAO(factory);

        Book book = new Book();
        book.setTitle("Test");
        book.setAuthor("Test");
        book.setGenre("Test");
        book.setDateAdded(new java.sql.Date(System.currentTimeMillis()));
        book.setIsbn("Test");
        book.setLanguage("Test");
        book.setPageCount(10);
        book.setPublicationYear(2020);
        book.setPublisher("Test");
        book.setDescription("Test");
        book.setStatus("Test");
        book.setCover("Test");

        bookDAO.create(book);

        Book newBook = bookDAO.read(HelperMethods.getId(book));
        Assertions.assertEquals(book.getTitle(), newBook.getTitle());
        Assertions.assertEquals(book.getAuthor(), newBook.getAuthor());
    }

    @Test
    public void update() {
        BookDAO bookDAO = new BookDAO(factory);

        Book book = new Book();
        book.setTitle("Test");
        book.setAuthor("Test");
        book.setGenre("Test");
        book.setDateAdded(new java.sql.Date(System.currentTimeMillis()));
        book.setIsbn("Test");
        book.setLanguage("Test");
        book.setPageCount(10);
        book.setPublicationYear(2020);
        book.setPublisher("Test");
        book.setDescription("Test");
        book.setStatus("Test");
        book.setCover("Test");

        bookDAO.create(book);

        book.setTitle("Test2");
        bookDAO.update(book);

        Book newBook = bookDAO.read(HelperMethods.getId(book));
        Assertions.assertEquals(book.getTitle(), newBook.getTitle());
    }

    @Test
    public void delete() {
        BookDAO bookDAO = new BookDAO(factory);

        Book book = new Book();
        book.setTitle("Test");
        book.setAuthor("Test");
        book.setGenre("Test");
        book.setDateAdded(new java.sql.Date(System.currentTimeMillis()));
        book.setIsbn("Test");
        book.setLanguage("Test");
        book.setPageCount(10);
        book.setPublicationYear(2020);
        book.setPublisher("Test");
        book.setDescription("Test");
        book.setStatus("Test");
        book.setCover("Test");

        bookDAO.create(book);

        bookDAO.delete(book);

        Book newBook = bookDAO.read(HelperMethods.getId(book));
        Assertions.assertNull(newBook);
    }
}
