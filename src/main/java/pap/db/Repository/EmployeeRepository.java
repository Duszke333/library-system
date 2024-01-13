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
    public List<Employee> getByBranchId(int branchId) throws NullPointerException {
        String sql = "SELECT * FROM pap.employees WHERE branch_id = " + branchId;
        List<Employee> res = employeeDAO.query(sql);
        if (res.size() == 0) {
            throw new NullPointerException("No employees found for branch with id " + branchId);
        }
        return res;
    }

    public List<Employee> getByBranch(Branch branch) throws NullPointerException {
        String sql = "SELECT * FROM pap.employees WHERE branch_id = " + branch.getBranchId();
        List<Employee> res = employeeDAO.query(sql);
        if (res.size() == 0) {
            throw new NullPointerException("No employees found for branch with id " + branch.getBranchId());
        }
        return res;
    }

    @Override
    public List<Employee> getAllActive() throws NullPointerException {
        String sql = "SELECT * FROM pap.employees WHERE active = true";
        List<Employee> res = employeeDAO.query(sql);
        if (res.size() == 0) {
            throw new NullPointerException("No active employees found");
        }
        return res;
    }

    @Override
    public List<Employee> getAllInactive() throws NullPointerException {
        String sql = "SELECT * FROM pap.employees WHERE active = false";
        List<Employee> res = employeeDAO.query(sql);
        if (res.size() == 0) {
            throw new NullPointerException("No inactive employees found");
        }
        return res;
    }

    @Override
    public List<Employee> getByRole(String role) throws NullPointerException {
        String sql = "SELECT * FROM pap.employees WHERE role = '" + role + "'";
        List<Employee> res = employeeDAO.query(sql);
        if (res.size() == 0) {
            throw new NullPointerException("No employees found with role " + role);
        }
        return res;
    }

    @Override
    public Employee getByEmail(String email) throws NullPointerException {
        String sql = "SELECT * FROM pap.employees WHERE email = '" + email + "'";
        List<Employee> employees = employeeDAO.query(sql);
        if (employees.size() == 0) {
            throw new NullPointerException("Employee with email " + email + " not found");
        }
        return employees.get(0);
    }

    @Override
    public Employee getByUsername(String username) throws NullPointerException {
        String sql = "SELECT * FROM pap.employees WHERE username = '" + username + "'";
        List<Employee> employees = employeeDAO.query(sql);
        if (employees.size() == 0) {
            throw new NullPointerException("Employee with username " + username + " not found");
        }
        return employees.get(0);
    }

    @Override
    public Employee getByUserID(int userID) throws NullPointerException {
        String sql = "SELECT * FROM pap.employees WHERE user_id = '" + userID + "'";
        List<Employee> employees = employeeDAO.query(sql);
        if (employees.size() == 0) {
            throw new NullPointerException("Employee with user id " + userID + " not found");
        }
        return employees.get(0);
    }
}
