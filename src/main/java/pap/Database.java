package pap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Date;
import java.util.Scanner;

import db.Entities.Address;
import db.Entities.User;
import db.Entities.Book;
import db.SessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class Database {
    public static void main(String[] args) {
        SessionFactoryMaker sessionFactoryMaker = new SessionFactoryMaker();
        SessionFactory factory = sessionFactoryMaker.getSessionFactory();

        Scanner scanner = new Scanner(System.in);

        /* Get user */
        User user = new User();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            user = session.get(User.class, 1);
            System.out.println(user.getFirstName());
            System.out.println(user.getLastName());
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        /* Modify user */
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            System.out.println("Enter new first name: ");
            String firstName = scanner.nextLine();
            user.setFirstName(firstName);

            System.out.println("Enter new last name: ");
            String lastName = scanner.nextLine();
            user.setLastName(lastName);

            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        /* Get new user data */
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            user = session.get(User.class, 1);
            System.out.println(user.getFirstName());
            System.out.println(user.getLastName());
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
