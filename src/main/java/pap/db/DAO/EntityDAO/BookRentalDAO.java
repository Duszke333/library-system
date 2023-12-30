package pap.db.DAO.EntityDAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.DAO.GenericDAO;
import pap.db.Entities.BookRental;
import pap.db.SessionFactoryMaker;

import java.util.List;

public class BookRentalDAO extends GenericDAO<BookRental> {

    public BookRentalDAO() {
        super(BookRental.class, "pap.book_rentals");
    }

    public BookRentalDAO(SessionFactory factory) {
        super(BookRental.class, "pap.book_rentals", factory);
    }
}
