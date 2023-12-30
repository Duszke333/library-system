package pap.db.DAO.EntityDAO;

import pap.db.DAO.GenericDAO;
import pap.db.Entities.Book;
import pap.db.SessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class BookDAO extends GenericDAO<Book> {

    public BookDAO() {
        super(Book.class, "pap.books");
    }

    public BookDAO(SessionFactory factory) {
        super(Book.class, "pap.books", factory);
    }
}
