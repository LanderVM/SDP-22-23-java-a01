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
                query = "SELECT u FROM User u WHERE u.emailAddress = ?1"
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
    private String emailAddress = "";
    private String surname = "";
    private String name = "";
    private String password = "";
    private String phoneNumber = "";
    private String address = "";
    private boolean isAdmin = false;
    
    @ManyToOne
    private Supplier supplier;

    public User(String accountName, String password, boolean isAdmin, String surname, String name,Supplier supplier, String phoneNumber, String address) {
        setEmailAddress(accountName);
        setPassword(password);
        setAdmin(isAdmin);
        setSurname(surname);
        setName(name);
        setSupplier(supplier);
        setPhoneNumber(phoneNumber);
        setAddress(address);
    }


    protected User() {
    }

    public int getUserId() {
		return userId;
	}


	public String getEmailAddress() {
        return emailAddress;
    }

    void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setSupplier(Supplier supplier) {
		if(supplier==null)
			throw new IllegalArgumentException("supplier may not be null!");
		this.supplier = supplier;
	}

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return emailAddress.equals(user.emailAddress);
    }

    @Override
    public int hashCode() {
        return emailAddress.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", accountName='" + emailAddress + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
