package persistence;

import domain.User;

import java.util.List;

public interface UserDao extends GenericDao<User> {

    User get(String email);

    List<User> getAllForSupplier(int supplierId);

    boolean exists(String email);

    void insert(User user);

}