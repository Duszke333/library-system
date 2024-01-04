package pap.db.Repository;

import pap.db.DAO.EntityDAO.EmployeeDAO;
import pap.db.DAO.GenericDAO;
import pap.db.Entities.Branch;
import pap.db.Entities.Employee;
import pap.db.Repository.Interface.IEmployeeRepository;

import java.util.List;

public class EmployeeRepository extends GenericRepository<Employee> implements IEmployeeRepository {
    EmployeeDAO employeeDAO = new EmployeeDAO();

    public EmployeeRepository() {
        super(Employee.class, new EmployeeDAO());
    }

    @Override
    public List<Employee> getByBranchId(int branchId) {
        String sql = "SELECT * FROM pap.employees WHERE branch_id = " + branchId;
        return employeeDAO.query(sql);
    }

    public List<Employee> getByBranch(Branch branch) {
        String sql = "SELECT * FROM pap.employees WHERE branch_id = " + branch.getBranchId();
        return employeeDAO.query(sql);
    }

    @Override
    public List<Employee> getAllActive() {
        String sql = "SELECT * FROM pap.employees WHERE active = true";
        return employeeDAO.query(sql);
    }

    @Override
    public List<Employee> getAllInactive() {
        String sql = "SELECT * FROM pap.employees WHERE active = false";
        return employeeDAO.query(sql);
    }

    @Override
    public List<Employee> getByRole(String role) {
        String sql = "SELECT * FROM pap.employees WHERE role = '" + role + "'";
        return employeeDAO.query(sql);
    }

    @Override
    public Employee getByEmail(String email) {
        String sql = "SELECT * FROM pap.employees WHERE email = '" + email + "'";
        List<Employee> employees = employeeDAO.query(sql);
        if (employees.size() == 0) {
            return null;
        }
        return employees.get(0);
    }

    @Override
    public Employee getByUsername(String username) {
        String sql = "SELECT * FROM pap.employees WHERE username = '" + username + "'";
        List<Employee> employees = employeeDAO.query(sql);
        if (employees.size() == 0) {
            return null;
        }
        return employees.get(0);
    }

    @Override
    public Employee getByUserID(int userID) {
        String sql = "SELECT * FROM pap.employees WHERE user_id = '" + userID + "'";
        List<Employee> employees = employeeDAO.query(sql);
        if (employees.size() == 0) {
            return null;
        }
        return employees.get(0);
    }
}
