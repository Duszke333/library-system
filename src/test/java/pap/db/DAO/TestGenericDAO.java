package pap.db.DAO;

import pap.db.TestSessionFactoryMaker;
import org.hibernate.SessionFactory;

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
