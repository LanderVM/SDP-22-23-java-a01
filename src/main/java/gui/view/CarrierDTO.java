package gui.view;

import domain.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CarrierDTO {

    private final SimpleIntegerProperty carrierId;
    private final SimpleStringProperty name;
    private final SimpleListProperty<ContactPersonDTO> contactPeople;
    private final SimpleIntegerProperty characterCount;
    private final SimpleBooleanProperty integersOnly;
    private final SimpleStringProperty prefix;
    private final SimpleObjectProperty<VerificationType> verificationType;
    private final SimpleBooleanProperty active;

    public CarrierDTO(Carrier carrier) {
        carrierId = new SimpleIntegerProperty(carrier.getCarrierId());
        name = new SimpleStringProperty(carrier.getName());
        contactPeople = new SimpleListProperty<>(carrier.getContactPersonList().stream().map(ContactPersonDTO::new).collect(Collectors.toCollection(FXCollections::observableArrayList)));
        TrackingCodeDetails trackingCodeDetails = carrier.getTrackingCodeDetails();
        characterCount = new SimpleIntegerProperty(trackingCodeDetails.getCharacterCount());
        integersOnly = new SimpleBooleanProperty(trackingCodeDetails.isIntegersOnly());
        prefix = new SimpleStringProperty(trackingCodeDetails.getPrefix());
        verificationType = new SimpleObjectProperty<>(trackingCodeDetails.getVerificationType());
        active = new SimpleBooleanProperty(carrier.isActive());
    }

    public int getCarrierId() {
        return carrierId.get();
    }

    public SimpleIntegerProperty carrierIdProperty() {
        return carrierId;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public ObservableList<ContactPersonDTO> getContactPeople() {
        return contactPeople.get();
    }

    public SimpleListProperty<ContactPersonDTO> contactPeopleProperty() {
        return contactPeople;
    }

    public int getCharacterCount() {
        return characterCount.get();
    }

    public SimpleIntegerProperty characterCountProperty() {
        return characterCount;
    }

    public boolean isIntegersOnly() {
        return integersOnly.get();
    }

    public SimpleBooleanProperty integersOnlyProperty() {
        return integersOnly;
    }

    public String getPrefix() {
        return prefix.get();
    }

    public SimpleStringProperty prefixProperty() {
        return prefix;
    }

    public VerificationType getVerificationType() {
        return verificationType.get();
    }

    public SimpleObjectProperty<VerificationType> verificationTypeProperty() {
        return verificationType;
    }

    public boolean isActive() {
        return active.get();
    }

    public SimpleStringProperty activeProperty() {
        return new SimpleStringProperty(isActive() ? "Active" : "Disabled");
    }
    
    public static ObservableList<String> getVerficationTypesObservableList () {
    	return FXCollections.observableList(Arrays.stream(VerificationType.values()).map(VerificationType::name)
				.collect(Collectors.toList()));
    }
}
