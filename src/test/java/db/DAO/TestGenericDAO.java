package db.DAO;

import db.HelperMethods;
import db.TestSessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pap.db.Entities.Employee;

import java.util.ArrayList;
import java.util.List;

public class TestGenericDAO<T, TDAO> {
    protected SessionFactory factory = TestSessionFactoryMaker.getSessionFactory();
    private TDAO dao;

    protected T createEntity() {
        return null;
    }

    protected TDAO createDAO(SessionFactory factory) {
        return null;
    }

    protected int getId(T entity) {
        return -1;
    }
}
