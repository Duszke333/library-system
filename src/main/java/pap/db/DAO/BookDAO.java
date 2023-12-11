package pap.db.DAO;

import pap.db.Entities.Book;
import pap.db.SessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class BookDAO implements DAO<Book> {
    private SessionFactory factory;

    public BookDAO() {
        factory = SessionFactoryMaker.getSessionFactory();
    }

    public BookDAO(SessionFactory factory) {
        this.factory = factory;
    }

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

    public Book[] getAllBookType() {
        List<Book> booksList = List.of();
        try (Session session = factory.openSession()) {
            booksList = session.createNativeQuery("SELECT * FROM pap.books", Book.class).list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Book[] booksArray = new Book[booksList.size()];
        booksArray = booksList.toArray(booksArray);

        return booksArray;
    }

    public List<Book> getAll() {
        List<Book> books = List.of();
        try (Session session = factory.openSession()) {
            books = session.createNativeQuery("SELECT * FROM pap.books", Book.class).list();
            return books;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return books;
    }


    public List<Book> query(String sql) {
        List<Book> books = List.of();
        try (Session session = factory.openSession()) {
            books = session.createNativeQuery(sql, Book.class).list();
            return books;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return books;
    }
}
