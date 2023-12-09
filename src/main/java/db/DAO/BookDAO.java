package db.DAO;

import db.Entities.Book;
import db.Repository.BookRepository;
import db.SessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class BookDAO implements DAO<Book>, BookRepository {
    SessionFactoryMaker sessionFactoryMaker = new SessionFactoryMaker();
    SessionFactory factory = sessionFactoryMaker.getSessionFactory();

    public void create(Book book) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(book);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Book read(int id) {
        Book book = null;
        try (Session session = factory.openSession()) {
            book = session.get(Book.class, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return book;
    }

    public void update(Book book) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.update(book);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(Book book) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.delete(book);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Book getById(int id) {
        return null;
    }

    public Book getByTitle(String title) {
        return null;
    }

    public Book getByISBN(String ISBN) {
        return null;
    }

    public Book getByAuthor(String author) {
        return null;
    }

    public Book getByGenre(String genre) {
        return null;
    }

    public Book getByDateAdded(String dateAdded) {
        return null;
    }

    public Book getByDatePublished(String datePublished) {
        return null;
    }

    public List<Book> getAllByLanguage(String language) {
        return null;
    }

    public List<Book> getAllAvailable() {
        return null;
    }

    public List<Book> getAll() {
        return null;
    }
}
