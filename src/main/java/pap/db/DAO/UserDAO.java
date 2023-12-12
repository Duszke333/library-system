package pap.db.DAO;

import pap.db.Entities.User;
import pap.db.SessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDAO implements DAO<User>{
    private SessionFactory factory;

    public UserDAO() {
        factory = SessionFactoryMaker.getSessionFactory();
    }

    public UserDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public void create(User user) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public User read(int id) {
        User user = null;
        try (Session session = factory.openSession()) {
            user = session.get(User.class, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public void update(User user) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(User user) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAll() {
        List<User> users = null;
        try (Session session = factory.openSession()) {
            users = session.createNativeQuery("SELECT * FROM pap.users", User.class).list();
            return users;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public List<User> query(String sql) {
        List<User> users = null;
        try (Session session = factory.openSession()) {
            users = session.createNativeQuery(sql, User.class).list();
            return users;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return users;
    }
}
