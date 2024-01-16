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

    /**
     * @return List of all entities
     */
    @Override
    public List<T> getAll() {
        List<T> list = tdao.getAll();
        return list;
    }

    /**
     * @param id Id of the entity
     * @return Entity with the given id
     */
    @Override
    public T getById(int id) {
        T t = tdao.read(id);
        return t;
    }

    @Override
    public void create(T entity) {
        tdao.create(entity);
    }

    @Override
    public void update(T entity) {
        tdao.update(entity);
    }

    /**
     * @param entity Entity to be deleted
     */
    @Override
    public void delete(T entity) {
        tdao.delete(entity);
    }
}
