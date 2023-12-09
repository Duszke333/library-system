package db.DAO;

import db.Entities.Book;
import db.Repository.BookRepository;
import db.SessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class BookDAO implements DAO<Book> {
    SessionFactory factory = SessionFactoryMaker.getSessionFactory();

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
}
