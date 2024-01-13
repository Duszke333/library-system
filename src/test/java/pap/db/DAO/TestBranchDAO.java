package pap.db.DAO;

import pap.db.RandomEntityGenerator;
import org.hibernate.SessionFactory;
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

    @Override
    protected int getId(Branch branch) {
        int id;
        try (org.hibernate.Session session = factory.openSession()) {
            session.beginTransaction();
            java.util.List<Branch> branches = session.createNativeQuery(
                    "SELECT * FROM pap.branches " +
                            "WHERE branch_name = '" + branch.getName() + "'"
                    , Branch.class).list();
            if (branches.isEmpty()) {
                return -1;
            }
            id = branches.get(0).getBranchId();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error while getting id of branch " + branch.getName());
            return -1;
        }
        return id;
    }

    @Test
    public void create() {
        BranchDAO branchDAO = createDAO(factory);
        Branch branch = createEntity();
        branchDAO.create(branch);
        Assertions.assertEquals(getId(branch), branch.getBranchId());
    }

    @Test
    public void update() {
        BranchDAO branchDAO = createDAO(factory);
        Branch branch = createEntity();
        branchDAO.create(branch);
        branch.setName("Test2");
        branchDAO.update(branch);
        Branch newBranch = branchDAO.read(getId(branch));
        Assertions.assertEquals(branch.getName(), newBranch.getName());
    }

    @Test
    public void delete() {
        BranchDAO branchDAO = createDAO(factory);
        Branch branch = createEntity();
        branchDAO.create(branch);
        branchDAO.delete(branch);
        Assertions.assertNull(branchDAO.read(getId(branch)));
    }
}
