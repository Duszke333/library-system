package db.Repository;

import db.Entities.Branch;
import db.Entities.Employee;
import java.util.List;

public interface IEmployeeRepository extends IRepository<Employee>{
    List<Employee> getByBranchId(int branchId);
    List<Employee> getByBranch(Branch branch);
    List<Employee> getAllActive();
    List<Employee> getAllInactive();
    List<Employee> getByRole(String role);
    Employee getById(int id);
    Employee getByEmail(String email);
    Employee getByUsername(String username);
 }
