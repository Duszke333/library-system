package pap.db.Repository;

import pap.db.DAO.EntityDAO.BookReportDAO;
import pap.db.Entities.BookReport;
import pap.db.Repository.Interface.IBookReport;

import java.util.List;

public class ReportRepository implements IBookReport {
    private BookReportDAO bookReportDAO = new BookReportDAO();
    @Override
    public List<BookReport> getAll() {
        return bookReportDAO.getAll();
    }

    public List<BookReport> getUnresolved() throws NullPointerException {
        var sql = "SELECT * FROM pap.reports WHERE resolved = false";
        List<BookReport> list = bookReportDAO.query(sql);
        if (list.isEmpty()) {
            throw new NullPointerException("No unresolved reports found");
        }
        return list;
    }

    @Override
    public BookReport getById(int id) {
        return bookReportDAO.read(id);
    }

    @Override
    public void create(BookReport entity) {
        bookReportDAO.create(entity);
    }

    @Override
    public void update(BookReport entity) {
        bookReportDAO.update(entity);
    }

    @Override
    public void delete(BookReport entity) {
        bookReportDAO.delete(entity);
    }

    @Override
    public BookReport getReportByUserAndBook(int userId, int bookId) throws NullPointerException {
        String sql = "SELECT * FROM pap.reports WHERE user_id = " + userId + " AND book_id = " + bookId;
        List<BookReport> reports =  bookReportDAO.query(sql);
        if (reports.isEmpty()) {
            throw new NullPointerException("Report for user with id " + userId + " and book with id " + bookId + " not found");
        }
        return reports.get(0);
    }
}
