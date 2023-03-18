package persistence;

import java.util.List;

public interface GenericDao<T> {

    T get(int id);

    List<T> getAll();

    void update(T entity);

    void insert(T entity);

}
