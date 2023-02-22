package domain;

public class User {
	
	private String accountName;
	private String password;
	private boolean isAdmin;
	
	public Users(String accountName, String password, boolean isAdmin) {
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
