package domain;

import gui.view.TransportServiceView;
import jakarta.persistence.NoResultException;
import persistence.TransportServiceJPADao;

import java.util.Collections;
import java.util.List;

public class TransportServiceController {

    private final TransportServiceJPADao transportServiceJPADao;

    public TransportServiceController(TransportServiceJPADao transportServiceJPADao) {
        this.transportServiceJPADao = transportServiceJPADao;
    }

    public List<String> getTransportServiceNames() {
        return transportServiceJPADao.getAll().stream().map(TransportService::getName).toList();
    }

    public List<TransportServiceView> getTransportServices() {
        return transportServiceJPADao.getAll().stream().map(TransportServiceView::new).toList();
    }

    public void addTransportService(String name, List<ContactPerson> contactPersonList, int characterCount, boolean isIntegersOnly, String prefix, String verificationTypeValue, boolean isActive) {
        if (getTransportServiceNames().contains(name))
            throw new IllegalArgumentException("A TransportService with the name " + name + " already exists!");
        if (contactPersonList.isEmpty())
            throw new IllegalArgumentException("You must add at least one contact person for this Transport Service!");
        transportServiceJPADao.add(
                new TransportService(
                        name,
                        contactPersonList,
                        new TrackingCodeDetails(characterCount, isIntegersOnly, prefix, VerificationType.valueOf(verificationTypeValue)),
                        isActive)
        );
    }

    public void setActive(int transportServiceId, boolean active) throws NoResultException {
        TransportService transportService = transportServiceJPADao.get(transportServiceId);
        transportService.setActive(active);
        transportServiceJPADao.update(transportService);
    }

}