package pap.db.Repository;

import pap.db.DAO.EntityDAO.AddressDAO;
import pap.db.DAO.EntityDAO.UserDAO;
import pap.db.DAO.GenericDAO;
import pap.db.Entities.Address;
import pap.db.Entities.User;
import pap.db.Repository.Interface.IAddressRepository;

import java.util.List;

public class AddressRepository extends GenericRepository<Address> implements IAddressRepository {
    private UserDAO userDAO = new UserDAO();
    private AddressDAO addressDAO = new AddressDAO();

    public AddressRepository() {
        super(Address.class, new AddressDAO());
    }

    @Override
    public Address getByUser(User user) {
        int addressId = user.getAddressId();
        Address address = addressDAO.read(addressId);
        return address;
    }

    @Override
    public Address getByUserId(int userId) {
        User user = userDAO.read(userId);
        int addressId = user.getAddressId();
        Address address = addressDAO.read(addressId);
        return address;
    }
}
