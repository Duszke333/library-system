package pap.db.Repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pap.db.Entities.Address;

import java.util.List;

class AddressRepositoryTest {
    AddressRepository addressRepository = new AddressRepository();

    @Test
    void getAll() {
        List<Address> addresses = addressRepository.getAll();
        Assertions.assertNotNull(addresses);
    }

    @Test
    void getById() {
        Address address = addressRepository.getById(1);
        Assertions.assertNotNull(address);
    }
}