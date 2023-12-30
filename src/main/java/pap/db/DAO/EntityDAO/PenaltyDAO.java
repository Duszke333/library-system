package pap.db.DAO.EntityDAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.DAO.GenericDAO;
import pap.db.Entities.Penalty;
import pap.db.SessionFactoryMaker;

import java.util.List;

public class PenaltyDAO extends GenericDAO<Penalty> {

    public PenaltyDAO() {
        super(Penalty.class, "pap.penalties");
    }

    public PenaltyDAO(SessionFactory factory) {
        super(Penalty.class, "pap.penalties", factory);
    }
}
