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

    /**
     * Constructor for the BranchEmployeeRecord class.
     * @param emp Employee object that is used to create the record.
     */
    public BranchEmployeeRecord(Employee emp) {
        this.employeeId = emp.getEmployeeId();
        User empUserAcc = new UserRepository().getById(emp.getUserID());
        this.firstName = empUserAcc.getFirstName();
        this.lastName = empUserAcc.getLastName();
        this.role = emp.getRole();
    }

    /**
     * A method that returns a list of all employees that work in a given branch.
     * @param branchId id of the branch
     * @return list of all employees that work in a given branch
     */
    public static List<BranchEmployeeRecord> getBranchEmployees(int branchId) {
        // get all the employees that work in the given branch
        List<Employee> employees = new pap.db.Repository.EmployeeRepository().getByBranchId(branchId);

        // create a list of records from the employees
        List<BranchEmployeeRecord> records = new java.util.ArrayList<>();
        for (Employee employee : employees) {
            if (employee.isActive()) {
                records.add(new BranchEmployeeRecord(employee));
            }
        }
        return records;
    }
}
