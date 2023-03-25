package domain;

import gui.view.ContactPersonView;
import gui.view.TransportServiceView;
import jakarta.persistence.NoResultException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.SupplierDao;
import persistence.TransportServiceDao;


import java.util.List;

import exceptions.EntityDoesntExistException;

public class TransportServiceController {

    private final TransportServiceDao transportServiceDaoJpa;
    private final SupplierDao supplierDaoJpa;

    public TransportServiceController(TransportServiceDao transportServiceDaoJpa,SupplierDao supplierDaoJpa) {
        this.transportServiceDaoJpa =  transportServiceDaoJpa;
		this.supplierDaoJpa = supplierDaoJpa;
    }

    public ObservableList<TransportServiceView> getTransportServices(int supplierId) {
        return FXCollections.observableArrayList(transportServiceDaoJpa.getAllForSupplier(supplierId).stream().map(TransportServiceView::new).toList());
    }
    
    public ObservableList<String> getTransportServicesNames (int supplierId) {
    	return FXCollections.observableArrayList(transportServiceDaoJpa.getAllNamesForSupplier(supplierId));
    }
    
    public TransportService getTransportServiceByNameForSupplier(String name,int supplierId) throws NoResultException {
		return transportServiceDaoJpa.getForSupplier(name, supplierId);
	}

    public void addTransportService(String name, List<ContactPerson> contactPersonList, int characterCount, boolean isIntegersOnly, String prefix, String verificationTypeValue, boolean isActive,int supplierId) {
    	Supplier supplier = supplierDaoJpa.get(supplierId); // TODO dit uit andere controller halen, niet zomaar een dao opvragen. Testen zijn hierdoor ook kapot omdat SupplierDAO niet gemockt wordt
        if (transportServiceDaoJpa.existsForSupplier(name,supplierId))
            throw new IllegalArgumentException("A TransportService with the name " + name + " already exists!");
        if (contactPersonList.isEmpty())
            throw new IllegalArgumentException("You must add at least one contact person for this Transport Service!");
        transportServiceDaoJpa.insert(
                new TransportService(
                        name,
                        contactPersonList,
                        new TrackingCodeDetails(characterCount, isIntegersOnly, prefix, VerificationType.valueOf(verificationTypeValue)),supplier,
                        isActive)
        );
    }

    public void updateTransportService(int id, String name, ObservableList<ContactPersonView> contactPersonList, int characterCount, boolean isIntegersOnly, String prefix, String verificationTypeValue, boolean isActive) throws EntityDoesntExistException {
        if (contactPersonList.isEmpty())
            throw new IllegalArgumentException("You must add at least one contact person for this Transport Service!");
        TransportService transportService = transportServiceDaoJpa.get(id);
        if(transportService==null)
        	throw new EntityDoesntExistException("there is no transportService for given transportServiceId");
        
        List<ContactPerson> list = contactPersonList.stream().map(el-> new ContactPerson(el.getEmail(),el.getPhoneNumber())).toList();
        
        transportService.setName(name);
        transportService.setContactPersonList(list);
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