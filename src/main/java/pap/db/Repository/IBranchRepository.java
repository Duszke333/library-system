package pap.db.Repository;

import pap.db.Entities.Address;
import pap.db.Entities.Branch;
import pap.db.Entities.Employee;

public interface IBranchRepository extends IRepository<Branch>{
    Branch getByEmployeeId(int employeeId);
    Branch getByEmployee(Employee employee);
    Branch getByAddressId(int addressId);
    Branch getByAddress(Address address);
}
