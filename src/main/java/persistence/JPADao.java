package persistence;


import java.util.List;
import java.util.Optional;

public interface JPADao<T> {

    Optional<T> get(int id);

    List<?> getAll();

    void update(T order);
}
