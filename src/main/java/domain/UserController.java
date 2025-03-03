package domain;

import exceptions.UserAlreadyExistsException;
import gui.view.UserDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.UserDao;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserController {

    private final UserDao userDao;
    private User user;
    private int supplierId;
    private ObservableList<UserDTO> userList = FXCollections.emptyObservableList();

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    public ObservableList<UserDTO> getEmployees() {
        if (userList.isEmpty() || supplierId != supplierIdFromUser()) {
            supplierId = supplierIdFromUser();
            this.userList = FXCollections.observableList(userDao.getAllForSupplier(user.getSupplier().getSupplierId()).stream().map(UserDTO::new).collect(Collectors.toList()));
        }
        return this.userList;
    }

    public void checkUser(String accountName, String password) {
    	User fromDatabase;
    	try {
    		fromDatabase = userDao.get(accountName);
    	}catch (NoResultException e) {
    		throw new IllegalArgumentException("Invalid login credentials!");
    	}
        if (fromDatabase == null || !fromDatabase.getPassword().equals(password))
            throw new IllegalArgumentException("Invalid login credentials!");
        user = fromDatabase;
    }

    public boolean userIsAdmin() {
        return user.isAdmin();
    }

    public Supplier getSupplier() {
        return user.getSupplier();
    }

    public int userId() {
        return user.getUserId();
    }

    public int supplierIdFromUser() {
        return user.getSupplier().getSupplierId();
    }

    public void addUser(String email, String password, String surName, String name, String tp, String function, String street, int number, String box, String city, String pc, String counrty, Supplier supplier) throws NumberFormatException, UserAlreadyExistsException {

        if (userDao.exists(email)) throw new UserAlreadyExistsException();

        User user = new User(email, password, Objects.equals(function, "Admin"), surName, name, tp, street, number, box, city, pc, counrty, supplier);
        userDao.insert(user);
        userList.add(new UserDTO(user));
    }

    public void updateUser(String email, String password, String surName, String name, String tp, String funcion, String street, int number, String box, String city, String pc, String counrty) throws EntityNotFoundException, NumberFormatException {

        if (email.isBlank() || email.isEmpty()) throw new IllegalArgumentException("Email mag niet leeg zijn");

        User user = userDao.get(email);

        user.setSurname(surName);
        user.setName(name);
        user.setTelephone(tp);
        user.setPassword(password);
        user.setAdmin(Objects.equals(funcion, "admin"));
        user.setAddress(street);
        user.setHouseNumber(number);
        user.setBox(box);
        user.setCity(city);
        user.setPostalCode(pc);
        user.setCountry(counrty);

        userDao.update(user);
        userList.set(getIndex(user.getUserId()), new UserDTO(user));
    }

    private int getIndex(int employeeId) {
        List<UserDTO> correspondingDTOs = userList.stream().filter(dto -> dto.getEmployeeId() == employeeId).toList();
        if (correspondingDTOs.isEmpty())
            throw new IllegalArgumentException("There is no UserDTO matching this employeeId!");
        if (correspondingDTOs.size() > 1)
            throw new RuntimeException("There were multiple UserDTO found matching this employeeId!");
        return userList.indexOf(correspondingDTOs.get(0));
    }

    public String toString() {
        return String.format("%s %s", user.getSurname(), user.getName());
    }
}
