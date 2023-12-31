package db.DAO;

import db.HelperMethods;
import db.RandomEntityGenerator;
import db.TestSessionFactoryMaker;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pap.db.DAO.EntityDAO.AddressDAO;
import pap.db.DAO.EntityDAO.BranchDAO;
import pap.db.Entities.Address;
import pap.db.Entities.Branch;


public class TestBranchDAO extends TestGenericDAO<Branch, BranchDAO>{
    private AddressDAO addressDAO = new AddressDAO(factory);

    @Override
    protected Branch createEntity() {
        Address address = RandomEntityGenerator.generateAddress();
        addressDAO.create(address);
        return RandomEntityGenerator.generateBranch(address);
    }

    @Override
    protected BranchDAO createDAO(SessionFactory factory) {
        return new BranchDAO(factory);
    }

    @Test
    public void create() {
        BranchDAO branchDAO = createDAO(factory);
        Branch branch = createEntity();
        branchDAO.create(branch);
        Assertions.assertEquals(HelperMethods.getId(branch), branch.getBranchId());
    }

    @Test
    public void update() {
        BranchDAO branchDAO = createDAO(factory);
        Branch branch = createEntity();
        branchDAO.create(branch);
        branch.setName("Test2");
        branchDAO.update(branch);
        Branch newBranch = branchDAO.read(HelperMethods.getId(branch));
        Assertions.assertEquals(branch.getName(), newBranch.getName());
    }

    @Test
    public void delete() {
        BranchDAO branchDAO = createDAO(factory);
        Branch branch = createEntity();
        branchDAO.create(branch);
        branchDAO.delete(branch);
        Assertions.assertNull(branchDAO.read(HelperMethods.getId(branch)));
    }
}
