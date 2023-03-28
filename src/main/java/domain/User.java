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
        ),
        @NamedQuery(
        		name = "User.findAllEmployeesForSupplier",
                query = "SELECT u FROM User u WHERE u.supplier.supplierId = ?1"
        ),
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "email")
    private String accountName = "";
    private String surname = "";
    private String name= "";
    private String password = ""; 
    private String country = "";   
    private String city = "";
    @Column(name = "postal_code")
    private String postalCode = "";
    private String address = "";   
    @Column(name = "home_address")
    private int houseNumber = 0;  
    private String box = "";
    private String telephone = "";
    @Column(name = "mobile_phone")
    private String mobilePhone = "";
    private boolean isAdmin = false;
    
    @ManyToOne
    private Supplier supplier;

    public User(String accountName, String password, boolean isAdmin, String surname, String name, String telephone, String mobilePhone,
    		String address, int houseNumber, String box, String city, String postalCode, String country, Supplier supplier) {
        setAccountName(accountName);
        setPassword(password);
        setAdmin(isAdmin);
        setSurname(surname);
        setName(name);
        setTelephone(telephone);
        setMobilePhone(mobilePhone);
        setAddress(address);
        setHouseNumber(houseNumber);
        setBox(box);
        setCity(city);
        setPostalCode(postalCode);       
        setCountry(country);
        setSupplier(supplier);
    }

    protected User() {
    	supplier = new Supplier();
    }

    public int getUserId() {
		return userId;
	}

	public String getAccountName() {
        return accountName;
    }

    void setAccountName(String accountName) {
        if(accountName.isEmpty() || accountName.isBlank() || accountName == "") throw new IllegalArgumentException("Email cannot be empty");
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
    	if(surname.isEmpty() || surname == null || surname.isBlank()) throw new IllegalArgumentException("First name can't be empty");
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

     void setName(String name) {
    	 if(name.isEmpty() || surname.isBlank() || name == null) throw new IllegalArgumentException("Name can't be empty");
        this.name = name;
    }
 
    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBox() {
		return box;
	}

	public void setBox(String box) {
		this.box = box;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		if (houseNumber <= 0) throw new NumberFormatException();	
		this.houseNumber = houseNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
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
		return "User [userId=" + userId + ", accountName=" + accountName + ", surname=" + surname + ", name=" + name
				+ ", password=" + password + ", country=" + country + ", City=" + city + ", postalCode=" + postalCode
				+ ", address=" + address + ", houseNumber=" + houseNumber + ", telephone=" + telephone
				+ ", mobilePhone=" + mobilePhone + ", isAdmin=" + isAdmin + ", supplier=" + supplier + "]";
	}

}