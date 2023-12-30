package pap.db.Repository;

import pap.db.DAO.EntityDAO.BookDAO;
import pap.db.DAO.EntityDAO.BookGradeDAO;
import pap.db.DAO.EntityDAO.BookRentalDAO;
import pap.db.DAO.EntityDAO.ReadListDAO;
import pap.db.Entities.Book;
import pap.db.Entities.BookGrade;
import pap.db.Entities.ReadList;
import pap.db.Repository.Interface.IBookRepository;

import java.util.List;

public class BookRepository implements IBookRepository {
    private BookDAO bookDAO = new BookDAO();
    private BookGradeDAO bookGradeDAO = new BookGradeDAO();
    private ReadListDAO readListDAO = new ReadListDAO();

    @Override
    public Book getById(int id) {
        return bookDAO.read(id);
    }

    @Override
    public void create(Book entity) {
        bookDAO.create(entity);
    }

    @Override
    public void update(Book entity) {
        bookDAO.update(entity);
    }

    @Override
    public void delete(Book entity) {
        bookDAO.delete(entity);
    }

    @Override
    public List<Book> getAll() {
        return bookDAO.getAll();
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

    @Override
    public BookGrade getBookGrade(int bookId) {
        String sql = "SELECT * FROM pap.book_grades WHERE book_id = " + bookId;
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
