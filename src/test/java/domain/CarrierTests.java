package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import exceptions.EntityDoesntExistException;
import gui.view.ContactPersonDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.SupplierDao;
import persistence.CarrierDao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarrierTests {

    @Mock
    private CarrierDao transportServiceJPADao;
    @Mock
    private SupplierDao supplierDaoJpa;
    @InjectMocks
    private CarrierController carrierController;

    private Supplier supplier;
    private ContactPersonDTO contactPersonDTO;
    Carrier carrier;

    @Nested
    class AddTests {

        @BeforeEach
        public void setupAddTests() {
            supplier = new Supplier("SupplIT", "contact@supplit.com", "Belgische Silicon Valley", "0499273659", "/images/testImg.jpg", List.of(), List.of(), List.of(), List.of());
        }

        @Test
        public void addTransportService_happyFlow() {
        	contactPersonDTO = new ContactPersonDTO("test@mail.com", "0477982037");
            when(transportServiceJPADao.existsForSupplier("bpost", 0)).thenReturn(false);
            when(supplierDaoJpa.get(0)).thenReturn(supplier);
            carrierController.addCarrier("bpost", FXCollections.observableArrayList(contactPersonDTO), 10, false, "test", "POST_CODE", true, 0);
        }

        @Test
        public void addTransportService_nameNotUnique_throwsIllegalArgumentException() {
            contactPersonDTO = new ContactPersonDTO("test@mail.com", "0477982037");
            when(transportServiceJPADao.existsForSupplier("bpost", 0)).thenReturn(true);
            when(supplierDaoJpa.get(0)).thenReturn(supplier);
            assertThrows(IllegalArgumentException.class, () -> carrierController.addCarrier("bpost", FXCollections.observableArrayList(contactPersonDTO), 10, false, "test", "POST_CODE", true, 0));
        }

        @Test
        public void addTransportService_contactPersonListEmpty_throwsIllegalArgumentException() {
            when(transportServiceJPADao.existsForSupplier("bpost",0)).thenReturn(false);
            when(supplierDaoJpa.get(0)).thenReturn(supplier);
            assertThrows(IllegalArgumentException.class, () -> carrierController.addCarrier("bpost", FXCollections.observableArrayList(), 10, false, "test", "POST_CODE", true, 0));
        }

        @Test
        public void addTransportService_invalidVerificationType_throwsIllegalArgumentException() {
            contactPersonDTO = new ContactPersonDTO("test@mail.com", "0477982037");
            when(transportServiceJPADao.existsForSupplier("bpost",0)).thenReturn(false);
            when(supplierDaoJpa.get(0)).thenReturn(supplier);
            assertThrows(IllegalArgumentException.class, () -> carrierController.addCarrier("bpost", FXCollections.observableArrayList(contactPersonDTO), 10, false, "test", "invalid_type", true, 0));
        }
    }

    @Nested
    class UpdateTests {
    	
    	@BeforeEach
        public void setupAddTests() {
            supplier = new Supplier("SupplIT", "contact@supplit.com", "Belgische Silicon Valley", "0499273659", "/images/testImg.jpg", List.of(), List.of(), List.of(), List.of());
        }
    	
        @Test
        public void updateService_happyFlow() {
            carrier = new Carrier("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), supplier, true);
            contactPersonDTO = new ContactPersonDTO("email@email.com", "4994233050");

            when(transportServiceJPADao.get(0)).thenReturn(carrier);

            final String name = "new name";
            final ObservableList<ContactPersonDTO> contactPersonObservableList = FXCollections.observableArrayList(contactPersonDTO);
            final int characterCount = 14;
            final boolean integersOnly = true;
            final String prefix = "new";
            final String verificationTypeValue = "POST_CODE";
            final boolean isActive = false;
            
            final List<ContactPerson> contactPersonList = List.of(new ContactPerson("email@email.com", "4994233050"));

            try {
				carrierController.updateCarrier(0, name, contactPersonObservableList, characterCount, integersOnly, prefix, verificationTypeValue, isActive);
			} catch (EntityDoesntExistException e) {
				e.printStackTrace();
			}

            Carrier updatedCarrier = transportServiceJPADao.get(0);
            TrackingCodeDetails updatedTrackingCodeDetails = updatedCarrier.getTrackingCodeDetails();
            assertEquals(updatedCarrier.getName(), name);
            assertEquals(updatedCarrier.getContactPersonList(), contactPersonList);
            assertEquals(updatedTrackingCodeDetails.getCharacterCount(), characterCount);
            assertEquals(updatedTrackingCodeDetails.isIntegersOnly(), integersOnly);
            assertEquals(updatedTrackingCodeDetails.getPrefix(), prefix);
            assertEquals(updatedTrackingCodeDetails.getVerificationType(), VerificationType.valueOf(verificationTypeValue));
            assertEquals(carrier.isActive(), isActive);
        }

        @Test
        public void updateService_emptyContactPersonList_throwsIllegalArgumentException() {
            carrier = new Carrier("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), supplier, true);

            final String name = "new name";
            final ObservableList<ContactPersonDTO> contactPersonList = FXCollections.observableArrayList();
            final int characterCount = 14;
            final boolean integersOnly = true;
            final String prefix = "new";
            final String verificationTypeValue = "POST_CODE";
            final boolean isActive = false;

            assertThrows(IllegalArgumentException.class, () -> carrierController.updateCarrier(0, name, contactPersonList, characterCount, integersOnly, prefix, verificationTypeValue, isActive));
        }
        
        @Test
        public void updateService_transportServiceIsNull_throwsEntityDoesntExistException() {
        	
        	contactPersonDTO = new ContactPersonDTO("email@email.com", "4994233050");
        	carrier = new Carrier("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), supplier, true);
        	
        	final String name = "new name";
            final ObservableList<ContactPersonDTO> contactPersonList = FXCollections.observableArrayList(contactPersonDTO);
            final int characterCount = 14;
            final boolean integersOnly = true;
            final String prefix = "new";
            final String verificationTypeValue = "POST_CODE";
            final boolean isActive = false;
            
            when(transportServiceJPADao.get(0)).thenReturn(null);
            
            assertThrows(EntityDoesntExistException.class, () -> carrierController.updateCarrier(0, name, contactPersonList, characterCount, integersOnly, prefix, verificationTypeValue, isActive));
        }
    }
}
