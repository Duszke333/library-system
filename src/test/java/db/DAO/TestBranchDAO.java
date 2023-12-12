package db.DAO;

import db.TestSessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pap.db.DAO.BranchDAO;
import pap.db.Entities.Branch;

import java.util.List;


public class TestBranchDAO {
    private SessionFactory factory = TestSessionFactoryMaker.getSessionFactory();

    private int getId(Branch branch) {
        int id;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            List<Branch> branches = session.createNativeQuery("SELECT * FROM pap.branches WHERE name = '" + branch.getName() + "'", Branch.class).list();
            if (branches.isEmpty()) {
                return -1;
            }
            id = branches.get(0).getBranchId();
            session.getTransaction().commit();
        }
        return id;
    }

    @Before
    public void setup() {

        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE pap.BRANCHES CASCADE").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @After
    public void teardown() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE pap.BRANCHES CASCADE").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void create() {
        BranchDAO branchDAO = new BranchDAO(factory);

        Branch branch = new Branch();
        branch.setName("Test");
        branch.setAddressId(1);

        branchDAO.create(branch);

        Branch newBranch = branchDAO.read(getId(branch));
        Assertions.assertEquals(getId(branch), newBranch.getBranchId());
    }

    @Test
    public void update() {
        BranchDAO branchDAO = new BranchDAO(factory);

        Branch branch = new Branch();
        branch.setName("Test");
        branch.setAddressId(1);
        branchDAO.create(branch);

        branch.setName("Test2");
        branchDAO.update(branch);

        Assertions.assertEquals(branchDAO.read(getId(branch)).getName(), branch.getName());
    }

    @Test
    public void delete() {
        BranchDAO branchDAO = new BranchDAO(factory);

        Branch branch = new Branch();
        branch.setName("Test");
        branch.setAddressId(1);
        branchDAO.create(branch);

        branchDAO.delete(branch);

        Assertions.assertNull(branchDAO.read(getId(branch)));
    }
}
