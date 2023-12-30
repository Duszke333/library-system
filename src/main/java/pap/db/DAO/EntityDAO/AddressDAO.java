package pap.db.DAO.EntityDAO;

import pap.db.DAO.GenericDAO;
import pap.db.Entities.Address;
import pap.db.SessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class AddressDAO extends GenericDAO<Address> {

    public AddressDAO() {
        super(Address.class, "pap.addresses");
    }

    public AddressDAO(SessionFactory factory) {
        super(Address.class, "pap.addresses", factory);
    }

}
