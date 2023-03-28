package persistence;

import java.util.List;

import domain.User;

public interface UserDao extends GenericDao<User> {

    User get(String email);
    
    List<User> getAllForSupplier(int supplierId);
    
    boolean exists(String email);
    
    void insert(User user);
       
}