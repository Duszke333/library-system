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
