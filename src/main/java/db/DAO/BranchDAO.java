package db.DAO;

import db.Entities.Branch;
import db.SessionFactoryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class BranchDAO implements DAO<Branch>{
    SessionFactoryMaker sessionFactoryMaker = new SessionFactoryMaker();
    SessionFactory factory = sessionFactoryMaker.getSessionFactory();

    @Override
    public void create(Branch branch) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(branch);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Branch read(int id) {
        Branch branch = null;
        try (Session session = factory.openSession()) {
            branch = session.get(Branch.class, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return branch;
    }

    @Override
    public void update(Branch branch) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.update(branch);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Branch branch) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.delete(branch);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Branch> getAll() {
        List<Branch> branches = null;
        try (Session session = factory.openSession()) {
            branches = session.createNativeQuery("SELECT * FROM branches").list();
            return branches;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return branches;
    }

    @Override
    public List<Branch> query(String sql) {
        List<Branch> branches = null;
        try (Session session = factory.openSession()) {
            branches = session.createNativeQuery(sql).list();
            return branches;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return branches;
    }
}
