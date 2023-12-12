package db.DAO;

import db.TestSessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pap.db.DAO.BookDAO;
import pap.db.Entities.Book;

import java.util.List;

public class TestBookDAO {
    private SessionFactory factory = TestSessionFactoryMaker.getSessionFactory();

    private int getId(Book book) {
        int id;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            List<Book> books = session.createNativeQuery("SELECT * FROM pap.books WHERE isbn = '" + book.getIsbn() + "'", Book.class).list();
            if (books.isEmpty()) {
                return -1;
            }
            id = books.get(0).getBookId();
            session.getTransaction().commit();
        }
        return id;
    }

    @Before
    public void setup() {

        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE pap.BOOKS CASCADE").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @After
    public void teardown() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE pap.BOOKS CASCADE").executeUpdate();
            session.getTransaction().commit();
        }
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
        book.setAvailable(true);

        bookDAO.create(book);

        Book newBook = bookDAO.read(this.getId(book));
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

        Book newBook = bookDAO.read(this.getId(book));
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
        book.setAvailable(true);

        bookDAO.create(book);

        bookDAO.delete(book);

        Book newBook = bookDAO.read(this.getId(book));
        Assertions.assertNull(newBook);
    }
}
