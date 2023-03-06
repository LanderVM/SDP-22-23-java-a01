package persistence;


import java.util.List;

public interface JPADao<T,E> {

    T get(E id);

    List<?> getAll();

    void update(T order);
}
