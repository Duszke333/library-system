package pap.db.Repository;

import pap.db.DAO.GenericDAO;
import pap.db.Repository.Interface.IRepository;

import java.util.List;

/**
 * @param <T> Entity class
 *           Base repository class
 *           It provides basic methods for other repositories
 *           It uses generic DAO to access the database
 */
public class GenericRepository<T> implements IRepository<T> {
    /**
     * @param type Entity class
     * @param tdao GenericDAO for the entity
     */
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

    /**
     * @param entity Entity to be created
     */
    @Override
    public void create(T entity) {
        tdao.create(entity);
    }

    /**
     * @param entity Entity to be updated
     */
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
