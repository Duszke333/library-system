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

        /* Modify user */
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, 1);
            user.setFirstName("John");
            user.setLastName("Doe");

            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        /* Get user */
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, 1);
            System.out.println(user.getFirstName());
            System.out.println(user.getLastName());
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
