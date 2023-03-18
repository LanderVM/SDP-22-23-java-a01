package persistence;

import domain.User;

public interface UserDao extends GenericDao<User> {

    User get(String email);
}