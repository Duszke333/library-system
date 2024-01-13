package pap.db.Repository;

import pap.db.DAO.EntityDAO.AddressDAO;
import pap.db.DAO.EntityDAO.BranchDAO;
import pap.db.DAO.EntityDAO.EmployeeDAO;
import pap.db.DAO.GenericDAO;
import pap.db.Entities.Address;
import pap.db.Entities.Branch;
import pap.db.Entities.Employee;
import pap.db.Repository.Interface.IBranchRepository;

import java.util.List;

public class BranchRepository extends GenericRepository<Branch> implements IBranchRepository {
    BranchDAO branchDAO = new BranchDAO();
    EmployeeDAO employeeDAO = new EmployeeDAO();
    AddressDAO addressDAO = new AddressDAO();

    public BranchRepository() {
        super(Branch.class, new BranchDAO());
    }

    @Override
    public Branch getByEmployeeId(int employeeId) throws NullPointerException {
        Employee employee = employeeDAO.read(employeeId);
        Branch branch = branchDAO.read(employee.getBranchId());
        if (branch == null) {
            throw new NullPointerException("Branch with id " + employee.getBranchId() + " not found");
        }
        return branch;
    }

    @Override
    public Branch getByEmployee(Employee employee) throws NullPointerException {
        Branch branch = branchDAO.read(employee.getBranchId());
        if (branch == null) {
            throw new NullPointerException("Branch with id " + employee.getBranchId() + " not found");
        }
        return branch;
    }

    @Override
    public Branch getByAddressId(int addressId) throws NullPointerException {
        String sql = "SELECT * FROM pap.branches WHERE address_id = " + addressId;
        List<Branch> branch = branchDAO.query(sql);
        if (branch.size() == 0) {
            throw new NullPointerException("Branch with address id " + addressId + " not found");
        }
        return branch.get(0);
    }

    @Override
    public Branch getByAddress(Address address) throws NullPointerException {
        String sql = "SELECT * FROM pap.branches WHERE address_id = " + address.getAddressId();
        List<Branch> branch = branchDAO.query(sql);
        if (branch.size() == 0) {
            throw new NullPointerException("Branch with address id " + address.getAddressId() + " not found");
        }
        return branch.get(0);
    }

    @Override
    public Branch getByBranchName(String name) throws NullPointerException {
        String sql = "SELECT * FROM pap.branches WHERE branch_name = '" + name + "'";
        List<Branch> branches = branchDAO.query(sql);
        if (branches.size() == 0) {
            throw new NullPointerException("Branch with name " + name + " not found");
        }
        return branches.get(0);
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
