package gui.view;

import domain.Roles;
import domain.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.stream.Collectors;

public class UserDTO {


    private final SimpleIntegerProperty employeeId;
    private final SimpleStringProperty accountName;
    private final SimpleStringProperty surname;
    private final SimpleStringProperty name;
    private final SimpleStringProperty telephone;
    private final SimpleStringProperty password;
    private final SimpleStringProperty street;
    private final SimpleIntegerProperty houseNumber;
    private final SimpleStringProperty box;
    private final SimpleStringProperty city;
    private final SimpleStringProperty postalCode;
    private final SimpleStringProperty country;
    private final SimpleStringProperty function;
    private final SimpleStringProperty fullName;


    public UserDTO(User user) {
        employeeId = new SimpleIntegerProperty(user.getUserId());
        accountName = new SimpleStringProperty(user.getAccountName());
        surname = new SimpleStringProperty(user.getSurname());
        name = new SimpleStringProperty(user.getName());
        telephone = new SimpleStringProperty(user.getTelephone());
        password = new SimpleStringProperty(user.getPassword());
        street = new SimpleStringProperty(user.getAddress());
        houseNumber = new SimpleIntegerProperty(user.getHouseNumber());
        box = new SimpleStringProperty(user.getBox());
        city = new SimpleStringProperty(user.getCity());
        postalCode = new SimpleStringProperty(user.getPostalCode());
        country = new SimpleStringProperty(user.getCountry());
        function = new SimpleStringProperty(user.isAdmin() ? "Admin" : "Storeperson");
        fullName = new SimpleStringProperty(user.getName() + " " + user.getSurname());
    }

    public SimpleStringProperty getAccountNameProperty() {
        return accountName;
    }

    public String getAccountName() {
        return accountName.get();
    }

    public String getSurname() {
        return surname.get();
    }
    
    public SimpleStringProperty getFullNameProperty() {
        return fullName;
    }

    public int getEmployeeId() {
        return employeeId.get();
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty functionProperty() {
        return function;
    }

    public String getFunction() {
        return function.get();
    }

    public String getTelephone() {
        return telephone.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getStreet() {
        return street.get();
    }

    public String getHouseNumber() {
        return String.valueOf(houseNumber.get());
    }

    public String getBox() {
        return box.get();
    }

    public String getCity() {
        return city.get();
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public String getCountry() {
        return country.get();
    }

    public static ObservableList<String> getRolesObservableList() {
        return FXCollections.observableList(Arrays.stream(Roles.values()).map(Roles::toString).collect(Collectors.toList()));
    }
}