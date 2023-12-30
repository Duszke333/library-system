package pap.db.DAO.EntityDAO;

import pap.db.DAO.GenericDAO;
import pap.db.Entities.Branch;
import pap.db.SessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class BranchDAO extends GenericDAO<Branch> {

    public BranchDAO() {
        super(Branch.class, "pap.branches");
    }

    public BranchDAO(SessionFactory factory) {
        super(Branch.class, "pap.branches", factory);
    }
}
