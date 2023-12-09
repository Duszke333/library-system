package db.Repository;

import db.Entities.Address;
import db.Entities.User;

public interface AddressRepository extends Repository<Address>{
    Address getByUser(User user);
    Address getByUserId(int userId);
}
