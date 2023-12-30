package pap.db.DAO.EntityDAO;

import pap.db.DAO.GenericDAO;
import pap.db.Entities.Employee;
import pap.db.SessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class EmployeeDAO extends GenericDAO<Employee> {

        public EmployeeDAO() {
            super(Employee.class, "pap.employees");
        }

        public EmployeeDAO(SessionFactory factory) {
            super(Employee.class, "pap.employees", factory);
        }
}
