package db.DAO;

import db.HelperMethods;
import db.TestSessionFactoryMaker;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class TestGenericDAO<T, TDAO> {
    protected SessionFactory factory = TestSessionFactoryMaker.getSessionFactory();
    private List<String> tables = new ArrayList<>();
    private TDAO dao;

    protected T createEntity() {
        return null;
    }

    protected TDAO createDAO(SessionFactory factory) {
        return null;
    }

    @Before
    public void setup() {
        try (SessionFactory factory = TestSessionFactoryMaker.getSessionFactory()) {
            tables.add("ADDRESSES");
            tables.add("BRANCHES");
            tables.add("BOOKS");
            tables.add("EMPLOYEES");
            tables.add("USERS");
            tables.add("PENALTIES");
            tables.add("RENTING_QUEUE");
            tables.add("BOOK_RENTALS");
            tables.add("READ_LIST");

            for (String table : tables) {
                HelperMethods.clearTable(table);
            }
        }
    }

    @After
    public void teardown() {
        try (SessionFactory factory = TestSessionFactoryMaker.getSessionFactory()) {
            for (String table : tables) {
                HelperMethods.clearTable(table);
            }
        }
    }
}
