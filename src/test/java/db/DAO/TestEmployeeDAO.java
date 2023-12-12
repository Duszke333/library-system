package db.DAO;


import db.HelperMethods;
import db.TestSessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pap.db.DAO.AddressDAO;
import pap.db.DAO.BranchDAO;
import pap.db.DAO.EmployeeDAO;
import pap.db.DAO.UserDAO;
import pap.db.Entities.Address;
import pap.db.Entities.Branch;
import pap.db.Entities.Employee;
import pap.db.Entities.User;

import java.util.List;

public class TestEmployeeDAO {
    private SessionFactory factory = TestSessionFactoryMaker.getSessionFactory();

    @Before
    public void setup() {
        HelperMethods.clearTable("EMPLOYEES");
    }

    @After
    public void teardown() {
        HelperMethods.clearTable("EMPLOYEES");
    }

    @Test
    public void create() {
        EmployeeDAO employeeDAO = new EmployeeDAO(factory);
        AddressDAO addressDAO = new AddressDAO(factory);
        UserDAO userDAO = new UserDAO(factory);
        BranchDAO branchDAO = new BranchDAO(factory);

        Address address = new Address();
        address.setStreet("Test");
        address.setCountry("Test");
        address.setCity("Test");
        address.setPostalCode("Test");
        address.setHouseNumber("1");
        address.setFlatNumber("1");

        addressDAO.create(address);

        Branch branch = new Branch();
        branch.setName("Test");
        branch.setAddressId(HelperMethods.getId(address));
        branchDAO.create(branch);

        branchDAO.delete(branch);

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEmail("test@test");
        user.setPasswordHash("test");
        user.setPasswordSalt("test");
        user.setDateCreated(new java.sql.Date(System.currentTimeMillis()));
        user.setActive(true);
        user.setHasUnpaidPenalty(false);
        user.setAddressId(HelperMethods.getId(address));

        userDAO.create(user);

        Employee employee = new Employee();
        employee.setUsername("Test");
        employee.setPasswordHash("Test");
        employee.setUserID(HelperMethods.getId(user));
        employee.setRole("Test");
        employee.setBranchId(HelperMethods.getId(branch));
        employee.setActive(true);

        employeeDAO.create(employee);

        Employee newEmployee = employeeDAO.read(HelperMethods.getId(employee));
        Assertions.assertEquals(HelperMethods.getId(employee), newEmployee.getEmployeeId());
    }

    @Test
    public void update() {
        EmployeeDAO employeeDAO = new EmployeeDAO(factory);
        AddressDAO addressDAO = new AddressDAO(factory);
        UserDAO userDAO = new UserDAO(factory);
        BranchDAO branchDAO = new BranchDAO(factory);

        Address address = new Address();
        address.setStreet("Test");
        address.setCountry("Test");
        address.setCity("Test");
        address.setPostalCode("Test");
        address.setHouseNumber("1");
        address.setFlatNumber("1");

        addressDAO.create(address);

        Branch branch = new Branch();
        branch.setName("Test");
        branch.setAddressId(HelperMethods.getId(address));
        branchDAO.create(branch);

        branchDAO.delete(branch);

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEmail("test@test");
        user.setPasswordHash("test");
        user.setPasswordSalt("test");
        user.setDateCreated(new java.sql.Date(System.currentTimeMillis()));
        user.setActive(true);
        user.setHasUnpaidPenalty(false);
        user.setAddressId(HelperMethods.getId(address));

        userDAO.create(user);

        Employee employee = new Employee();
        employee.setUsername("Test");
        employee.setPasswordHash("Test");
        employee.setUserID(HelperMethods.getId(user));
        employee.setRole("Test");
        employee.setBranchId(HelperMethods.getId(branch));
        employee.setActive(true);

        employeeDAO.create(employee);

        Employee newEmployee = employeeDAO.read(HelperMethods.getId(employee));
        newEmployee.setUsername("Test2");
        employeeDAO.update(newEmployee);
        Assertions.assertEquals("Test2", employeeDAO.read(HelperMethods.getId(newEmployee)).getUsername());
    }

    @Test
    public void delete() {
        EmployeeDAO employeeDAO = new EmployeeDAO(factory);
        AddressDAO addressDAO = new AddressDAO(factory);
        UserDAO userDAO = new UserDAO(factory);
        BranchDAO branchDAO = new BranchDAO(factory);

        Address address = new Address();
        address.setStreet("Test");
        address.setCountry("Test");
        address.setCity("Test");
        address.setPostalCode("Test");
        address.setHouseNumber("1");
        address.setFlatNumber("1");

        addressDAO.create(address);

        Branch branch = new Branch();
        branch.setName("Test");
        branch.setAddressId(HelperMethods.getId(address));
        branchDAO.create(branch);

        branchDAO.delete(branch);

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEmail("test@test");
        user.setPasswordHash("test");
        user.setPasswordSalt("test");
        user.setDateCreated(new java.sql.Date(System.currentTimeMillis()));
        user.setActive(true);
        user.setHasUnpaidPenalty(false);
        user.setAddressId(HelperMethods.getId(address));

        userDAO.create(user);

        Employee employee = new Employee();
        employee.setUsername("Test");
        employee.setPasswordHash("Test");
        employee.setUserID(HelperMethods.getId(user));
        employee.setRole("Test");
        employee.setBranchId(HelperMethods.getId(branch));
        employee.setActive(true);

        employeeDAO.create(employee);

        employeeDAO.delete(employee);

        Assertions.assertNull(employeeDAO.read(HelperMethods.getId(employee)));
    }
}
