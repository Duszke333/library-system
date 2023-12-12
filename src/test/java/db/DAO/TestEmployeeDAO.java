package db.DAO;


import db.TestSessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pap.db.DAO.EmployeeDAO;
import pap.db.Entities.Employee;

import java.util.List;

public class TestEmployeeDAO {
    private SessionFactory factory = TestSessionFactoryMaker.getSessionFactory();

    private int getId(Employee employee) {
        int id;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            List<Employee> employees = session.createNativeQuery("SELECT * FROM pap.employees WHERE username = '" + employee.getUsername() + "'", Employee.class).list();
            if (employees.isEmpty()) {
                return -1;
            }
            id = employees.get(0).getEmployeeId();
            session.getTransaction().commit();
        }
        return id;
    }

    @Before
    public void setup() {

        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE pap.EMPLOYEES CASCADE").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @After
    public void teardown() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE pap.EMPLOYEES CASCADE").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void create() {
        EmployeeDAO employeeDAO = new EmployeeDAO(factory);

        Employee employee = new Employee();
        employee.setUsername("Test");
        employee.setPasswordHash("Test");
        employee.setUserID(1);
        employee.setRole("Test");
        employee.setBranchId(1);
        employee.setActive(true);

        employeeDAO.create(employee);

        Employee newEmployee = employeeDAO.read(getId(employee));
        Assertions.assertEquals(getId(employee), newEmployee.getEmployeeId());
    }

    @Test
    public void update() {
        EmployeeDAO employeeDAO = new EmployeeDAO(factory);

        Employee employee = new Employee();
        employee.setUsername("Test");
        employee.setPasswordHash("Test");
        employee.setUserID(1);
        employee.setRole("Test");
        employee.setBranchId(1);
        employee.setActive(true);

        employeeDAO.create(employee);

        Employee newEmployee = employeeDAO.read(getId(employee));
        newEmployee.setUsername("Test2");
        employeeDAO.update(newEmployee);
        Assertions.assertEquals("Test2", employeeDAO.read(getId(newEmployee)).getUsername());
    }

    @Test
    public void delete() {
        EmployeeDAO employeeDAO = new EmployeeDAO(factory);

        Employee employee = new Employee();
        employee.setUsername("Test");
        employee.setPasswordHash("Test");
        employee.setUserID(1);
        employee.setRole("Test");
        employee.setBranchId(1);
        employee.setActive(true);

        employeeDAO.create(employee);

        employeeDAO.delete(employee);

        Assertions.assertNull(employeeDAO.read(getId(employee)));
    }
}
