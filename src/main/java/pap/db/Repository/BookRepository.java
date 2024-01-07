package pap.db.Repository;

import javafx.util.Pair;
import pap.db.DAO.EntityDAO.BookDAO;
import pap.db.DAO.EntityDAO.BookGradeDAO;
import pap.db.DAO.EntityDAO.BookRentalDAO;
import pap.db.DAO.EntityDAO.ReadListDAO;
import pap.db.DAO.GenericDAO;
import pap.db.Entities.Book;
import pap.db.Entities.BookGrade;
import pap.db.Entities.ReadList;
import pap.db.Repository.Interface.IBookRepository;

import java.util.List;

public class BookRepository extends GenericRepository<Book> implements IBookRepository {
    private BookDAO bookDAO = new BookDAO();
    private BookGradeDAO bookGradeDAO = new BookGradeDAO();
    private ReadListDAO readListDAO = new ReadListDAO();

    public BookRepository() {
        super(Book.class, new BookDAO());
    }

    @Override
    public Book getByTitle(String title) {
        String sql = "SELECT * FROM pap.books WHERE title = '" + title + "'";
        List<Book> books = bookDAO.query(sql);
        if (books.size() == 0) {
            return null;
        }
        return books.get(0);
    }

    @Override
    public Book getByISBN(String ISBN) {
        String sql = "SELECT * FROM pap.books WHERE ISBN = '" + ISBN + "'";
        List<Book> books = bookDAO.query(sql);
        if (books.size() == 0) {
            return null;
        }
        return books.get(0);
    }

    @Override
    public List<Book> getByAuthor(String author) {
        String sql = "SELECT * FROM pap.books WHERE author = '" + author + "'";
        List<Book> books = bookDAO.query(sql);
        if (books.size() == 0) {
            return null;
        }
        return books;
    }

    @Override
    public List<Book> getByGenre(String genre) {
        String sql = "SELECT * FROM pap.books WHERE genre = '" + genre + "'";
        List<Book> books = bookDAO.query(sql);
        if (books.size() == 0) {
            return null;
        }
        return books;
    }

    @Override
    public List<Book> getByDateAdded(String dateAdded) {
        String sql = "SELECT * FROM pap.books WHERE date_added = '" + dateAdded + "'";
        List<Book> books = bookDAO.query(sql);
        if (books.size() == 0) {
            return null;
        }
        return books;
    }

    @Override
    public List<Book> getByDatePublished(String datePublished) {
        String sql = "SELECT * FROM pap.books WHERE date_published = '" + datePublished + "'";
        List<Book> books = bookDAO.query(sql);
        if (books.size() == 0) {
            return null;
        }
        return books;
    }

    @Override
    public List<Book> getAllByLanguage(String language) {
        String sql = "SELECT * FROM pap.books WHERE language = '" + language + "'";
        List<Book> books = bookDAO.query(sql);
        if (books.size() == 0) {
            return null;
        }
        return books;
    }

    @Override
    public List<Book> getAllAvailable() {
        String sql = "SELECT * FROM pap.books WHERE available = true";
        List<Book> books = bookDAO.query(sql);
        if (books.size() == 0) {
            return null;
        }
        return books;
    }
    public List<Book> getMostPopular(String periodType) {
        String sql;
        if (!periodType.equalsIgnoreCase("all_time"))
        {
            sql = "SELECT * FROM pap.BOOKS WHERE book_id in (SELECT book_id FROM pap.BOOK_RENTALS WHERE Date_rented >= CURRENT_DATE - INTERVAL '1 " + periodType + "' GROUP BY Book_id ORDER BY COUNT(*) DESC)";
        }else{
            sql = "SELECT * FROM pap.BOOKS WHERE book_id in (SELECT book_id FROM pap.BOOK_RENTALS GROUP BY Book_id ORDER BY COUNT(*) DESC)";
        }
        return bookDAO.query(sql);
    }

    @Override
    public BookGrade getBookGrade(int gradeId) {
        String sql = "SELECT * FROM pap.book_grades WHERE grade_id = " + gradeId;
        List<BookGrade> bookGrades = bookGradeDAO.query(sql);
        if (bookGrades.size() == 0) {
            return null;
        }
        return bookGrades.get(0);
    }

    @Override
    public List<BookGrade> getAllBookGrades() {
        return bookGradeDAO.getAll();
    }

    @Override
    public List<BookGrade> getBookGradesByUser(int userId) {
        String sql = "SELECT * FROM pap.book_grades WHERE user_id = " + userId;
        List<BookGrade> bookGrades = bookGradeDAO.query(sql);
        if (bookGrades.size() == 0) {
            return null;
        }
        return bookGrades;
    }

    public List<BookGrade> getBookGradesByBook(int bookId) {
        String sql = "SELECT * FROM pap.book_grades WHERE book_id = " + bookId;
        List<BookGrade> bookGrades = bookGradeDAO.query(sql);
        if (bookGrades.size() == 0) {
            return null;
        }
        return bookGrades;
    }

    public Pair<Integer, Double> getBookGradeCountAndAverageGrade(int bookId) {
        List<BookGrade> bookGrades = getBookGradesByBook(bookId);
        if (bookGrades == null) {
            return null;
        }
        int count = bookGrades.size();
        double sum = 0;
        for (BookGrade bookGrade : bookGrades) {
            sum += bookGrade.getGrade();
        }
        return new Pair<Integer, Double>(count, Math.round(sum * 100 / count) / 100.0);
    }
    @Override
    public BookGrade getThisBookGradeByUser(int bookId, int userId) {
        String sql = "SELECT * FROM pap.book_grades WHERE user_id = " + userId + " AND book_id = " + bookId;
        List<BookGrade> bookGrades = bookGradeDAO.query(sql);
        if (bookGrades.size() == 0) {
            return null;
        }
        return bookGrades.get(0);
    }

    @Override
    public void updateBookGrade(BookGrade bookGrade) {
        bookGradeDAO.update(bookGrade);
    }

    @Override
    public void addBookGrade(BookGrade bookGrade) {
        bookGradeDAO.create(bookGrade);
    }

    @Override
    public void deleteBookGrade(BookGrade bookGrade) {
        bookGradeDAO.delete(bookGrade);
    }

    @Override
    public ReadList getReadList(int readListId) {
        String sql = "SELECT * FROM pap.read_lists WHERE read_list_id = " + readListId;
        List<ReadList> readLists = readListDAO.query(sql);
        if (readLists.size() == 0) {
            return null;
        }
        return readLists.get(0);
    }

    @Override
    public List<ReadList> getReadListByUser(int userId) {
        String sql = "SELECT * FROM pap.read_lists WHERE user_id = " + userId;
        List<ReadList> readLists = readListDAO.query(sql);
        if (readLists.size() == 0) {
            return null;
        }
        return readLists;
    }

    @Override
    public List<ReadList> getReadListByBook(int bookId) {
        String sql = "SELECT * FROM pap.read_lists WHERE book_id = " + bookId;
        List<ReadList> readLists = readListDAO.query(sql);
        if (readLists.size() == 0) {
            return null;
        }
        return readLists;
    }

    @Override
    public void addReadList(ReadList readList) {
        readListDAO.create(readList);
    }

    @Override
    public void deleteReadList(ReadList readList) {
        readListDAO.delete(readList);
    }

    @Override
    public void updateReadList(ReadList readList) {
        readListDAO.update(readList);
    }
}
