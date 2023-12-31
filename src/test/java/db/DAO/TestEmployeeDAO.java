package db.DAO;


import db.HelperMethods;
import db.RandomEntityGenerator;
import db.TestSessionFactoryMaker;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pap.db.DAO.EntityDAO.AddressDAO;
import pap.db.DAO.EntityDAO.BranchDAO;
import pap.db.DAO.EntityDAO.EmployeeDAO;
import pap.db.DAO.EntityDAO.UserDAO;
import pap.db.Entities.Address;
import pap.db.Entities.Branch;
import pap.db.Entities.Employee;
import pap.db.Entities.User;

public class TestEmployeeDAO extends TestGenericDAO<Employee, EmployeeDAO>{

    private AddressDAO addressDAO = new AddressDAO(factory);
    private UserDAO userDAO = new UserDAO(factory);
    private BranchDAO branchDAO = new BranchDAO(factory);

    @Override
    protected Employee createEntity() {
        Address address = RandomEntityGenerator.generateAddress();
        addressDAO.create(address);
        User user = RandomEntityGenerator.generateUser(address);
        userDAO.create(user);
        Branch branch = RandomEntityGenerator.generateBranch(address);
        branchDAO.create(branch);
        Employee employee = RandomEntityGenerator.generateEmployee(user, branch);
        return employee;
    }

    @Override
    protected EmployeeDAO createDAO(SessionFactory factory) {
        return new EmployeeDAO(factory);
    }

    @Test
    public void create() {
        EmployeeDAO employeeDAO = createDAO(factory);
        Employee employee = createEntity();
        employeeDAO.create(employee);
        Assertions.assertEquals(HelperMethods.getId(employee), employee.getEmployeeId());
    }

    @Test
    public void update() {
        EmployeeDAO employeeDAO = createDAO(factory);
        Employee employee = createEntity();
        employeeDAO.create(employee);
        employee.setUsername("Test2");
        employeeDAO.update(employee);
        Employee newEmployee = employeeDAO.read(HelperMethods.getId(employee));
        Assertions.assertEquals(employee.getUsername(), newEmployee.getUsername());
    }

    @Test
    public void delete() {
        EmployeeDAO employeeDAO = createDAO(factory);
        Employee employee = createEntity();
        employeeDAO.create(employee);
        employeeDAO.delete(employee);
        Assertions.assertNull(employeeDAO.read(HelperMethods.getId(employee)));
    }
}
