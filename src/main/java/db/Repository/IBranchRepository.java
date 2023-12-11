package db.Repository;

import db.Entities.Address;
import db.Entities.Branch;
import db.Entities.Employee;

public interface IBranchRepository extends IRepository<Branch>{
    Branch getByEmployeeId(int employeeId);
    Branch getByEmployee(Employee employee);
    Branch getByAddressId(int addressId);
    Branch getByAddress(Address address);
}
