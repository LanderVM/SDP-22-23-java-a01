package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="accounts")
public class User {
	
	@Id
	@Column(name="email")
	private String accountName;
	private String password;
	private boolean isAdmin;
	
	public User(String accountName, String password, boolean isAdmin) {
		super();
		this.accountName = accountName;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
}
