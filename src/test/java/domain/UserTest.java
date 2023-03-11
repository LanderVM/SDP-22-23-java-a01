package domain;

import exceptions.IncorrectPasswordException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import application.StartUp;
import persistence.UserJPADao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Objects;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Mock
    EntityManager entityManager;

    @Mock
    TypedQuery<User> query;

    private UserJPADao userDao;
    private UserController userController;

    private User admin;
    private Supplier supplier;

    @Test
    public void getById_happyFlow() {

        supplier = new Supplier("Tim CO","tim@mail.com","Timlaan 24 1000 Brussel" , "0426343211", getFile());
        admin = new User("testAdmin@mail.com", "testAdmin", true, "Test", "Admin", supplier);

        when(entityManager.createNamedQuery("User.findById", User.class)).thenReturn(query);
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(admin);

        userDao = new UserJPADao(entityManager);

        assertEquals(admin, userDao.get(1));
        verify(query).setParameter(1, 1);
    }
    private static byte[] getFile() {
        byte[] fileContent = null;
        try {
            File fi = new File(Objects.requireNonNull(StartUp.class.getResource("/images/testImg.jpg")).toURI());
            fileContent = Files.readAllBytes(fi.toPath());
        } catch (IOException | URISyntaxException exception) {
            exception.printStackTrace();
        }
        return fileContent;
    }

    @Test
    public void getById_invalidID_throwsNoResultException() {
        when(entityManager.createNamedQuery("User.findById", User.class)).thenReturn(query);
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenThrow(NoResultException.class);

        userDao = new UserJPADao(entityManager);

        assertThrows(NoResultException.class, () -> userDao.get(1));
        verify(query).setParameter(1, 1);
    }

    @Test
    public void getByEmail_happyFlow() {
        supplier = new Supplier("Tim CO","tim@mail.com","Timlaan 24 1000 Brussel" , "0426343211", getFile());
        admin = new User("testAdmin@mail.com", "testAdmin", true, "Test", "Admin", supplier);

        when(entityManager.createNamedQuery("User.findByEmail", User.class)).thenReturn(query);
        when(query.setParameter(1, "testAdmin@mail.com")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(admin);

        userDao = new UserJPADao(entityManager);

        assertEquals(admin, userDao.get("testAdmin@mail.com"));
        verify(query).setParameter(1, "testAdmin@mail.com");
    }

    @Test
    public void getByEmail_invalidEmail_throwsNoResultException() {
        when(entityManager.createNamedQuery("User.findByEmail", User.class)).thenReturn(query);
        when(query.setParameter(1, "testAdmin@mail.com")).thenReturn(query);
        when(query.getSingleResult()).thenThrow(NoResultException.class);

        userDao = new UserJPADao(entityManager);

        assertThrows(NoResultException.class, () -> userDao.get("testAdmin@mail.com"));
        verify(query).setParameter(1, "testAdmin@mail.com");
    }

    @Test
    public void checkUser_happyFlow() throws IncorrectPasswordException {
        supplier = new Supplier("Tim CO","tim@mail.com","Timlaan 24 1000 Brussel" , "0426343211", getFile());
        admin = new User("testAdmin@mail.com", "testAdmin", true, "Test", "Admin", supplier);

        when(entityManager.createNamedQuery("User.findByEmail", User.class)).thenReturn(query);
        when(query.setParameter(1, "testAdmin@mail.com")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(admin);

        userDao = new UserJPADao(entityManager);
        userController = new UserController(userDao);
        userController.checkUser("testAdmin@mail.com", "testAdmin");
        verify(query).setParameter(1, "testAdmin@mail.com");
    }

    @Test
    public void checkUser_invalidPassword_throwsIncorrectPasswordException() {
        supplier = new Supplier("Tim CO","tim@mail.com","Timlaan 24 1000 Brussel" , "0426343211", getFile());
        admin = new User("testAdmin@mail.com", "testAdminFalse", true, "Test", "Admin", supplier);

        when(entityManager.createNamedQuery("User.findByEmail", User.class)).thenReturn(query);
        when(query.setParameter(1, "testAdmin@mail.com")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(admin);

        userDao = new UserJPADao(entityManager);
        userController = new UserController(userDao);

        assertThrows(IncorrectPasswordException.class, () -> userController.checkUser("testAdmin@mail.com", "testAdmin"));
        verify(query).setParameter(1, "testAdmin@mail.com");
    }

    @Test
    public void checkUser_invalidEmail_throwsNoResultException() {
        supplier = new Supplier("Tim CO","tim@mail.com","Timlaan 24 1000 Brussel" , "0426343211", getFile());
        admin = new User("testAdmin@mail.com", "testAdminFalse", true, "Test", "Admin", supplier);

        when(entityManager.createNamedQuery("User.findByEmail", User.class)).thenReturn(query);
        when(query.setParameter(1, "testAdmin@mail.com")).thenReturn(query);
        when(query.getSingleResult()).thenThrow(NoResultException.class);

        userDao = new UserJPADao(entityManager);
        userController = new UserController(userDao);

        assertThrows(NoResultException.class, () -> userController.checkUser("testAdmin@mail.com", "testAdmin"));
        verify(query).setParameter(1, "testAdmin@mail.com");
    }
}
