package gui.view;

import java.util.Arrays;
import java.util.stream.Collectors;

import domain.Roles;
import domain.User;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserDTO {
	
	
	private final SimpleIntegerProperty employeeId;
    private final SimpleStringProperty accountName;
    private final SimpleStringProperty surname;
    private final SimpleStringProperty name;
    private final SimpleBooleanProperty isAdmin;
    private final SimpleStringProperty telephone;
    private final SimpleStringProperty mobilePhone;
    private final SimpleStringProperty street;
    private final SimpleIntegerProperty houseNumber;
    private final SimpleStringProperty box;
    private final SimpleStringProperty city;
    private final SimpleStringProperty postalCode;
    private final SimpleStringProperty country;
    private final SimpleStringProperty function;
    
    
	public UserDTO(User user) {
		employeeId = new SimpleIntegerProperty(user.getUserId());
		accountName = new SimpleStringProperty(user.getAccountName());
		surname = new SimpleStringProperty(user.getSurname());
		name = new SimpleStringProperty(user.getName());
		isAdmin = new SimpleBooleanProperty(user.isAdmin());
		telephone = new SimpleStringProperty(user.getTelephone());
		mobilePhone = new SimpleStringProperty(user.getMobilePhone());
		street = new SimpleStringProperty(user.getAddress());
		houseNumber = new SimpleIntegerProperty(user.getHouseNumber());
		box = new SimpleStringProperty(user.getBox());
		city = new SimpleStringProperty(user.getCity());
		postalCode = new SimpleStringProperty(user.getPostalCode());
		country = new SimpleStringProperty(user.getCountry());
		function = new SimpleStringProperty(user.isAdmin() ? "Admin" : "Storeperson");

	}

	public SimpleStringProperty getAccountNameProperty() {
		return accountName;
	}
	
	public String getAccountName() {
		return accountName.get();
	}

	public SimpleStringProperty surnameProperty() {
		return surname;
	}
	
	public String getSurname() {
		return surname.get();
	}

	public int getEmployeeId() {
        return employeeId.get();
    }

	public SimpleStringProperty nameProperty() {
		return name;
	}
	
	public String getName() {
        return name.get();
    }

	public SimpleBooleanProperty isAdminProperty() {
		return isAdmin;
	}
	public boolean isAdmin() {
		return isAdmin.get();
	}

	public SimpleStringProperty functionProperty() {
		return function;
	}
	public String getFunction() {
		return function.get();
	}

	public SimpleStringProperty telephoneProperty() {
		return telephone;
	}
	
	public String getTelephone() {
		return telephone.get();
	}

	public SimpleStringProperty mobilePhoneProperty() {
		return mobilePhone;
	}
	
	public String getMobilePhone() {
		return mobilePhone.get();
	}

	public SimpleStringProperty streetProperty() {
		return street;
	}

	public String getStreet() {
		return street.get();
	}

	public SimpleIntegerProperty houseNumberProperty() {
		return houseNumber;
	}	
	public String getHouseNumber() {
		return String.valueOf(houseNumber.get());
	}

	
	public SimpleStringProperty boxProperty() {
		return box;
	}
	public String getBox() {
		return box.get();
	}

	
	public SimpleStringProperty cityProperty() {
		return city;
	}
	public String getCity() {
		return city.get();
	}

	
	public SimpleStringProperty postalCodeProperty() {
		return postalCode;
	}
	public String getPostalCode() {
		return postalCode.get();
	}

	
	public SimpleStringProperty countryProperty() {
		return country;
	}
	public String getCountry() {
		return country.get();
	}
	
	
    public static ObservableList<String> getRolesObservableList() {
    	return FXCollections.observableList(Arrays.stream(Roles.values()).map(Roles::toString)
				.collect(Collectors.toList()));
    }
    

}