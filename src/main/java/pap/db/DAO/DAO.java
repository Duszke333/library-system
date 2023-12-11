package pap.db.DAO;

import java.io.Serializable;
import java.util.List;

public interface DAO<T> {
    void create(T t);
    T read(int id);
    void update(T t);
    void delete(T t);
    List<T> getAll();
    List<T> query(String sql);
}
