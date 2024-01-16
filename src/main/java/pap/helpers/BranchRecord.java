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

    /**
     * Constructor for the BranchRecord class.
     * @param branch Branch object that is used to create the record.
     */
    public BranchRecord(Branch branch) {
        this.branchId = branch.getBranchId();
        this.name = branch.getName();
        Address address = new AddressRepository().getById(branch.getAddressId());

        // make the address look clean
        if (!address.getFlatNumber().isEmpty()) {
            this.address = address.getCountry() + ", " + address.getCity() + "(" + address.getPostalCode() + "), " + address.getStreet() + " " + address.getHouseNumber() + "/" + address.getFlatNumber();
        } else {
            this.address = address.getCountry() + ", " + address.getCity() + "(" + address.getPostalCode() + "), " + address.getStreet() + " " + address.getHouseNumber();
        }
    }

    /**
     * A method that returns a list of all branches.
     * @return list of all branches
     */
    public static List<BranchRecord> getAll() {
        // get all the branches
        List<Branch> branches = new BranchRepository().getAll();

        // create a list of records from the branches
        List<BranchRecord> records = new java.util.ArrayList<>();
        for (Branch branch : branches) {
            records.add(new BranchRecord(branch));
        }
        return records;
    }
}
