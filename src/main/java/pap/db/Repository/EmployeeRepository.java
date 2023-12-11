package pap.db.Repository;

import pap.db.DAO.EmployeeDAO;
import pap.db.Entities.Branch;
import pap.db.Entities.Employee;

import java.util.List;

public class EmployeeRepository implements IEmployeeRepository{
    EmployeeDAO employeeDAO = new EmployeeDAO();

    @Override
    public List<Employee> getByBranchId(int branchId) {
        String sql = "SELECT * FROM view.employees WHERE branch_id = " + branchId;
        return employeeDAO.query(sql);
    }

    public List<Employee> getByBranch(Branch branch) {
        String sql = "SELECT * FROM view.employees WHERE branch_id = " + branch.getBranchId();
        return employeeDAO.query(sql);
    }

    @Override
    public List<Employee> getAllActive() {
        String sql = "SELECT * FROM view.employees WHERE active = true";
        return employeeDAO.query(sql);
    }

    @Override
    public List<Employee> getAllInactive() {
        String sql = "SELECT * FROM view.employees WHERE active = false";
        return employeeDAO.query(sql);
    }

    @Override
    public List<Employee> getByRole(String role) {
        String sql = "SELECT * FROM view.employees WHERE role = '" + role + "'";
        return employeeDAO.query(sql);
    }

    @Override
    public List<Employee> getAll() {
        return employeeDAO.getAll();
    }

    @Override
    public Employee getById(int id) {
        return employeeDAO.read(id);
    }

    @Override
    public void create(Employee entity) {
        employeeDAO.create(entity);
    }

    @Override
    public void update(Employee entity) {
        employeeDAO.update(entity);
    }

    @Override
    public void delete(Employee entity) {
        employeeDAO.delete(entity);
    }

    @Override
    public Employee getByEmail(String email) {
        String sql = "SELECT * FROM view.employees WHERE email = '" + email + "'";
        List<Employee> employees = employeeDAO.query(sql);
        if (employees.size() == 0) {
            return null;
        }
        return employees.get(0);
    }

    @Override
    public Employee getByUsername(String username) {
        String sql = "SELECT * FROM view.employees WHERE username = '" + username + "'";
        List<Employee> employees = employeeDAO.query(sql);
        if (employees.size() == 0) {
            return null;
        }
        return employees.get(0);
    }
}
