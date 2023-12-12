package db.DAO;

import db.TestSessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pap.db.DAO.AddressDAO;
import pap.db.Entities.Address;

import java.util.List;


public class TestAddressDAO {
    private SessionFactory factory = TestSessionFactoryMaker.getSessionFactory();

    private int getId(Address address) {
        int id;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            List<Address> addresses = session.createNativeQuery(
                    "SELECT * FROM pap.addresses " +
                            "WHERE street = '" + address.getStreet() + "'" +
                            "AND flat_number = '" + address.getFlatNumber() + "' AND house_number = '" + address.getHouseNumber() + "'"
                    , Address.class).list();
            if (addresses.isEmpty()) {
                return -1;
            }
            id = addresses.get(0).getAddressId();
            session.getTransaction().commit();
        }
        return id;
    }

    @Before
    public void setup() {

        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE pap.ADDRESSES CASCADE").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @After
    public void teardown() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE pap.ADDRESSES CASCADE").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void create() {
        AddressDAO addressDAO = new AddressDAO(factory);

        Address address = new Address();
        address.setStreet("Test");
        address.setCountry("Test");
        address.setCity("Test");
        address.setPostalCode("Test");
        address.setHouseNumber("1");
        address.setFlatNumber("1");

        addressDAO.create(address);

        Assertions.assertEquals(getId(address), address.getAddressId());
    }

    @Test
    public void update() {
        AddressDAO addressDAO = new AddressDAO(factory);

        Address address = new Address();
        address.setStreet("Test");
        address.setCountry("Test");
        address.setCity("Test");
        address.setPostalCode("Test");
        address.setHouseNumber("1");
        address.setFlatNumber("1");

        addressDAO.create(address);

        address.setStreet("Test2");
        addressDAO.update(address);

        Assertions.assertEquals(addressDAO.read(getId(address)).getStreet(), address.getStreet());
    }

    @Test
    public void delete() {
        AddressDAO addressDAO = new AddressDAO(factory);

        Address address = new Address();
        address.setStreet("Test");
        address.setCountry("Test");
        address.setCity("Test");
        address.setPostalCode("Test");
        address.setHouseNumber("1");
        address.setFlatNumber("1");

        addressDAO.create(address);

        addressDAO.delete(address);

        Assertions.assertNull(addressDAO.read(getId(address)));
    }
}