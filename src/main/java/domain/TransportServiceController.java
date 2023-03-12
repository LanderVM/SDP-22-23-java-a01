package domain;

import persistence.TransportServiceJPADao;

import java.util.List;

public class TransportServiceController {

    private final TransportServiceJPADao transportServiceJPADao;

    public TransportServiceController(TransportServiceJPADao transportServiceJPADao) {
        this.transportServiceJPADao = transportServiceJPADao;
    }

    public List<String> getTransportServices() {
        return transportServiceJPADao.getAll().stream().map(TransportService::getName).toList();
    }

    public void addTransportService(String name, List<ContactPerson> contactPersonList, int characterCount, boolean isIntegersOnly, String prefix, String verificationTypeValue, boolean isActive) {
        if (getTransportServices().contains(name))
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
}