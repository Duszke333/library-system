package db.DAO;

import java.io.Serializable;
import java.util.List;

public interface DAO<T> {
    public void create(T t);
    public T read(int id);
    public void update(T t);
    public void delete(T t);
    public List<T> getAll();
    public List<T> query(String sql);
}
