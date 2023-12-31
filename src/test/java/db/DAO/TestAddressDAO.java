package db.DAO;

import db.HelperMethods;
import db.RandomEntityGenerator;
import db.TestSessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pap.db.DAO.EntityDAO.AddressDAO;
import pap.db.Entities.Address;


public class TestAddressDAO extends TestGenericDAO<Address, AddressDAO>{
    @Override
    protected Address createEntity() {
        return RandomEntityGenerator.generateAddress();
    }

    @Override
    protected AddressDAO createDAO(SessionFactory factory) {
        return new AddressDAO(factory);
    }

    @Test
    public void create() {
        AddressDAO addressDAO = createDAO(factory);
        Address address = createEntity();
        addressDAO.create(address);
        Assertions.assertEquals(HelperMethods.getId(address), address.getAddressId());
    }

    @Test
    public void update() {
        AddressDAO addressDAO = createDAO(factory);
        Address address = createEntity();
        addressDAO.create(address);
        address.setCity("Test2");
        addressDAO.update(address);
        Address newAddress = addressDAO.read(HelperMethods.getId(address));
        Assertions.assertEquals(address.getCity(), newAddress.getCity());
    }

    @Test
    public void delete() {
        AddressDAO addressDAO = createDAO(factory);
        Address address = createEntity();
        addressDAO.create(address);
        addressDAO.delete(address);
        Assertions.assertNull(addressDAO.read(HelperMethods.getId(address)));
    }
}