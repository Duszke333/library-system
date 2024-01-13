package pap.db.Repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pap.db.Entities.Employee;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeRepositoryTest {
    EmployeeRepository employeeRepository = new EmployeeRepository();

    @Test
    void getAll() {
        List<Employee> employees = employeeRepository.getAll();
        Assertions.assertNotNull(employees);
    }

    @Test
    void getById() {
        Employee employee = employeeRepository.getById(1);
        Assertions.assertNotNull(employee);
    }
}