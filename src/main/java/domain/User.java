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
    private String surname;
    private String name;
    private String password;
    private boolean isAdmin;
    
    @ManyToOne
    private Supplier supplier;

    public User(String accountName, String password, boolean isAdmin, String surname, String name,Supplier supplier) {
        setAccountName(accountName);
        setPassword(password);
        setAdmin(isAdmin);
        setSurname(surname);
        setName(name);
        this.supplier = supplier;
    }


    protected User() {
    }

    public int getUserId() {
		return userId;
	}


	public String getAccountName() {
        return accountName;
    }

    void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getSurname() {
        return surname;
    }

    void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

     void setName(String name) {
        this.name = name;
    }

    public Supplier getSupplier() {
		return supplier;
	}


	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
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
                "userId=" + userId +
                ", accountName='" + accountName + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
