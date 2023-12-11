package pap.db.Repository.Interface;


import pap.db.Entities.Address;
import pap.db.Entities.User;

public interface IAddressRepository extends IRepository<Address> {
    Address getByUser(User user);
    Address getByUserId(int userId);
}
