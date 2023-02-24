package testen;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.User;

class UserTest {

	private User user;
	private String accountName = "erik.dahl@outlook.com";
	private String password = "ZRt5?.Up2!m";
	private boolean isAdmin = true;
	
	@Test
	public void maakUser_checkFields() {
		user = new User(accountName,password,isAdmin);
		
		Assertions.assertEquals(user.getAccountName(), accountName);
		Assertions.assertEquals(user.getPassword(), password);
		Assertions.assertEquals(user.isAdmin(), isAdmin);
	}
	
	@Test
	public void maakUser_changeFields() {
		user = new User(accountName,password,isAdmin);
		
		user.setAccountName("thomas.mann@outlook.com");
		Assertions.assertEquals(user.getAccountName(), "thomas.mann@outlook.com");
		user.setPassword("Pr5!H;?jk567");
		Assertions.assertEquals(user.getPassword(), "Pr5!H;?jk567");
		user.setAdmin(false);
		Assertions.assertEquals(user.isAdmin(), false);
	}

}
