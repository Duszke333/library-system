package pap.db.DAO;

import pap.db.Entities.Address;
import pap.db.SessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class AddressDAO implements DAO<Address> {
    SessionFactory factory = SessionFactoryMaker.getSessionFactory();

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

    public List<Address> getAll() {
        List<Address> addresses = null;
        try (Session session = factory.openSession()) {
            addresses = session.createNativeQuery("SELECT * FROM pap.addresses").list();
            return addresses;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return addresses;
    }

    public List<Address> query(String sql) {
        List<Address> addresses = null;
        try (Session session = factory.openSession()) {
            addresses = session.createNativeQuery(sql).list();
            return addresses;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return addresses;
    }
}
