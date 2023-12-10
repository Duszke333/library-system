package db.Repository.Interface;

import db.Entities.Address;
import db.Entities.User;

public interface IAddressRepository extends IRepository<Address> {
    Address getByUser(User user);
    Address getByUserId(int userId);
}
