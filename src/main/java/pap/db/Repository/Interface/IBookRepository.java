package pap.db.Repository.Interface;

import pap.db.Entities.Book;
import pap.db.Entities.BookGrade;
import pap.db.Entities.ReadList;

import java.util.List;

public interface IBookRepository extends IRepository<Book> {
    Book getByTitle(String title);
    Book getByISBN(String ISBN);
    List<Book> getByAuthor(String author);
    List<Book> getByGenre(String genre);
    List<Book> getByDateAdded(String dateAdded);
    List<Book> getByDatePublished(String datePublished);
    List<Book> getAllByLanguage(String language);
    List<Book> getAllAvailable();
    List<Book> getMostPopular(String periodType);
    BookGrade getBookGrade(int bookId);
    List<BookGrade> getAllBookGrades();
    List<BookGrade> getBookGradesByUser(int userId);
    BookGrade getThisBookGradeByUser(int bookId, int userId);
    void updateBookGrade(BookGrade bookGrade);
    void addBookGrade(BookGrade bookGrade);
    void deleteBookGrade(BookGrade bookGrade);
    ReadList getReadList(int readListId);
    List<ReadList> getReadListByUser(int userId);
    List<ReadList> getReadListByBook(int bookId);
    void addReadList(ReadList readList);
    void deleteReadList(ReadList readList);
    void updateReadList(ReadList readList);
}
