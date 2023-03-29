package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import exceptions.UserAlreadyExistsException;
import gui.view.UserDTO;
import persistence.impl.UserDaoJpa;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserTests {

    @Mock
    private UserDaoJpa userDaoJpa;
    @Mock
    private UserDTO userDTO;
    @InjectMocks
    private UserController userController;

    String accountName = "testAdmin@mail.com";
    String surname = "Test";
    String name = "Admin";
    String password = "testAdmin"; 
    String country = "Belgium";   
    String city = "Aalst";         
    String postalCode = "9300";
    String address = "testStraat";   
    int houseNumber = 1;  
    String box = "A2";
    String telephone = "12 34 56 78";
    String mobilePhone = "0412 34 56 78";
    String function = "Magazijnier";

    Supplier supplier;
    User user;
    
    @BeforeEach
    public void beforeEach() {
    	supplier = new Supplier("Tim CO", "tim@mail.com", "Timlaan 24 1000 Brussel", "0426343211", "/images/testImg.jpg");
    	user = new User(accountName, password, true, surname, name, telephone, mobilePhone,
        		address, houseNumber, box, city, postalCode, country, supplier);
    }
    
    @Nested
    class AddTests {
        @Test
        public void addUser_happyFlow() throws NumberFormatException, UserAlreadyExistsException {
            when(userDaoJpa.exists("testAdmin@mail.com")).thenReturn(false);
            when(userDaoJpa.getAllForSupplier(-1)).thenReturn(List.of());
            when(userDaoJpa.get(accountName)).thenReturn(user);
            
            userController.checkUser(accountName, password);
            userController.getEmployees();
            
            userController.addUser("testAdmin@mail.com", "Test", "Admin", "02 70 25 25", "0470 25 25 25", "admin", "doeStreet", 23, "B2", "Aalst", "9300", "Belgium", new Supplier());
        }

        @Test
        public void addUser_UserAlreadyExists_throwsUserAlreadyExistsExeption() {
        	when(userDaoJpa.exists("testAdmin@mail.com")).thenReturn(true);
            assertThrows(UserAlreadyExistsException.class, () -> userController.addUser("testAdmin@mail.com", "Test", "Admin", "02 70 25 25", "0470 25 25 25", "admin", "doeStreet", 23, "B2", "Aalst", "9300", "Belgium", new Supplier()));
        }

       
        @Test
        public void addUser_BlankName_throwsIllegalArgumentException() {
            when(userDaoJpa.exists("testAdmin@mail.com")).thenReturn(false);

            assertThrows(IllegalArgumentException.class, () -> userController.addUser("testAdmin@mail.com", "Test", "", "02 70 25 25", "0470 25 25 25", "admin", "doeStreet", 23, "B2", "Aalst", "9300", "Belgium", new Supplier()));
            assertThrows(IllegalArgumentException.class, () -> userController.addUser("testAdmin@mail.com", "", "Admin", "02 70 25 25", "0470 25 25 25", "admin", "doeStreet", 23, "B2", "Aalst", "9300", "Belgium", new Supplier()));
            assertThrows(IllegalArgumentException.class, () -> userController.addUser("testAdmin@mail.com", "", "", "02 70 25 25", "0470 25 25 25", "admin", "doeStreet", 23, "B2", "Aalst", "9300", "Belgium", new Supplier()));
            assertThrows(IllegalArgumentException.class, () -> userController.addUser("testAdmin@mail.com", "  ", "      ", "02 70 25 25", "0470 25 25 25", "admin", "doeStreet", 23, "B2", "Aalst", "9300", "Belgium", new Supplier()));
        }
        
        @Test
        public void addUser_BlankAccountName_throwsIllegalArgumentException() {
        	when(userDaoJpa.exists("")).thenReturn(false);
        	when(userDaoJpa.exists("  ")).thenReturn(false);
            assertThrows(IllegalArgumentException.class, () -> userController.addUser("", "Test", "Admin", "02 70 25 25", "0470 25 25 25", "admin", "doeStreet", 23, "B2", "Aalst", "9300", "Belgium", new Supplier()));         
            assertThrows(IllegalArgumentException.class, () -> userController.addUser("  ", "Test", "Admin", "02 70 25 25", "0470 25 25 25", "admin", "doeStreet", 23, "B2", "Aalst", "9300", "Belgium", new Supplier()));         
        }

        @Test
        public void addUser_InvalidHouseNumber_throwsNumberFormatException() {
        	when(userDaoJpa.exists("testAdmin@mail.com")).thenReturn(false);
            assertThrows(NumberFormatException.class, () -> userController.addUser("testAdmin@mail.com", "Test", "Admin", "02 70 25 25", "0470 25 25 25", "admin", "doeStreet", 0, "B2", "Aalst", "9300", "Belgium", new Supplier()));
            assertThrows(NumberFormatException.class, () -> userController.addUser("testAdmin@mail.com", "Test", "Admin", "02 70 25 25", "0470 25 25 25", "admin", "doeStreet", -1, "B2", "Aalst", "9300", "Belgium", new Supplier()));
            assertThrows(NumberFormatException.class, () -> userController.addUser("testAdmin@mail.com", "Test", "Admin", "02 70 25 25", "0470 25 25 25", "admin", "doeStreet", -99, "B2", "Aalst", "9300", "Belgium", new Supplier()));
        }
    }

    @Nested
    class UpdateTests {
        @Test
        public void updateService_happyFlow() {
            when(userDaoJpa.get(accountName)).thenReturn(user);
            when(userDaoJpa.getAllForSupplier(-1)).thenReturn(List.of(user));       
            
            userController.checkUser(accountName, password);
            userController.getEmployees();
            
            userController.updateUser(accountName, surname, name, telephone, mobilePhone, function,
            		address, houseNumber, box, city, postalCode, country);

            User updatedUser = userDaoJpa.get(accountName);
            assertEquals(updatedUser.getAccountName(), accountName);
            assertEquals(updatedUser.getName(), name);
            assertEquals(updatedUser.getSurname(), surname);
            assertEquals(updatedUser.getCountry(), country);
            assertEquals(updatedUser.getCity(), city);
            assertEquals(updatedUser.getPostalCode(), postalCode);
            assertEquals(updatedUser.getAddress(), address);
            assertEquals(updatedUser.getHouseNumber(), houseNumber);
            assertEquals(updatedUser.getBox(), box);
            assertEquals(updatedUser.getTelephone(), telephone);
            assertEquals(updatedUser.getMobilePhone(), mobilePhone);
            assertFalse(updatedUser.isAdmin());
        }
        
        @Test
        public void updateUser_BlankName_throwsInvalidNameException() {
        	User user = new User("testAdmin@mail.com", "testAdmin", true, "Test", "Admin", "02 70 25 25", "0470 25 25 25", "doeStreet", 23, "B2", "Aalst", "9300", "Belgium", new Supplier());

            when(userDaoJpa.get("testAdmin@mail.com")).thenReturn(user);
        
            assertThrows(IllegalArgumentException.class, () -> userController.updateUser(accountName, "", name, telephone, mobilePhone, function,
            		address, houseNumber, box, city, postalCode, country));
            assertThrows(IllegalArgumentException.class, () -> userController.updateUser(accountName, surname, "", telephone, mobilePhone, function,
            		address, houseNumber, box, city, postalCode, country));
            assertThrows(IllegalArgumentException.class, () -> userController.updateUser("", surname, name, telephone, mobilePhone, function,
            		address, houseNumber, box, city, postalCode, country));
        }

        @Test
        public void updateUser_InvalidHouseNumber_throwsInvalidNameException() {
        	User user = new User("testAdmin@mail.com", "testAdmin", true, "Test", "Admin", "02 70 25 25", "0470 25 25 25", "doeStreet", 23, "B2", "Aalst", "9300", "Belgium", new Supplier());

            when(userDaoJpa.get("testAdmin@mail.com")).thenReturn(user);
        
            assertThrows(NumberFormatException.class, () -> userController.updateUser(accountName, surname, name, telephone, mobilePhone, function,
            		address, 0, box, city, postalCode, country));
            assertThrows(NumberFormatException.class, () -> userController.updateUser(accountName, surname, name, telephone, mobilePhone, function,
            		address, -1, box, city, postalCode, country));    
            assertThrows(NumberFormatException.class, () -> userController.updateUser(accountName, surname, name, telephone, mobilePhone, function,
            		address, -99, box, city, postalCode, country));
        }
        
    }
}