package pap.db.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.Entities.Branch;
import pap.db.SessionFactoryMaker;

import java.util.List;

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
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(t);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public T read(int id) {
        T t = null;
        try (Session session = factory.openSession()) {
            t = session.get(type, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return t;
    }

    public void update(T t) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.update(t);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(T t) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.delete(t);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<T> getAll() {
        List<T> list = null;
        try (Session session = factory.openSession()) {
            list = session.createNativeQuery("SELECT * FROM " + tableName, type).list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<T> query(String query) {
        List<T> list = null;
        try (Session session = factory.openSession()) {
            list = session.createNativeQuery(query, type).list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
}
