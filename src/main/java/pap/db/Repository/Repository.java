package db.Repository;

import java.util.List;

public interface Repository<T> {
    List<T> getAll();
    T getById(int id);
    void create(T entity);
    void update(T entity);
    void delete(T entity);
}
