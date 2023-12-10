package db.Repository;

import db.DAO.AddressDAO;
import db.DAO.BranchDAO;
import db.DAO.EmployeeDAO;
import db.Entities.Address;
import db.Entities.Branch;
import db.Entities.Employee;
import db.Repository.Interface.IBranchRepository;

import java.util.List;

public class BranchRepository implements IBranchRepository {
    BranchDAO branchDAO = new BranchDAO();
    EmployeeDAO employeeDAO = new EmployeeDAO();
    AddressDAO addressDAO = new AddressDAO();

    @Override
    public Branch getByEmployeeId(int employeeId) {
        Employee employee = employeeDAO.read(employeeId);
        Branch branch = branchDAO.read(employee.getBranchId());
        return branch;
    }

    @Override
    public Branch getByEmployee(Employee employee) {
        Branch branch = branchDAO.read(employee.getBranchId());
        return branch;
    }

    @Override
    public Branch getByAddressId(int addressId) {
        String sql = "SELECT * FROM pap.branches WHERE address_id = " + addressId;
        return branchDAO.query(sql).get(0);
    }

    @Override
    public Branch getByAddress(Address address) {
        String sql = "SELECT * FROM pap.branches WHERE address_id = " + address.getAddressId();
        return branchDAO.query(sql).get(0);
    }

    @Override
    public List<Branch> getAll() {
        return branchDAO.getAll();
    }

    @Override
    public Branch getById(int id) {
        return branchDAO.read(id);
    }

    @Override
    public void create(Branch entity) {
        branchDAO.create(entity);
    }

    @Override
    public void update(Branch entity) {
        branchDAO.update(entity);
    }

    @Override
    public void delete(Branch entity) {
        branchDAO.delete(entity);
    }
}
