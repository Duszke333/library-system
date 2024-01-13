package pap.db.Repository.Interface;


import java.util.List;

/**
 * @param <T> Entity class
 *     Base repository interface
 *     It provides methods that other repositories need to implement
 */
public interface IRepository<T> {
    List<T> getAll();
    T getById(int id);
    void create(T entity);
    void update(T entity);
    void delete(T entity);
}
