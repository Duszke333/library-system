package pap.db.Repository;

import pap.db.DAO.BookDAO;
import pap.db.Entities.Book;

import java.util.List;

public class BookRepository implements IBookRepository {
    private BookDAO bookDAO = new BookDAO();

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

    @Deprecated (since = "getAll works fine now.", forRemoval = true)
    public Book[] getAllBookType(){return bookDAO.getAllBookType();}


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
}
