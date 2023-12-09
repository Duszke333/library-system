package db.DAO;

import java.io.Serializable;

public interface DAO<T> {
    public void create(T t);
    public T read(int id);
    public void update(T t);
    public void delete(T t);
}
