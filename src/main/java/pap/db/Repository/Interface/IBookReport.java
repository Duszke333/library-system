package pap.db.Repository.Interface;
import pap.db.Entities.BookReport;


public interface IBookReport extends IRepository<BookReport> {
    BookReport getReportByUserAndBook(int userId, int bookId);

}
