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
    public Address getByUser(User user) throws NullPointerException {
        int addressId = user.getAddressId();
        Address address = addressDAO.read(addressId);
        if (address == null) {
            throw new NullPointerException("Address with id " + addressId + " not found");
        }
        return address;
    }

    @Override
    public Address getByUserId(int userId) throws NullPointerException {
        User user = userDAO.read(userId);
        if (user == null) {
            throw new NullPointerException("User with id " + userId + " not found");
        }
        int addressId = user.getAddressId();
        Address address = addressDAO.read(addressId);
        if (address == null) {
            throw new NullPointerException("Address with id " + addressId + " not found");
        }
        return address;
    }
}
