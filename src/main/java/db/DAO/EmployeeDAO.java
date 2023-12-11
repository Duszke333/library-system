package db.DAO;

import db.Entities.Employee;
import db.SessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class EmployeeDAO implements DAO<Employee>{
    SessionFactoryMaker sessionFactoryMaker = new SessionFactoryMaker();
    SessionFactory factory = sessionFactoryMaker.getSessionFactory();

    public void create(Employee employee) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(employee);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Employee read(int id) {
        Employee employee = null;
        try (Session session = factory.openSession()) {
            employee = session.get(Employee.class, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return employee;
    }

    public void update(Employee employee) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.update(employee);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(Employee employee) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.delete(employee);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = null;
        try (Session session = factory.openSession()) {
            employees = session.createNativeQuery("SELECT * FROM employees").list();
            return employees;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return employees;
    }

    @Override
    public List<Employee> query(String sql) {
        List<Employee> employees = null;
        try (Session session = factory.openSession()) {
            employees = session.createNativeQuery(sql, Employee.class).list();
            return employees;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return employees;
    }

}
