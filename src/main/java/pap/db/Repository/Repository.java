package db.Repository;

import pap.db.Repository.Interface.IRepository;

import java.util.List;

public abstract class Repository<T> implements IRepository<T> {
    public List<T> getAll() {
        return null;
    }

    public T getById(int id) {
        return null;
    }

    public void create(T entity) {

    }

    public void update(T entity) {

    }

    public void delete(T entity) {

    }
}
