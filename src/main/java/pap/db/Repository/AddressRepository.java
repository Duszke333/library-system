package pap.db.Repository;

import pap.db.DAO.AddressDAO;
import pap.db.DAO.UserDAO;
import pap.db.Entities.Address;
import pap.db.Entities.User;

import java.util.List;

public class AddressRepository implements IAddressRepository{
    private UserDAO userDAO = new UserDAO();
    private AddressDAO addressDAO = new AddressDAO();

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

    @Override
    public List<Address> getAll() {
        return addressDAO.getAll();
    }

    @Override
    public Address getById(int id) {
        return addressDAO.read(id);
    }

    @Override
    public void create(Address entity) {
        addressDAO.create(entity);
    }

    @Override
    public void update(Address entity) {
        addressDAO.update(entity);
    }

    @Override
    public void delete(Address entity) {
        addressDAO.delete(entity);
    }
}
