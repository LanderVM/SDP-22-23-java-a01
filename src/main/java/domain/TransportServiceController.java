package domain;

import gui.view.TransportServiceView;
import jakarta.persistence.NoResultException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.TransportServiceDao;


import java.util.List;

public class TransportServiceController {

    private final TransportServiceDao transportServiceDaoJpa;

    public TransportServiceController(TransportServiceDao transportServiceDaoJpa) {
        this.transportServiceDaoJpa =  transportServiceDaoJpa;
    }

    public ObservableList<TransportServiceView> getTransportServices() {
        return FXCollections.observableArrayList(transportServiceDaoJpa.getAll().stream().map(TransportServiceView::new).toList());
    }
    
    public ObservableList<String> getTransportServicesNames () {
    	return FXCollections.observableArrayList(transportServiceDaoJpa.getAllNames());
    }
    
    public TransportService getTransportServiceByName(String name) throws NoResultException {
		return transportServiceDaoJpa.get(name);
	}

    public void addTransportService(String name, List<ContactPerson> contactPersonList, int characterCount, boolean isIntegersOnly, String prefix, String verificationTypeValue, boolean isActive) {
        if (transportServiceDaoJpa.exists(name))
            throw new IllegalArgumentException("A TransportService with the name " + name + " already exists!");
        if (contactPersonList.isEmpty())
            throw new IllegalArgumentException("You must add at least one contact person for this Transport Service!");
        transportServiceDaoJpa.insert(
                new TransportService(
                        name,
                        contactPersonList,
                        new TrackingCodeDetails(characterCount, isIntegersOnly, prefix, VerificationType.valueOf(verificationTypeValue)),
                        isActive)
        );
    }

    public void updateTransportService(int id, String name, List<ContactPerson> contactPersonList, int characterCount, boolean isIntegersOnly, String prefix, String verificationTypeValue, boolean isActive) {
        if (contactPersonList.isEmpty())
            throw new IllegalArgumentException("You must add at least one contact person for this Transport Service!");
        TransportService transportService = transportServiceDaoJpa.get(id);
        transportService.setName(name);
        transportService.setContactPersonList(contactPersonList);
        transportService.getTrackingCodeDetails().setCharacterCount(characterCount);
        transportService.getTrackingCodeDetails().setIntegersOnly(isIntegersOnly);
        transportService.getTrackingCodeDetails().setPrefix(prefix);
        transportService.getTrackingCodeDetails().setVerificationType(VerificationType.valueOf(verificationTypeValue));
        transportService.setActive(isActive);

        transportServiceDaoJpa.update(transportService);
    }

    public void setActive(int transportServiceId, boolean active) throws NoResultException {
        TransportService transportService = transportServiceDaoJpa.get(transportServiceId);
        transportService.setActive(active);
        transportServiceDaoJpa.update(transportService);
    }

}