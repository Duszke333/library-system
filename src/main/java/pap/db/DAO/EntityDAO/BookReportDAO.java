package pap.db.DAO.EntityDAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.DAO.GenericDAO;
import pap.db.Entities.BookReport;



public class BookReportDAO extends GenericDAO<BookReport> {

    public BookReportDAO() {
        super(BookReport.class, "pap.report");
    }

    public BookReportDAO(SessionFactory factory) {
        super(BookReport.class, "pap.report", factory);
    }
}
