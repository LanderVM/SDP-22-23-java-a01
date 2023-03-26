package gui.view;

import java.util.Arrays;
import java.util.stream.Collectors;

import domain.Rolles;
import domain.User;
import jakarta.persistence.Column;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserDTO {
	
	
	private SimpleIntegerProperty employeeId;
    private SimpleStringProperty accountName;
    private SimpleStringProperty surname;
    private SimpleStringProperty name;
    private SimpleStringProperty password;
    private SimpleBooleanProperty isAdmin;
    private SimpleStringProperty telephone;
    private SimpleStringProperty mobilePhone;
    private SimpleStringProperty street;
    private SimpleIntegerProperty houseNumber;
    private SimpleStringProperty box;
    private SimpleStringProperty city;
    private SimpleStringProperty postalCode;
    private SimpleStringProperty country;
    private SimpleStringProperty function;
    
    
	public UserDTO(User user) {
		
		employeeId = new SimpleIntegerProperty(user.getUserId());
		accountName = new SimpleStringProperty(user.getAccountName());
		surname = new SimpleStringProperty(user.getSurname());
		name = new SimpleStringProperty(user.getName());
		password = new SimpleStringProperty(user.getPassword());
		isAdmin = new SimpleBooleanProperty(user.isAdmin());
		telephone = new SimpleStringProperty(user.getTelephone());
		mobilePhone = new SimpleStringProperty(user.getMobilePhone());
		street = new SimpleStringProperty(user.getAddress());
		houseNumber = new SimpleIntegerProperty(user.getHouseNumber());
		box = new SimpleStringProperty(user.getBox());
		city = new SimpleStringProperty(user.getCity());
		postalCode = new SimpleStringProperty(user.getPostalCode());
		country = new SimpleStringProperty(user.getCountry());
		function = new SimpleStringProperty(user.isAdmin() ? "admin" : "magazijnier");
				
	}

	public SimpleStringProperty getAccountNameProperty() {
		return accountName;
	}
	
	public String getAccountName() {
		return accountName.get();
	}

	public SimpleStringProperty getSurnameProperty() {
		return surname;
	}
	
	public String getSurname() {
		return surname.get();
	}

	public int getEmployeeId() {
        return employeeId.get();
    }

	public SimpleStringProperty getNameProperty() {
		return name;
	}
	
	public String getName() {
        return name.get();
    }


	public SimpleStringProperty getPasswordProperty() {
		return password;
	}
	
	public String getPassword() {
		return password.get();
	}


	public SimpleBooleanProperty getIsAdminProperty() {
		return isAdmin;
	}
	public boolean isAdmin() {
		return isAdmin();
	}

	public SimpleStringProperty getFunctionProperty() {
		return function;
	}
	public String getFunction() {
		return function.get();
	}

	public SimpleStringProperty getTelephoneProperty() {
		return telephone;
	}
	
	public String getTelephone() {
		return telephone.get();
	}

	public SimpleStringProperty getMobilePhoneProperty() {
		return mobilePhone;
	}
	
	public String getMobilePhone() {
		return mobilePhone.get();
	}

	public SimpleStringProperty getStreetProperty() {
		return street;
	}

	public String getStreet() {
		return street.get();
	}

	public SimpleIntegerProperty getHouseNumberProperty() {
		return houseNumber;
	}	
	public String getHouseNumber() {
		return String.valueOf(houseNumber.get());
	}

	
	public SimpleStringProperty getBoxProperty() {
		return box;
	}
	public String getBox() {
		return box.get();
	}

	
	public SimpleStringProperty getCityProperty() {
		return city;
	}
	public String getCity() {
		return city.get();
	}

	
	public SimpleStringProperty getPostalCodeProperty() {
		return postalCode;
	}
	public String getPostalCode() {
		return postalCode.get();
	}

	
	public SimpleStringProperty getCountryProperty() {
		return country;
	}
	public String getCountry() {
		return country.get();
	}
	
	
    public static ObservableList<String> getRollesObservableList () {
    	return FXCollections.observableList(Arrays.stream(Rolles.values()).map(Rolles::toString)
				.collect(Collectors.toList()));
    }
    

}