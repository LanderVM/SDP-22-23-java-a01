package domain;

import exceptions.EntityDoesntExistException;
import gui.view.CarrierDTO;
import gui.view.ContactPersonDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.CarrierDao;
import persistence.SupplierDao;

import java.util.List;
import java.util.stream.Collectors;

public class CarrierController {

    private final CarrierDao carrierDaoJpa;
    private final SupplierDao supplierDaoJpa;
    private ObservableList<CarrierDTO> carrierViewList = FXCollections.observableArrayList();
    private int supplierId;

    public CarrierController(CarrierDao carrierDaoJpa, SupplierDao supplierDaoJpa) {
        this.carrierDaoJpa = carrierDaoJpa;
        this.supplierDaoJpa = supplierDaoJpa;
    }

    public ObservableList<CarrierDTO> getCarriers(int supplierId) {
        if (carrierViewList.isEmpty() || this.supplierId != supplierId) {
            this.supplierId = supplierId;
            this.carrierViewList = FXCollections.observableList(carrierDaoJpa.getAllForSupplier(supplierId).stream().map(CarrierDTO::new).collect(Collectors.toList()));
        }
        return carrierViewList;
    }

    public ObservableList<String> getCarrierNames(int supplierId) {
        return FXCollections.observableArrayList(carrierDaoJpa.getAllNamesForSupplier(supplierId));
    }

    public void addCarrier(String name, ObservableList<ContactPersonDTO> contactPersonList, int characterCount, boolean isIntegersOnly, String prefix, String verificationTypeValue, boolean isActive, int supplierId) {
        Supplier supplier = supplierDaoJpa.get(supplierId);
        if (carrierDaoJpa.existsForSupplier(name, supplierId))
            throw new IllegalArgumentException("A Carrier with the name " + name + " already exists!");
        if (contactPersonList.isEmpty())
            throw new IllegalArgumentException("You must add at least one contact person for this Carrier!");
        List<ContactPerson> list = contactPersonList.stream().map(el -> new ContactPerson(el.getEmail(), el.getPhoneNumber())).toList();
        TrackingCodeDetails trackingCodeDetails = new TrackingCodeDetails(characterCount, isIntegersOnly, prefix, verificationTypeValue);
        Carrier carrier = new Carrier(name, list, trackingCodeDetails, supplier, isActive);
        carrierDaoJpa.insert(carrier);
        
        int carrierId = carrierDaoJpa.getForSupplier(name, supplierId).getCarrierId();
        
        CarrierDTO carrierDTO = new CarrierDTO(carrier);
        carrierDTO.setCarrierId(carrierId);
        
        carrierViewList.add(carrierDTO);
    }

    public void updateCarrier(int id, String name, ObservableList<ContactPersonDTO> contactPersonList, int characterCount, boolean isIntegersOnly, String prefix, String verificationTypeValue, boolean isActive) throws EntityDoesntExistException {
        if (contactPersonList.isEmpty())
            throw new IllegalArgumentException("You must add at least one contact person for this Carrier!");
        if (characterCount <= 0) throw new IllegalArgumentException("Character count input must a positive number!");
        Carrier carrier = carrierDaoJpa.get(id);
        if (carrier == null) throw new EntityDoesntExistException("Database did not find a Carrier with this id!");

        List<ContactPerson> list = contactPersonList.stream().map(el -> new ContactPerson(el.getEmail(), el.getPhoneNumber())).toList();

        carrier.setName(name);
        carrier.setContactPersonList(list);
        carrier.getTrackingCodeDetails().setCharacterCount(characterCount);
        carrier.getTrackingCodeDetails().setIntegersOnly(isIntegersOnly);
        carrier.getTrackingCodeDetails().setPrefix(prefix);
        carrier.getTrackingCodeDetails().setVerificationType(verificationTypeValue);
        carrier.setActive(isActive);
        
        carrierDaoJpa.update(carrier);
        carrierViewList.set(getIndex(carrier.getCarrierId()), new CarrierDTO(carrier));
    }

    private int getIndex(int carrierId) {
        List<CarrierDTO> correspondingDTOs = carrierViewList.stream().filter(dto -> dto.getCarrierId() == carrierId).toList();
        if (correspondingDTOs.isEmpty())
            throw new IllegalArgumentException("There is no PackagingDTO matching this packagingId!");
        if (correspondingDTOs.size() > 1)
            throw new RuntimeException("There were multiple PackagingDTOs found matching this packagingId!");
        return carrierViewList.indexOf(correspondingDTOs.get(0));
    }

}