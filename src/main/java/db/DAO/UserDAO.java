package db.DAO;

import db.Entities.User;
import db.SessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class UserDAO implements DAO<User>{
    SessionFactory factory = SessionFactoryMaker.getSessionFactory();

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
