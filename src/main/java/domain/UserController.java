package domain;

import exceptions.EntityDoesntExistException;
import exceptions.IncorrectPasswordException;
import exceptions.InvalidNameException;
import exceptions.UserAlreadyExistsExeption;
import gui.view.UserDTO;
import jakarta.persistence.EntityNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.UserDao;

import java.util.Objects;

public class UserController {

    private final UserDao userDao;
    private User user;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    public ObservableList<UserDTO> getEmployees() {
        return FXCollections.observableArrayList(userDao.getAllForSupplier(user.getSupplier().getSupplierId()).stream().map(UserDTO::new).toList());
    }
    
    public void checkUser(String accountName, String password) throws EntityNotFoundException, IncorrectPasswordException, EntityDoesntExistException {
        User fromDatabase = userDao.get(accountName);
        if(fromDatabase==null)
        	throw new EntityDoesntExistException("there is no user for given accountName");
        if (!fromDatabase.getPassword().equals(password))
            throw new IncorrectPasswordException();
        user = fromDatabase;
    }

    public boolean userIsAdmin() {
        return user.isAdmin();
    }
    
    public Supplier getSupplier() {
    	return user.getSupplier();
    }
    
    public int userId () {
    	return user.getUserId();
    }

    public int supplierIdFromUser () {
    	return user.getSupplier().getSupplierId();
    }

    public void addUser(String email, String surName, String name, String tp, String mp, String funcion, String street, int number,
    		String box, String city, String pc, String counrty, Supplier supplier) throws NumberFormatException, UserAlreadyExistsExeption, InvalidNameException {
    	    	
    	if(userDao.exists(email)) throw new UserAlreadyExistsExeption();
    	
    	userDao.insert(new User(
            email, "test", Objects.equals(funcion, "admin"), surName, name, tp, mp, street, number, box, city, pc, counrty, supplier
    	));
    	
    }
    
    public void updateUser(String email, String surName, String name, String tp, String mp, String funcion, String street, int number,
    		String box, String city, String pc, String counrty) throws EntityNotFoundException, NumberFormatException {
    	 	
    	if(email.isBlank() || email.isEmpty()) throw new IllegalArgumentException("Email mag niet leeg zijn");
    	
    	User user = userDao.get(email);
    	
    	user.setSurname(surName);
    	user.setName(name);
    	user.setTelephone(tp);
    	user.setMobilePhone(mp);
    	user.setAdmin(Objects.equals(funcion, "admin"));
    	user.setAddress(street);
    	user.setHouseNumber(number);
    	user.setBox(box);
    	user.setCity(city);
    	user.setPostalCode(pc);
    	user.setCountry(counrty);
    	
    	userDao.update(user);
    	
    }
    
    public String toString() {
    	return String.format("%s %s",user.getSurname(), user.getName());
    }
}
