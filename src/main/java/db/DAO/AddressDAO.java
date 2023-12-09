package db.DAO;

import db.Entities.Address;
import db.SessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class AddressDAO implements DAO<Address> {
    SessionFactoryMaker sessionFactoryMaker = new SessionFactoryMaker();
    SessionFactory factory = sessionFactoryMaker.getSessionFactory();

    public void create(Address address) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(address);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Address read(int id) {
        Address address = null;
        try (Session session = factory.openSession()) {
            address = session.get(Address.class, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return address;
    }

    public void update(Address address) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.update(address);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(Address address) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.delete(address);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
