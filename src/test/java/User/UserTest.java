package User;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domain.User;

class UserTest {

    private User user;
    private final String accountName = "erik.dahl@outlook.com";
    private final String password = "ZRt5?.Up2!m";
    private final boolean isAdmin = true;

    @Test
    public void maakUser_checkFields() {
        user = new User(accountName, password, isAdmin);

        assertEquals(user.getAccountName(), accountName);
        assertEquals(user.getPassword(), password);
        assertEquals(user.isAdmin(), isAdmin);
    }

    @Test
    public void maakUser_changeFields() {
        user = new User(accountName, password, isAdmin);

        user.setAccountName("thomas.mann@outlook.com");
        assertEquals(user.getAccountName(), "thomas.mann@outlook.com");
        user.setPassword("Pr5!H;?jk567");
        assertEquals(user.getPassword(), "Pr5!H;?jk567");
        user.setAdmin(false);
        assertFalse(user.isAdmin());
    }

}
