package pap.db.DAO;

import pap.db.RandomEntityGenerator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pap.db.DAO.EntityDAO.AddressDAO;
import pap.db.Entities.Address;

import java.util.List;


public class TestAddressDAO extends TestGenericDAO<Address, AddressDAO>{
    @Override
    protected Address createEntity() {
        return RandomEntityGenerator.generateAddress();
    }

    @Override
    protected AddressDAO createDAO(SessionFactory factory) {
        return new AddressDAO(factory);
    }

    @Override
    protected int getId(Address address) {
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
        } catch (Exception e) {
            System.out.println("Error while getting id of address " + address.getStreet());
            return -1;
        }
        return id;
    }

    @Test
    public void create() {
        AddressDAO addressDAO = createDAO(factory);
        Address address = createEntity();
        addressDAO.create(address);
        Assertions.assertEquals(getId(address), address.getAddressId());
    }

    @Test
    public void update() {
        AddressDAO addressDAO = createDAO(factory);
        Address address = createEntity();
        addressDAO.create(address);
        address.setCity("Test2");
        addressDAO.update(address);
        Address newAddress = addressDAO.read(getId(address));
        Assertions.assertEquals(address.getCity(), newAddress.getCity());
    }

    @Test
    public void delete() {
        AddressDAO addressDAO = createDAO(factory);
        Address address = createEntity();
        addressDAO.create(address);
        addressDAO.delete(address);
        Assertions.assertNull(addressDAO.read(getId(address)));
    }
}