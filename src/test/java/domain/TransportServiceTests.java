package domain;

import gui.view.TransportServiceView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import persistence.TransportServiceJPADao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransportServiceTests {

//    @Mock
//    EntityManager entityManager;
//
//    @Mock
//    TypedQuery<TransportService> query;
//
//    @Mock
//    private EntityTransaction entityTransaction;
//
//    private TransportServiceJPADao transportServiceJPADao;
//    private TransportServiceController transportServiceController;
//
//    private ContactPerson contactPerson;
//    List<TransportService> transportServiceList;
//    TransportService transportService;
//
//    @Nested
//    class GetTests {
//
//        @Test
//        public void getById_happyFlow() {
//            transportService = new TransportService("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), true);
//
//            when(entityManager.createNamedQuery("TransportService.findById", TransportService.class)).thenReturn(query);
//            when(query.setParameter(1, 1)).thenReturn(query);
//            when(query.getSingleResult()).thenReturn(transportService);
//
//            transportServiceJPADao = new TransportServiceJPADao(entityManager);
//
//            assertEquals(transportService, transportServiceJPADao.get(1));
//            verify(query).setParameter(1, 1);
//        }
//
//        @Test
//        public void getById_invalidID_throwsNoResultException() {
//            when(entityManager.createNamedQuery("TransportService.findById", TransportService.class)).thenReturn(query);
//            when(query.setParameter(1, 1)).thenReturn(query);
//            when(query.getSingleResult()).thenThrow(NoResultException.class);
//
//            transportServiceJPADao = new TransportServiceJPADao(entityManager);
//
//            assertThrows(NoResultException.class, () -> transportServiceJPADao.get(1));
//            verify(query).setParameter(1, 1);
//        }
//
//        @Test
//        public void getAll_happyFlow() {
//            transportServiceList = List.of(
//                    new TransportService("bpost", List.of(), new TrackingCodeDetails(13, true, "32", VerificationType.POST_CODE), true),
//                    new TransportService("postnl", List.of(), new TrackingCodeDetails(9, false, "postnlprefix", VerificationType.ORDER_ID), false)
//            );
//
//            when(entityManager.createNamedQuery("TransportService.findAll", TransportService.class)).thenReturn(query);
//            when(query.getResultList()).thenReturn(transportServiceList);
//
//            transportServiceJPADao = new TransportServiceJPADao(entityManager);
//            transportServiceController = new TransportServiceController(transportServiceJPADao);
//
//            assertEquals(transportServiceList.stream().map(TransportService::getName).toList(), transportServiceController.getTransportServices().stream().map(TransportServiceView::getName).toList());
//            verify(query).getResultList();
//        }
//
//        @Test
//        public void getAllActive_happyFlow() {
//            transportServiceList =
//                    List.of(new TransportService("bpost", List.of(), new TrackingCodeDetails(13, true, "32", VerificationType.POST_CODE), true),
//                            new TransportService("postnl", List.of(), new TrackingCodeDetails(9, false, "postnlprefix", VerificationType.ORDER_ID), true)
//                    );
//            when(entityManager.createNamedQuery("TransportService.findAllActive", TransportService.class)).thenReturn(query);
//            when(query.getResultList()).thenReturn(transportServiceList);
//
//            transportServiceJPADao = new TransportServiceJPADao(entityManager);
//
//            assertEquals(transportServiceList, transportServiceJPADao.getAllActive());
//            verify(query).getResultList();
//        }
//
//        @Test
//        public void findExists_exists_returnsTrue() {
//            transportServiceList = List.of(new TransportService("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), true));
//
//            when(entityManager.createNamedQuery("TransportService.findNameExists", TransportService.class)).thenReturn(query);
//            when(query.setParameter(1, "test")).thenReturn(query);
//            when(query.getResultList()).thenReturn(transportServiceList);
//
//            transportServiceJPADao = new TransportServiceJPADao(entityManager);
//
//            assertTrue(transportServiceJPADao.exists("test"));
//            verify(query).setParameter(1, "test");
//        }
//
//        @Test
//        public void findExists_doesNotExist_returnsFalse() {
//            transportServiceList = List.of();
//
//            when(entityManager.createNamedQuery("TransportService.findNameExists", TransportService.class)).thenReturn(query);
//            when(query.setParameter(1, "test")).thenReturn(query);
//            when(query.getResultList()).thenReturn(transportServiceList);
//
//            transportServiceJPADao = new TransportServiceJPADao(entityManager);
//
//            assertFalse(transportServiceJPADao.exists("test"));
//            verify(query).setParameter(1, "test");
//        }
//    }
//
//    @Nested
//    class AddTests {
//
//        @BeforeEach
//        public void setupAddTransportationServiceTests() {
//            contactPerson = new ContactPerson("test@mail.com", "0477982037");
//            transportServiceList =
//                    List.of(new TransportService("bpost", List.of(), new TrackingCodeDetails(13, true, "32", VerificationType.POST_CODE), true),
//                            new TransportService("postnl", List.of(), new TrackingCodeDetails(9, false, "postnlprefix", VerificationType.ORDER_ID), false)
//                    );
//            when(entityManager.createNamedQuery("TransportService.findNameExists", TransportService.class)).thenReturn(query);
//            when(query.getResultList()).thenReturn(transportServiceList);
//
//            transportServiceJPADao = new TransportServiceJPADao(entityManager);
//            transportServiceController = new TransportServiceController(transportServiceJPADao);
//        }
//
//        @Test
//        public void addTransportService_happyFlow() {
//            transportServiceList = List.of();
//            when(entityManager.getTransaction()).thenReturn(entityTransaction);
//            when(query.setParameter(1, "test")).thenReturn(query);
//            when(query.getResultList()).thenReturn(transportServiceList);
//
//            transportServiceController.addTransportService("test", List.of(contactPerson), 10, false, "test", "POST_CODE", true);
//
//            verify(query).getResultList();
//            verify(entityManager, times(2)).getTransaction();
//        }
//
//        @Test
//        public void addTransportService_nameNotUnique_throwsIllegalArgumentException() {
//            when(query.setParameter(1, "bpost")).thenReturn(query);
//            assertThrows(IllegalArgumentException.class, () -> transportServiceController.addTransportService("bpost", List.of(contactPerson), 10, false, "test", "POST_CODE", true));
//            verify(query).getResultList();
//        }
//
//        @Test
//        public void addTransportService_contactPersonListEmpty_throwsIllegalArgumentException() {
//            when(query.setParameter(1, "test")).thenReturn(query);
//            assertThrows(IllegalArgumentException.class, () -> transportServiceController.addTransportService("test", List.of(), 10, false, "test", "POST_CODE", true));
//            verify(query).getResultList();
//        }
//
//        @Test
//        public void addTransportService_invalidVerificationType_throwsIllegalArgumentException() {
//            when(query.setParameter(1, "test")).thenReturn(query);
//            assertThrows(IllegalArgumentException.class, () -> transportServiceController.addTransportService("test", List.of(), 10, false, "test", "invalid_type", true));
//            verify(query).getResultList();
//        }
//    }
//
//    @Nested
//    class UpdateTransportServiceTests {
//
//        @BeforeEach
//        public void setupUpdateTests() {
//            transportServiceJPADao = new TransportServiceJPADao(entityManager);
//            transportServiceController = new TransportServiceController(transportServiceJPADao);
//
//            lenient().when(entityManager.createNamedQuery("TransportService.findById", TransportService.class)).thenReturn(query);
//            lenient().when(query.setParameter(1, 0)).thenReturn(query);
//        }
//
//        @Test
//        public void updateService_happyFlow() {
//            transportService = new TransportService("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), true);
//            contactPerson = new ContactPerson("email@email.com", "4994233050");
//
//            when(query.getSingleResult()).thenReturn(transportService);
//            when(entityManager.getTransaction()).thenReturn(entityTransaction);
//
//            String name = "new name";
//            List<ContactPerson> contactPersonList = List.of(contactPerson);
//            int characterCount = 14;
//            boolean integersOnly = true;
//            String prefix = "new";
//            String verificationTypeValue = "POST_CODE";
//            boolean isActive = false;
//
//            transportServiceController.updateTransportService(0, name, contactPersonList, characterCount, integersOnly, prefix, verificationTypeValue, isActive);
//
//            TransportService updatedTransportService = transportServiceJPADao.get(0);
//            TrackingCodeDetails updatedTrackingCodeDetails = updatedTransportService.getTrackingCodeDetails();
//            assertEquals(updatedTransportService.getName(), name);
//            assertEquals(updatedTransportService.getContactPersonList(), contactPersonList);
//            assertEquals(updatedTrackingCodeDetails.getCharacterCount(), characterCount);
//            assertEquals(updatedTrackingCodeDetails.isIntegersOnly(), integersOnly);
//            assertEquals(updatedTrackingCodeDetails.getPrefix(), prefix);
//            assertEquals(updatedTrackingCodeDetails.getVerificationType(), VerificationType.valueOf(verificationTypeValue));
//            assertEquals(transportService.isActive(), isActive);
//            verify(query, times(2)).setParameter(1, 0);
//        }
//
//        @Test
//        public void updateService_invalidVerificationType_throwsIllegalArgumentException() {
//            transportService = new TransportService("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), true);
//            contactPerson = new ContactPerson("email@email.com", "4994233050");
//
//            when(query.getSingleResult()).thenReturn(transportService);
//
//            String name = "new name";
//            List<ContactPerson> contactPersonList = List.of(contactPerson);
//            int characterCount = 14;
//            boolean integersOnly = true;
//            String prefix = "new";
//            String verificationTypeValue = "invalid_type";
//            boolean isActive = false;
//
//            assertThrows(IllegalArgumentException.class, () -> transportServiceController.updateTransportService(0, name, contactPersonList, characterCount, integersOnly, prefix, verificationTypeValue, isActive));
//        }
//
//        @Test
//        public void updateService_emptyContactPersonList_throwsIllegalArgumentException() {
//            transportService = new TransportService("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), true);
//
//            String name = "new name";
//            List<ContactPerson> contactPersonList = List.of();
//            int characterCount = 14;
//            boolean integersOnly = true;
//            String prefix = "new";
//            String verificationTypeValue = "POST_CODE";
//            boolean isActive = false;
//
//            assertThrows(IllegalArgumentException.class, () -> transportServiceController.updateTransportService(0, name, contactPersonList, characterCount, integersOnly, prefix, verificationTypeValue, isActive));
//        }
//
//        @Test
//        public void updateActive_happyFlow() {
//            transportService = new TransportService("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), true);
//
//            when(query.getSingleResult()).thenReturn(transportService);
//            when(entityManager.getTransaction()).thenReturn(entityTransaction);
//
//            transportServiceController.setActive(0, true);
//
//            verify(query).setParameter(1, 0);
//
//        }
//
//        @Test
//        public void updateActive_transactionDoesntExist_throwsNoResultException() {
//            when(query.getSingleResult()).thenThrow(NoResultException.class);
//
//            assertThrows(NoResultException.class, () -> transportServiceController.setActive(0, true));
//
//            verify(query).setParameter(1, 0);
//
//        }
//    }
}
