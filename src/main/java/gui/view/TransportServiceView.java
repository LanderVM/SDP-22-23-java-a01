package gui.view;

import domain.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.Collectors;

public class TransportServiceView {

    private final SimpleIntegerProperty transportServiceId;
    private final SimpleStringProperty name;
    private final SimpleListProperty<ContactPersonView> contactPeople; // TODO ContactpersonView
    private final SimpleIntegerProperty characterCount;
    private final SimpleBooleanProperty integersOnly;
    private final SimpleStringProperty prefix;
    private final SimpleObjectProperty<VerificationType> verificationType;
    private final SimpleBooleanProperty active;

    public TransportServiceView(TransportService transportService) {
        transportServiceId = new SimpleIntegerProperty(transportService.getTransportServiceId());
        name = new SimpleStringProperty(transportService.getName());
        contactPeople = new SimpleListProperty<>(transportService.getContactPersonList().stream().map(ContactPersonView::new).collect(Collectors.toCollection(FXCollections::observableArrayList)));
        TrackingCodeDetails trackingCodeDetails = transportService.getTrackingCodeDetails();
        characterCount = new SimpleIntegerProperty(trackingCodeDetails.getCharacterCount());
        integersOnly = new SimpleBooleanProperty(trackingCodeDetails.isIntegersOnly());
        prefix = new SimpleStringProperty(trackingCodeDetails.getPrefix());
        verificationType = new SimpleObjectProperty<>(trackingCodeDetails.getVerificationType());
        active = new SimpleBooleanProperty(transportService.isActive());
    }

    public int getTransportServiceId() {
        return transportServiceId.get();
    }

    public SimpleIntegerProperty transportServiceIdProperty() {
        return transportServiceId;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public ObservableList<ContactPersonView> getContactPeople() {
        return contactPeople.get();
    }

    public SimpleListProperty<ContactPersonView> contactPeopleProperty() {
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

    public SimpleBooleanProperty activeProperty() {
        return active;
    }
}
