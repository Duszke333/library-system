package pap.db.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.Entities.Branch;
import pap.db.SessionFactoryMaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @param <T> Entity class
 *     Generic DAO implementation
 *     This class is used implement methods for other entities
 *     It uses session factory to connect to the database
 *     It handles transactions
 */
public class GenericDAO<T> implements DAO<T>{
    /**
     * @param type
     *     The type of the entity
     * @param factory
     *     The session factory
     * @param tableName
     *     The name of the table used in the database
     */
    private final Class<T> type;
    private final SessionFactory factory;
    private final String tableName;

    /**
     * @param type
     *     The type of the entity
     * @param tableName
     *     The name of the table used in the database
     */
    public GenericDAO(Class<T> type, String tableName) {
        this.type = type;
        this.tableName = tableName;
        factory = SessionFactoryMaker.getSessionFactory();
    }

    /**
     * @param type
     *     The type of the entity
     * @param tableName
     *     The name of the table used in the database
     * @param factory
     *     The session factory (used for testing)
     */
    public GenericDAO(Class<T> type, String tableName, SessionFactory factory) {
        this.type = type;
        this.tableName = tableName;
        this.factory = factory;
    }

    /**
     * @param t The entity to be created
     */
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

    /**
     * @return Entity with the given id
     */
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

    /**
     * @param t The entity to be updated
     */
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

    /**
     * @param t The entity to be deleted
     */
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

    /**
     * @return A list of all entities from the table
     */
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

    /**
     * @param query The query to be executed
     * @return A list of all entities from the table
     */
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

    @Override
    public List<T> query(String sql, Object... params) {
        List<T> list = new ArrayList<T>();
        try (Connection connection = SessionFactoryMaker.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    T t = type.getConstructor().newInstance();
                    List<Object> values = new ArrayList<Object>();
                    for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                        values.add(resultSet.getObject(i + 1));
                    }
                    for (int i = 0; i < values.size(); i++) {
                        t.getClass().getDeclaredFields()[i].setAccessible(true);
                        t.getClass().getDeclaredFields()[i].set(t, values.get(i));
                    }
                    list.add(t);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
}
