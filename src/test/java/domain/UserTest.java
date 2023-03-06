package domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import persistence.UserJPADoa;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Mock
    EntityManager entityManager;

    @Mock
    TypedQuery<User> query;

    private UserJPADoa userDao;
    private UserController userController;

    private User admin;

    @Test
    public void getById_happyFlow() {
        admin = new User("testAdmin@mail.com", "testAdmin", true);

        when(entityManager.createNamedQuery("User.findById", User.class)).thenReturn(query);
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(admin);

        userDao = new UserJPADoa(entityManager);

        assertEquals(admin, userDao.get(1).orElse(null));
        verify(query).setParameter(1, 1);
    }

    @Test
    public void getById_invalidID_returnsEmptyOptional() {
        when(entityManager.createNamedQuery("User.findById", User.class)).thenReturn(query);
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(null);

        userDao = new UserJPADoa(entityManager);

        assertEquals(Optional.empty(), userDao.get(1));
        verify(query).setParameter(1, 1);
    }

    @Test
    public void getByEmail_happyFlow() {
        admin = new User("testAdmin@mail.com", "testAdmin", true);

        when(entityManager.createNamedQuery("User.findByEmail", User.class)).thenReturn(query);
        when(query.setParameter(1, "testAdmin@mail.com")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(admin);

        userDao = new UserJPADoa(entityManager);

        assertEquals(admin, userDao.get("testAdmin@mail.com").orElse(null));
        verify(query).setParameter(1, "testAdmin@mail.com");
    }

    @Test
    public void getByEmail_invalidEmail_returnsEmptyOptional() {
        when(entityManager.createNamedQuery("User.findByEmail", User.class)).thenReturn(query);
        when(query.setParameter(1, "testAdmin@mail.com")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(null);

        userDao = new UserJPADoa(entityManager);

        assertEquals(Optional.empty(), userDao.get("testAdmin@mail.com"));
        verify(query).setParameter(1, "testAdmin@mail.com");
    }

    @Test
    public void checkUser_happyFlow() {
        admin = new User("testAdmin@mail.com", "testAdmin", true);

        when(entityManager.createNamedQuery("User.findByEmail", User.class)).thenReturn(query);
        when(query.setParameter(1, "testAdmin@mail.com")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(admin);

        userDao = new UserJPADoa(entityManager);
        userController = new UserController(userDao);

        assertTrue(userController.checkUser("testAdmin@mail.com", "testAdmin"));
        verify(query).setParameter(1, "testAdmin@mail.com");
    }

    @Test
    public void checkUser_invalidPassword_returnsFalse() {
        admin = new User("testAdmin@mail.com", "testAdminFalse", true);

        when(entityManager.createNamedQuery("User.findByEmail", User.class)).thenReturn(query);
        when(query.setParameter(1, "testAdmin@mail.com")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(admin);

        userDao = new UserJPADoa(entityManager);
        userController = new UserController(userDao);

        assertFalse(userController.checkUser("testAdmin@mail.com", "testAdmin"));
        verify(query).setParameter(1, "testAdmin@mail.com");
    }


}
