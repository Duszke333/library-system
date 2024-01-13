package pap.db.DAO;

import pap.db.RandomEntityGenerator;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pap.db.DAO.EntityDAO.BookDAO;
import pap.db.Entities.Book;

public class TestBookDAO extends TestGenericDAO<Book, BookDAO>{
    @Override
    protected Book createEntity() {
        return RandomEntityGenerator.generateBook();
    }

    @Override
    protected BookDAO createDAO(SessionFactory factory) {
        return new BookDAO(factory);
    }

    @Override
    protected int getId(Book book) {
        int id;
        try (var session = factory.openSession()) {
            session.beginTransaction();
            var books = session.createNativeQuery(
                    "SELECT * FROM pap.books " +
                            "WHERE title = '" + book.getTitle() + "'" +
                            "AND author = '" + book.getAuthor() + "' AND isbn = '" + book.getIsbn() + "'"
                    , Book.class).list();
            if (books.isEmpty()) {
                return -1;
            }
            id = books.get(0).getBookId();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error while getting id of book " + book.getTitle());
            return -1;
        }
        return id;
    }

    @Test
    public void create() {
        BookDAO bookDAO = createDAO(factory);
        Book book = createEntity();
        bookDAO.create(book);
        Assertions.assertEquals(getId(book), book.getBookId());
    }

    @Test
    public void update() {
        BookDAO bookDAO = createDAO(factory);
        Book book = createEntity();
        bookDAO.create(book);
        book.setAuthor("Test2");
        bookDAO.update(book);
        Book newBook = bookDAO.read(getId(book));
        Assertions.assertEquals(book.getAuthor(), newBook.getAuthor());
    }

    @Test
    public void delete() {
        BookDAO bookDAO = createDAO(factory);
        Book book = createEntity();
        bookDAO.create(book);
        bookDAO.delete(book);
        Assertions.assertNull(bookDAO.read(getId(book)));
    }
}
