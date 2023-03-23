package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import persistence.TransportServiceDao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransportServiceTests {

    @Mock
    private TransportServiceDao transportServiceJPADao;
    @InjectMocks
    private TransportServiceController transportServiceController;

    private Supplier supplier;
    private ContactPerson contactPerson;
    TransportService transportService;

    @Nested
    class AddTests {

        @BeforeEach
        public void setupAddTests() {
            supplier = new Supplier("SupplIT", "contact@supplit.com", "Belgische Silicon Valley", "0499273659", "/images/testImg.jpg", List.of(), List.of(), List.of(), List.of());
        }

        @Test
        public void addTransportService_happyFlow() {
            when(transportServiceJPADao.exists("bpost")).thenReturn(false);
            transportServiceController.addTransportService("bpost", List.of(contactPerson), 10, false, "test", "POST_CODE", true, supplier.getSupplierId());
        }

        @Test
        public void addTransportService_nameNotUnique_throwsIllegalArgumentException() {
            contactPerson = new ContactPerson("test@mail.com", "0477982037");
            when(transportServiceJPADao.existsForSupplier("bpost", supplier.getSupplierId())).thenReturn(true);
            assertThrows(IllegalArgumentException.class, () -> transportServiceController.addTransportService("bpost", List.of(contactPerson), 10, false, "test", "POST_CODE", true, supplier.getSupplierId()));
        }

        @Test
        public void addTransportService_contactPersonListEmpty_throwsIllegalArgumentException() {
            when(transportServiceJPADao.exists("bpost")).thenReturn(false);
            assertThrows(IllegalArgumentException.class, () -> transportServiceController.addTransportService("bpost", List.of(), 10, false, "test", "POST_CODE", true, supplier.getSupplierId()));
        }

        @Test
        public void addTransportService_invalidVerificationType_throwsIllegalArgumentException() {
            contactPerson = new ContactPerson("test@mail.com", "0477982037");
            when(transportServiceJPADao.exists("bpost")).thenReturn(false);
            assertThrows(IllegalArgumentException.class, () -> transportServiceController.addTransportService("bpost", List.of(contactPerson), 10, false, "test", "invalid_type", true, supplier.getSupplierId()));
        }
    }

    @Nested
    class UpdateTests {
        @Test
        public void updateService_happyFlow() {
            transportService = new TransportService("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), supplier, true);
            contactPerson = new ContactPerson("email@email.com", "4994233050");

            when(transportServiceJPADao.get(0)).thenReturn(transportService);

            final String name = "new name";
            final List<ContactPerson> contactPersonList = List.of(contactPerson);
            final int characterCount = 14;
            final boolean integersOnly = true;
            final String prefix = "new";
            final String verificationTypeValue = "POST_CODE";
            final boolean isActive = false;

            transportServiceController.updateTransportService(0, name, contactPersonList, characterCount, integersOnly, prefix, verificationTypeValue, isActive);

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
            final List<ContactPerson> contactPersonList = List.of();
            final int characterCount = 14;
            final boolean integersOnly = true;
            final String prefix = "new";
            final String verificationTypeValue = "POST_CODE";
            final boolean isActive = false;

            assertThrows(IllegalArgumentException.class, () -> transportServiceController.updateTransportService(0, name, contactPersonList, characterCount, integersOnly, prefix, verificationTypeValue, isActive));
        }
    }
}
