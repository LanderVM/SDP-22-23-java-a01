package persistence;


public interface GenericDao<T> {

    T get(int id);

    void update(T entity);

    void insert(T entity);

    boolean exists(int id);

}
