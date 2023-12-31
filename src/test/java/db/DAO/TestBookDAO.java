package db.DAO;

import db.RandomEntityGenerator;
import db.TestSessionFactoryMaker;
import db.HelperMethods;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
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

    @Test
    public void create() {
        BookDAO bookDAO = createDAO(factory);
        Book book = createEntity();
        bookDAO.create(book);
        Assertions.assertEquals(HelperMethods.getId(book), book.getBookId());
    }

    @Test
    public void update() {
        BookDAO bookDAO = createDAO(factory);
        Book book = createEntity();
        bookDAO.create(book);
        book.setAuthor("Test2");
        bookDAO.update(book);
        Book newBook = bookDAO.read(HelperMethods.getId(book));
        Assertions.assertEquals(book.getAuthor(), newBook.getAuthor());
    }

    @Test
    public void delete() {
        BookDAO bookDAO = createDAO(factory);
        Book book = createEntity();
        bookDAO.create(book);
        bookDAO.delete(book);
        Assertions.assertNull(bookDAO.read(HelperMethods.getId(book)));
    }
}
