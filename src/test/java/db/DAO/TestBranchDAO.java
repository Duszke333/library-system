package db.DAO;

import db.HelperMethods;
import db.TestSessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pap.db.DAO.AddressDAO;
import pap.db.DAO.BranchDAO;
import pap.db.Entities.Address;
import pap.db.Entities.Branch;

import java.util.List;


public class TestBranchDAO {
    private SessionFactory factory = TestSessionFactoryMaker.getSessionFactory();

    @Before
    public void setup() {
        HelperMethods.clearTable("BRANCHES");
    }

    @After
    public void teardown() {
        HelperMethods.clearTable("BRANCHES");
    }

    @Test
    public void create() {
        BranchDAO branchDAO = new BranchDAO(factory);
        AddressDAO addressDAO = new AddressDAO(factory);

        Address address = new Address();
        address.setStreet("Test");
        address.setCountry("Test");
        address.setCity("Test");
        address.setPostalCode("Test");
        address.setHouseNumber("1");
        address.setFlatNumber("1");

        addressDAO.create(address);

        Branch branch = new Branch();
        branch.setName("Test");
        branch.setAddressId(HelperMethods.getId(address));

        branchDAO.create(branch);

        Branch newBranch = branchDAO.read(HelperMethods.getId(branch));
        Assertions.assertEquals(HelperMethods.getId(branch), newBranch.getBranchId());
    }

    @Test
    public void update() {
        BranchDAO branchDAO = new BranchDAO(factory);
        AddressDAO addressDAO = new AddressDAO(factory);

        Address address = new Address();
        address.setStreet("Test");
        address.setCountry("Test");
        address.setCity("Test");
        address.setPostalCode("Test");
        address.setHouseNumber("1");
        address.setFlatNumber("1");

        addressDAO.create(address);

        Branch branch = new Branch();
        branch.setName("Test");
        branch.setAddressId(HelperMethods.getId(address));
        branchDAO.create(branch);

        branch.setName("Test2");
        branchDAO.update(branch);

        Assertions.assertEquals(branchDAO.read(HelperMethods.getId(branch)).getName(), branch.getName());
    }

    @Test
    public void delete() {
        BranchDAO branchDAO = new BranchDAO(factory);
        AddressDAO addressDAO = new AddressDAO(factory);

        Address address = new Address();
        address.setStreet("Test");
        address.setCountry("Test");
        address.setCity("Test");
        address.setPostalCode("Test");
        address.setHouseNumber("1");
        address.setFlatNumber("1");

        addressDAO.create(address);

        Branch branch = new Branch();
        branch.setName("Test");
        branch.setAddressId(HelperMethods.getId(address));
        branchDAO.create(branch);

        branchDAO.delete(branch);

        Assertions.assertNull(branchDAO.read(HelperMethods.getId(branch)));
    }
}
