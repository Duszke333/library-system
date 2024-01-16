package pap.db.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.Entities.Branch;
import pap.db.SessionFactoryMaker;

import java.util.List;

/**
 * @param <T> Entity class
 *     Generic DAO implementation
 *     This class is used implement methods for other entities
 *     It uses session factory to connect to the database
 *     In case of an error, it prints the error message and rolls back the transaction
 */
public class GenericDAO<T> implements DAO<T>{
    private final Class<T> type;
    private final SessionFactory factory;
    private final String tableName;

    public GenericDAO(Class<T> type, String tableName) {
        this.type = type;
        this.tableName = tableName;
        factory = SessionFactoryMaker.getSessionFactory();
    }

    public GenericDAO(Class<T> type, String tableName, SessionFactory factory) {
        this.type = type;
        this.tableName = tableName;
        this.factory = factory;
    }

    public void create(T t) {
        Session session = factory.openSession();
        try (session) {
            session.beginTransaction();
            session.save(t);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
    }

    public T read(int id) {
        T t = null;
        Session session = factory.openSession();
        try (session) {
            t = session.get(type, id);
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        return t;
    }

    public void update(T t) {
        Session session = factory.openSession();
        try (session) {
            session.beginTransaction();
            session.update(t);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
    }

    public void delete(T t) {
        Session session = factory.openSession();
        try (session) {
            session.beginTransaction();
            session.delete(t);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
    }

    public List<T> getAll() {
        List<T> list = null;
        Session session = factory.openSession();
        try (session) {
            list = session.createNativeQuery("SELECT * FROM " + tableName, type).list();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<T> query(String query) {
        List<T> list = null;
        Session session = factory.openSession();
        try (session) {
            list = session.createNativeQuery(query, type).list();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        return list;
    }
}
