package db.DAO;

import db.TestSessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import pap.db.DAO.BookDAO;
import pap.db.Entities.Book;

public class TestBookDAO {
    private SessionFactory factory = TestSessionFactoryMaker.getSessionFactory();

    @BeforeEach
    public void setup() {

        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE pap.BOOKS CASCADE").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @AfterEach
    public void teardown() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE pap.BOOKS CASCADE").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    public void createAndRead() {
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
        book.setAvailable(true);

        bookDAO.create(book);

        Book newBook = bookDAO.read(1);
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
        book.setAvailable(true);

        bookDAO.create(book);

        book.setTitle("Test2");
        bookDAO.update(book);

        Book newBook = bookDAO.read(1);
        Assertions.assertEquals(book.getTitle(), newBook.getTitle());
    }
}
