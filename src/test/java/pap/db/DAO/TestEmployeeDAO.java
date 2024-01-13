package pap.db.DAO;


import pap.db.RandomEntityGenerator;
import org.hibernate.SessionFactory;
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
    protected int getId(Employee employee) {
        int id;
        try (org.hibernate.Session session = factory.openSession()) {
            session.beginTransaction();
            java.util.List<Employee> employees = session.createNativeQuery(
                    "SELECT * FROM pap.employees " +
                            "WHERE username = '" + employee.getUsername() + "'"
                    , Employee.class).list();
            if (employees.isEmpty()) {
                return -1;
            }
            id = employees.get(0).getEmployeeId();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error while getting id of employee " + employee.getUsername());
            return -1;
        }
        return id;
    }

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
        Assertions.assertEquals(getId(employee), employee.getEmployeeId());
    }

    @Test
    public void update() {
        EmployeeDAO employeeDAO = createDAO(factory);
        Employee employee = createEntity();
        employeeDAO.create(employee);
        employee.setUsername("Test2");
        employeeDAO.update(employee);
        Employee newEmployee = employeeDAO.read(getId(employee));
        Assertions.assertEquals(employee.getUsername(), newEmployee.getUsername());
    }

    @Test
    public void delete() {
        EmployeeDAO employeeDAO = createDAO(factory);
        Employee employee = createEntity();
        employeeDAO.create(employee);
        employeeDAO.delete(employee);
        Assertions.assertNull(employeeDAO.read(getId(employee)));
    }
}
