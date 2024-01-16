package pap.db.DAO;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * @param <T> Entity class
 *     Base DAO interface
 *     It provides methods that other DAOs need to implement
 */
public interface DAO<T> {
    void create(T t);
    T read(int id);
    void update(T t);
    void delete(T t);
    List<T> getAll();
    List<T> query(String sql);
}
