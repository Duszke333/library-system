package pap.helpers;

import lombok.Getter;
import pap.db.Entities.Employee;
import pap.db.Entities.User;
import pap.db.Repository.UserRepository;

import java.util.List;

@Getter
public class BranchEmployeeRecord {
    /**
     * A class that represents a record of the TableView in browse-branch-employees page.
     */
    private int employeeId;
    private String firstName;
    private String lastName;
    private String role;

    public BranchEmployeeRecord(Employee emp) {
        this.employeeId = emp.getEmployeeId();
        User empUserAcc = new UserRepository().getById(emp.getUserID());
        this.firstName = empUserAcc.getFirstName();
        this.lastName = empUserAcc.getLastName();
        this.role = emp.getRole();
    }

    public static List<BranchEmployeeRecord> getBranchEmployees(int branchId) {
        List<Employee> employees = new pap.db.Repository.EmployeeRepository().getByBranchId(branchId);
        List<BranchEmployeeRecord> records = new java.util.ArrayList<>();
        for (Employee employee : employees) {
            if (employee.isActive()) {
                records.add(new BranchEmployeeRecord(employee));
            }
        }
        return records;
    }
}
