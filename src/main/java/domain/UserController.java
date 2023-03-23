package domain;

import exceptions.EntityDoesntExistException;
import exceptions.IncorrectPasswordException;
import jakarta.persistence.EntityNotFoundException;
import persistence.UserDao;

public class UserController {

    private final UserDao userDao;
    private User user;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    public void checkUser(String accountName, String password) throws EntityNotFoundException, IncorrectPasswordException, EntityDoesntExistException {
        user = userDao.get(accountName);
        if(user==null)
        	throw new EntityDoesntExistException("there is no user for given accountName");
        if (!user.getPassword().equals(password))
            throw new IncorrectPasswordException();
    }

    public boolean userIsAdmin() {
        return user.isAdmin();
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
