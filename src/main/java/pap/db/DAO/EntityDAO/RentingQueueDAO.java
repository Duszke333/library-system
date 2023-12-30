package pap.db.DAO.EntityDAO;

import org.hibernate.SessionFactory;
import pap.db.DAO.GenericDAO;
import pap.db.Entities.RentingQueue;

public class RentingQueueDAO extends GenericDAO<RentingQueue> {

        public RentingQueueDAO() {
            super(RentingQueue.class, "pap.renting_queue");
        }

        public RentingQueueDAO(SessionFactory factory) {
            super(RentingQueue.class, "pap.renting_queue", factory);
        }
}
