package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import exceptions.EntityDoesntExistException;
import gui.view.ContactPersonView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.SupplierDao;
import persistence.TransportServiceDao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransportServiceTests {

    @Mock
    private TransportServiceDao transportServiceJPADao;
    @Mock
    private SupplierDao supplierDaoJpa;
    @InjectMocks
    private TransportServiceController transportServiceController;

    private Supplier supplier;
    private ContactPersonView contactPersonView;
    TransportService transportService;

    @Nested
    class AddTests {

        @BeforeEach
        public void setupAddTests() {
            supplier = new Supplier("SupplIT", "contact@supplit.com", "Belgische Silicon Valley", "0499273659", "/images/testImg.jpg", List.of(), List.of(), List.of(), List.of());
        }

        @Test
        public void addTransportService_happyFlow() {
        	contactPersonView = new ContactPersonView("test@mail.com", "0477982037");
            when(transportServiceJPADao.existsForSupplier("bpost", 0)).thenReturn(false);
            when(supplierDaoJpa.get(0)).thenReturn(supplier);
            transportServiceController.addTransportService("bpost", FXCollections.observableArrayList(contactPersonView), 10, false, "test", "POST_CODE", true, 0);
        }

        @Test
        public void addTransportService_nameNotUnique_throwsIllegalArgumentException() {
            contactPersonView = new ContactPersonView("test@mail.com", "0477982037");
            when(transportServiceJPADao.existsForSupplier("bpost", 0)).thenReturn(true);
            when(supplierDaoJpa.get(0)).thenReturn(supplier);
            assertThrows(IllegalArgumentException.class, () -> transportServiceController.addTransportService("bpost", FXCollections.observableArrayList(contactPersonView), 10, false, "test", "POST_CODE", true, 0));
        }

        @Test
        public void addTransportService_contactPersonListEmpty_throwsIllegalArgumentException() {
            when(transportServiceJPADao.existsForSupplier("bpost",0)).thenReturn(false);
            when(supplierDaoJpa.get(0)).thenReturn(supplier);
            assertThrows(IllegalArgumentException.class, () -> transportServiceController.addTransportService("bpost", FXCollections.observableArrayList(), 10, false, "test", "POST_CODE", true, 0));
        }

        @Test
        public void addTransportService_invalidVerificationType_throwsIllegalArgumentException() {
            contactPersonView = new ContactPersonView("test@mail.com", "0477982037");
            when(transportServiceJPADao.existsForSupplier("bpost",0)).thenReturn(false);
            when(supplierDaoJpa.get(0)).thenReturn(supplier);
            assertThrows(IllegalArgumentException.class, () -> transportServiceController.addTransportService("bpost", FXCollections.observableArrayList(contactPersonView), 10, false, "test", "invalid_type", true, 0));
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
            transportService = new TransportService("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), supplier, true);
            contactPersonView = new ContactPersonView("email@email.com", "4994233050");

            when(transportServiceJPADao.get(0)).thenReturn(transportService);

            final String name = "new name";
            final ObservableList<ContactPersonView> contactPersonObservableList = FXCollections.observableArrayList(contactPersonView);
            final int characterCount = 14;
            final boolean integersOnly = true;
            final String prefix = "new";
            final String verificationTypeValue = "POST_CODE";
            final boolean isActive = false;
            
            final List<ContactPerson> contactPersonList = List.of(new ContactPerson("email@email.com", "4994233050"));

            try {
				transportServiceController.updateTransportService(0, name, contactPersonObservableList, characterCount, integersOnly, prefix, verificationTypeValue, isActive);
			} catch (EntityDoesntExistException e) {
				e.printStackTrace();
			}

            TransportService updatedTransportService = transportServiceJPADao.get(0);
            TrackingCodeDetails updatedTrackingCodeDetails = updatedTransportService.getTrackingCodeDetails();
            assertEquals(updatedTransportService.getName(), name);
            assertEquals(updatedTransportService.getContactPersonList(), contactPersonList);
            assertEquals(updatedTrackingCodeDetails.getCharacterCount(), characterCount);
            assertEquals(updatedTrackingCodeDetails.isIntegersOnly(), integersOnly);
            assertEquals(updatedTrackingCodeDetails.getPrefix(), prefix);
            assertEquals(updatedTrackingCodeDetails.getVerificationType(), VerificationType.valueOf(verificationTypeValue));
            assertEquals(transportService.isActive(), isActive);
        }

        @Test
        public void updateService_emptyContactPersonList_throwsIllegalArgumentException() {
            transportService = new TransportService("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), supplier, true);

            final String name = "new name";
            final ObservableList<ContactPersonView> contactPersonList = FXCollections.observableArrayList();
            final int characterCount = 14;
            final boolean integersOnly = true;
            final String prefix = "new";
            final String verificationTypeValue = "POST_CODE";
            final boolean isActive = false;

            assertThrows(IllegalArgumentException.class, () -> transportServiceController.updateTransportService(0, name, contactPersonList, characterCount, integersOnly, prefix, verificationTypeValue, isActive));
        }
        
        @Test
        public void updateService_transportServiceIsNull_throwsEntityDoesntExistException() {
        	
        	contactPersonView = new ContactPersonView("email@email.com", "4994233050");
        	transportService = new TransportService("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), supplier, true);
        	
        	final String name = "new name";
            final ObservableList<ContactPersonView> contactPersonList = FXCollections.observableArrayList(contactPersonView);
            final int characterCount = 14;
            final boolean integersOnly = true;
            final String prefix = "new";
            final String verificationTypeValue = "POST_CODE";
            final boolean isActive = false;
            
            when(transportServiceJPADao.get(0)).thenReturn(null);
            
            assertThrows(EntityDoesntExistException.class, () -> transportServiceController.updateTransportService(0, name, contactPersonList, characterCount, integersOnly, prefix, verificationTypeValue, isActive));
        }
    }
}
