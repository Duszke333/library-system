package pap.helpers;

import lombok.Getter;
import pap.db.Entities.Address;
import pap.db.Entities.Branch;
import pap.db.Repository.AddressRepository;
import pap.db.Repository.BranchRepository;

import java.util.List;

@Getter
public class BranchRecord {
    /**
     * A class that represents a record of the TableView in browse-branches page.
     */
    private int branchId;
    private String name;
    private String address;

    public BranchRecord(Branch branch) {
        this.branchId = branch.getBranchId();
        this.name = branch.getName();
        Address address = new AddressRepository().getById(branch.getAddressId());
        if (!address.getFlatNumber().isEmpty()) {
            this.address = address.getCountry() + ", " + address.getCity() + "(" + address.getPostalCode() + "), " + address.getStreet() + " " + address.getHouseNumber() + "/" + address.getFlatNumber();
        } else {
            this.address = address.getCountry() + ", " + address.getCity() + "(" + address.getPostalCode() + "), " + address.getStreet() + " " + address.getHouseNumber();
        }
    }

    public static List<BranchRecord> getAll() {
        List<Branch> branches = new BranchRepository().getAll();
        List<BranchRecord> records = new java.util.ArrayList<>();
        for (Branch branch : branches) {
            records.add(new BranchRecord(branch));
        }
        return records;
    }
}
