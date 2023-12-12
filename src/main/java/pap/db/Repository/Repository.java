package pap.db.Repository;

import java.util.List;

@Deprecated(since = "IRepository exists", forRemoval = true)
public interface Repository<T> {
    List<T> getAll();
    T getById(int id);
    void create(T entity);
    void update(T entity);
    void delete(T entity);
}
