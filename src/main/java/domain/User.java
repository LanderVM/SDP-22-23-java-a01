package domain;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(
                name = "User.findAll",
                query = "SELECT u FROM User u"
        ),
        @NamedQuery(
                name = "User.findByEmail",
                query = "SELECT u FROM User u WHERE u.accountName = ?1"
        ),
        @NamedQuery(
                name = "User.findById",
                query = "SELECT u FROM User u WHERE u.userId = ?1"
        )
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "email")
    private String accountName;
    private String password;
    private boolean isAdmin;

    public User(String accountName, String password, boolean isAdmin) {
        setAccountName(accountName);
        setPassword(password);
        setAdmin(isAdmin);
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return accountName.equals(user.accountName);
    }

    @Override
    public int hashCode() {
        return accountName.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "accountName='" + accountName + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
