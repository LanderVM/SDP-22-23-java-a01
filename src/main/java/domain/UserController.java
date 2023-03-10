package domain;

import exceptions.IncorrectPasswordException;
import jakarta.persistence.EntityNotFoundException;
import persistence.UserJPADao;

public class UserController {

    private final UserJPADao userJPADao;
    private User user;

    public UserController(UserJPADao userJPADao) {
        this.userJPADao = userJPADao;
    }

    public void checkUser(String accountName, String password) throws EntityNotFoundException, IncorrectPasswordException {
        user = userJPADao.get(accountName);
        if (!user.getPassword().equals(password))
            throw new IncorrectPasswordException();
    }

    public boolean userIsAdmin() {
        return user.isAdmin();
    }
    public String userName() {
        return user.getName();
    }

    public String userSurname() {
        return user.getSurname();
    }
    
    public int userId () {
    	return user.getUserId();
    }
    
    public int supplierIdFromUser () {
    	return user.getSupplier().getSupplierId();
    }
    
    public String toString() {
    	return String.format("%s %s",user.getSurname(), user.getName());
    }
}
