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
}
