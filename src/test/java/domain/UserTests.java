package domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import exceptions.InvalidNameException;
import exceptions.UserAlreadyExistsExeption;
import jakarta.persistence.Column;
import persistence.impl.TransportServiceDaoJpa;
import persistence.impl.UserDaoJpa;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserTests {

    @Mock
    private UserDaoJpa userDaoJpa;
    @InjectMocks
    private UserController userController;

    String accountName = "testAdmin@mail.com";
    String surname = "Test";
    String name = "Admin";
    String password = "pass"; 
    String country = "Belgium";   
    String city = "Aalst";         
    String postalCode = "9300";
    String address = "testStraat";   
    int houseNumber = 1;  
    String box = "A2";
    String telephone = "12 34 56 78";
    String mobilePhone = "0412 34 56 78";
    String function = "magazijnier";

    @Nested
    class AddTests {
        @Test
        public void addUser_happyFlow() throws NumberFormatException, UserAlreadyExistsExeption, InvalidNameException {
            when(userDaoJpa.exists("testAdmin@mail.com")).thenReturn(false);
            userController.addUser("testAdmin@mail.com", "Test", "Admin", "02 70 25 25", "0470 25 25 25", "admin", "doeStreet", 23, "B2", "Aalst", "9300", "Belgium", new Supplier());
        }

        @Test
        public void addUser_UserAlreadyExists_throwsUserAlreadyExistsExeption() {
        	when(userDaoJpa.exists("testAdmin@mail.com")).thenReturn(true);
            assertThrows(UserAlreadyExistsExeption.class, () -> userController.addUser("testAdmin@mail.com", "Test", "Admin", "02 70 25 25", "0470 25 25 25", "admin", "doeStreet", 23, "B2", "Aalst", "9300", "Belgium", new Supplier()));
        }

       
        @Test
        public void addUser_BlankName_throwsIllegalArgumentException(String mail, String surName, String name, String pn, String mp, String password, String sn, int hn, String box, String city, String pc, String country) {
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
            User user = new User("testAdmin@mail.com", "testAdmin", true, "Test", "Admin", "02 70 25 25", "0470 25 25 25", "doeStreet", 23, "B2", "Aalst", "9300", "Belgium", new Supplier());

            when(userDaoJpa.get("testAdmin@mail.com")).thenReturn(user);
                     
            userController.updateUser(accountName, surname, name, telephone, mobilePhone, function,
            		address, houseNumber, box, city, postalCode, country);

            User updatedUser = userDaoJpa.get("testAdmin@mail.com");
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
            assertEquals(updatedUser.isAdmin(), false);
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