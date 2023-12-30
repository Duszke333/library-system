package pap.db.DAO.EntityDAO;

import pap.db.DAO.GenericDAO;
import pap.db.Entities.ReadList;

public class ReadListDAO extends GenericDAO<ReadList> {

    public ReadListDAO() {
        super(ReadList.class, "pap.read_lists");
    }

    public ReadListDAO(org.hibernate.SessionFactory factory) {
        super(ReadList.class, "pap.read_lists", factory);
    }
}
