package persistence;


import java.util.List;

public interface JPADao<T> {

    T get(int id);

    List<?> getAll();

    void update(T order);
}
