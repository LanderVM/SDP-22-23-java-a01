package domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class User {

    @Id
    @Column(name = "email")
    private String accountName;
    private String password;
    private int isAdmin;

    public User(String accountName, String password, int isAdmin) {
        super();
        this.accountName = accountName;
        this.password = password;
        this.isAdmin = isAdmin;
    }


    protected User() {
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

    public int isAdmin() {
        return isAdmin;
    }

    public void setAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }


}
