package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.Entities.*;

import java.util.Date;
import java.util.List;

public class HelperMethods {
    private static SessionFactory factory = TestSessionFactoryMaker.getSessionFactory();

    public static int getRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static String getRandomString(int length) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String result = "";
        for (int i = 0; i < length; i++) {
            result += alphabet.charAt(getRandomInt(0, alphabet.length()));
        }
        return result;
    }

    public static String getRandomEmail() {
        return getRandomString(10) + "@" + getRandomString(5) + ".com";
    }

    public static Date getRandomDate() {
        return new Date(getRandomInt(0, 1000000000));
    }

    public static int getId(Book book) {
        int id;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            List<Book> books = session.createNativeQuery("SELECT * FROM pap.books WHERE isbn = '" + book.getIsbn() + "'", Book.class).list();
            if (books.isEmpty()) {
                return -1;
            }
            id = books.get(0).getBookId();
            session.getTransaction().commit();
        }
        return id;
    }

    public static int getId(Address address) {
        int id;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            List<Address> addresses = session.createNativeQuery(
                    "SELECT * FROM pap.addresses " +
                            "WHERE street = '" + address.getStreet() + "'" +
                            "AND flat_number = '" + address.getFlatNumber() + "' AND house_number = '" + address.getHouseNumber() + "'"
                    , Address.class).list();
            if (addresses.isEmpty()) {
                return -1;
            }
            id = addresses.get(0).getAddressId();
            session.getTransaction().commit();
        }
        return id;
    }

    public static int getId(User user) {
        int id;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            List<User> users = session.createNativeQuery("SELECT * FROM pap.users WHERE email = '" + user.getEmail() + "'", User.class).list();
            if (users.isEmpty()) {
                return -1;
            }
            id = users.get(0).getAccountId();
            session.getTransaction().commit();
        }
        return id;
    }

    public static int getId(Branch branch) {
        int id;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            List<Branch> branches = session.createNativeQuery("SELECT * FROM pap.branches WHERE branch_name = '" + branch.getName() + "'", Branch.class).list();
            if (branches.isEmpty()) {
                return -1;
            }
            id = branches.get(0).getBranchId();
            session.getTransaction().commit();
        }
        return id;
    }

    public static int getId(Employee employee) {
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

    public static void clearTable(String tableName) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE pap." + tableName + " CASCADE").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error while clearing table " + tableName);
        }
    }

}
