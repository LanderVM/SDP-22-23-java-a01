package domain;

import gui.view.ContactPersonView;
import gui.view.TransportServiceView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.SupplierDao;
import persistence.TransportServiceDao;


import java.util.List;
import java.util.stream.Collectors;

import exceptions.EntityDoesntExistException;

public class TransportServiceController {

    private final TransportServiceDao transportServiceDaoJpa;
    private final SupplierDao supplierDaoJpa;
    private ObservableList<TransportServiceView> transportServiceList = FXCollections.emptyObservableList();
    private int supplierId;

    public TransportServiceController(TransportServiceDao transportServiceDaoJpa,SupplierDao supplierDaoJpa) {
        this.transportServiceDaoJpa =  transportServiceDaoJpa;
		this.supplierDaoJpa = supplierDaoJpa;
    }

    public ObservableList<TransportServiceView> getTransportServices(int supplierId) {
        if (transportServiceList.isEmpty() || this.supplierId != supplierId) {
            this.supplierId = supplierId;
            this.transportServiceList = FXCollections.observableList(transportServiceDaoJpa.getAllForSupplier(supplierId).stream().map(TransportServiceView::new).collect(Collectors.toList()));
        }
        return transportServiceList;
    }
    
    public ObservableList<String> getTransportServicesNames(int supplierId) {
    	return FXCollections.observableArrayList(transportServiceDaoJpa.getAllNamesForSupplier(supplierId));
    }

    public void addTransportService(String name, ObservableList<ContactPersonView> contactPersonList, int characterCount, boolean isIntegersOnly, String prefix, String verificationTypeValue, boolean isActive,int supplierId) {
    	Supplier supplier = supplierDaoJpa.get(supplierId); 
        if (transportServiceDaoJpa.existsForSupplier(name,supplierId))
            throw new IllegalArgumentException("A TransportService with the name " + name + " already exists!");
        if (contactPersonList.isEmpty())
            throw new IllegalArgumentException("You must add at least one contact person for this Transport Service!");
        List<ContactPerson> list = contactPersonList.stream().map(el-> new ContactPerson(el.getEmail(),el.getPhoneNumber())).toList();
        TrackingCodeDetails trackingCodeDetails = new TrackingCodeDetails(characterCount, isIntegersOnly, prefix, VerificationType.valueOf(verificationTypeValue));
        TransportService transportService = new TransportService(name, list, trackingCodeDetails, supplier, isActive);
        transportServiceDaoJpa.insert(transportService);
        transportServiceList.add(new TransportServiceView(transportService));
    }

    public void updateTransportService(int id, String name, ObservableList<ContactPersonView> contactPersonList, int characterCount, boolean isIntegersOnly, String prefix, String verificationTypeValue, boolean isActive) throws EntityDoesntExistException {
        if (contactPersonList.isEmpty())
            throw new IllegalArgumentException("You must add at least one contact person for this Carrier!");
        if (characterCount <= 0)
            throw new IllegalArgumentException("Character count input must a positive number!");
        TransportService transportService = transportServiceDaoJpa.get(id);
        if(transportService==null)
        	throw new EntityDoesntExistException("Database did not find a Carrier with this id!");

        List<ContactPerson> list = contactPersonList.stream().map(el-> new ContactPerson(el.getEmail(),el.getPhoneNumber())).toList();
        
        transportService.setName(name);
        transportService.setContactPersonList(list);
        transportService.getTrackingCodeDetails().setCharacterCount(characterCount);
        transportService.getTrackingCodeDetails().setIntegersOnly(isIntegersOnly);
        transportService.getTrackingCodeDetails().setPrefix(prefix);
        transportService.getTrackingCodeDetails().setVerificationType(VerificationType.valueOf(verificationTypeValue));
        transportService.setActive(isActive);

        transportServiceDaoJpa.update(transportService);
        transportServiceList.set(getIndex(transportService.getTransportServiceId()), new TransportServiceView(transportService));
    }

    private int getIndex(int transportServiceId) {
        List<TransportServiceView> correspondingDTOs = transportServiceList.stream().filter(dto -> dto.getTransportServiceId() == transportServiceId).toList();
        if (correspondingDTOs.isEmpty())
            throw new IllegalArgumentException("There is no PackagingDTO matching this packagingId!");
        if (correspondingDTOs.size() > 1)
            throw new RuntimeException("There were multiple PackagingDTOs found matching this packagingId!");
        return transportServiceList.indexOf(correspondingDTOs.get(0));
    }

}