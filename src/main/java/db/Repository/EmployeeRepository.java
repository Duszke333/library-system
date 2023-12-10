package db.Repository;

import db.Entities.Employee;


import java.util.List;

public interface EmployeeRepository extends Repository<Employee> {
    
    Employee getByEmail(String email);
    Employee getByEmployeeName(String EmployeeName);
    List<Employee> getByBranch(int branchId);
    List<Employee> getAllActive();
    List<Employee> getAllInactive();
    List<Employee> getByRole(String role);
}
