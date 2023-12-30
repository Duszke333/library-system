package pap.db.DAO.EntityDAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.DAO.GenericDAO;
import pap.db.Entities.BookGrade;
import pap.db.SessionFactoryMaker;

import java.util.List;

public class BookGradeDAO extends GenericDAO<BookGrade> {

    public BookGradeDAO() {
        super(BookGrade.class, "pap.book_grades");
    }

    public BookGradeDAO(SessionFactory factory) {
        super(BookGrade.class, "pap.book_grades", factory);
    }
}
