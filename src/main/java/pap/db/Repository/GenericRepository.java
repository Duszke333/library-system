package pap.db.Repository;

import pap.db.DAO.GenericDAO;
import pap.db.Repository.Interface.IRepository;

import java.util.List;

public class GenericRepository<T> implements IRepository<T> {
    private final Class<T> type;
    private final GenericDAO<T> tdao;

    public GenericRepository(Class<T> type, GenericDAO<T> tdao) {
        this.type = type;
        this.tdao = tdao;
    }

    @Override
    public List<T> getAll() {
        return tdao.getAll();
    }

    @Override
    public T getById(int id) {
        return tdao.read(id);
    }

    @Override
    public void create(T entity) {
        tdao.create(entity);
    }

    @Override
    public void update(T entity) {
        tdao.update(entity);
    }

    @Override
    public void delete(T entity) {
        tdao.delete(entity);
    }
}
