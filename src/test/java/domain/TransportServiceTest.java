package domain;

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
public class TransportServiceTest {

    @Mock
    EntityManager entityManager;

    @Mock
    TypedQuery<TransportService> query;

    @Mock
    private EntityTransaction entityTransaction;

    private TransportServiceJPADao transportServiceJPADao;
    private TransportServiceController transportServiceController;

    private TransportService transportService;
    private ContactPerson contactPerson;
    List<TransportService> transportServiceList;

    @Test
    public void getById_happyFlow() {
        transportService = new TransportService("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), true);

        when(entityManager.createNamedQuery("TransportService.findById", TransportService.class)).thenReturn(query);
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(transportService);

        transportServiceJPADao = new TransportServiceJPADao(entityManager);

        assertEquals(transportService, transportServiceJPADao.get(1));
        verify(query).setParameter(1, 1);
    }

    @Test
    public void getById_invalidID_throwsNoResultException() {
        when(entityManager.createNamedQuery("TransportService.findById", TransportService.class)).thenReturn(query);
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenThrow(NoResultException.class);

        transportServiceJPADao = new TransportServiceJPADao(entityManager);

        assertThrows(NoResultException.class, () -> transportServiceJPADao.get(1));
        verify(query).setParameter(1, 1);
    }

    @Test
    public void getAll_happyFlow() {
        transportServiceList = List.of(
                new TransportService("bpost", List.of(), new TrackingCodeDetails(13, true, "32", VerificationType.POST_CODE), true),
                new TransportService("postnl", List.of(), new TrackingCodeDetails(9, false, "postnlprefix", VerificationType.ORDER_ID), false)
        );

        when(entityManager.createNamedQuery("TransportService.findAll", TransportService.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(transportServiceList);

        transportServiceJPADao = new TransportServiceJPADao(entityManager);
        transportServiceController = new TransportServiceController(transportServiceJPADao);

        assertEquals(transportServiceList.stream().map(TransportService::getName).toList(), transportServiceController.getTransportServices());
        verify(query).getResultList();
    }

    @Test
    public void getAllActive_happyFlow() {
        List<TransportService> transportServiceList =
                List.of(new TransportService("bpost", List.of(), new TrackingCodeDetails(13, true, "32", VerificationType.POST_CODE), true),
                        new TransportService("postnl", List.of(), new TrackingCodeDetails(9, false, "postnlprefix", VerificationType.ORDER_ID), true)
                );
        when(entityManager.createNamedQuery("TransportService.findAllActive", TransportService.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(transportServiceList);

        transportServiceJPADao = new TransportServiceJPADao(entityManager);

        assertEquals(transportServiceList, transportServiceJPADao.getAllActive());
        verify(query).getResultList();
    }


    // TODO: viewTransportService, deactiveTransportService, activeTransportService && all validations

    @Nested
    class AddTransactionServiceTests {

        @BeforeEach
        public void setupAddTransportationServiceTests() {
            contactPerson = new ContactPerson("test@mail.com", "0477982037");
            transportServiceList =
                    List.of(new TransportService("bpost", List.of(), new TrackingCodeDetails(13, true, "32", VerificationType.POST_CODE), true),
                            new TransportService("postnl", List.of(), new TrackingCodeDetails(9, false, "postnlprefix", VerificationType.ORDER_ID), false)
                    );

            when(entityManager.createNamedQuery("TransportService.findAll", TransportService.class)).thenReturn(query);
            when(query.getResultList()).thenReturn(transportServiceList);

            transportServiceJPADao = new TransportServiceJPADao(entityManager);
            transportServiceController = new TransportServiceController(transportServiceJPADao);
        }

        @Test
        public void addTransportService_happyFlow() {
            when(entityManager.getTransaction()).thenReturn(entityTransaction);

            transportServiceController.addTransportService("test", List.of(contactPerson), 10, false, "test", "POST_CODE", true);

            verify(query).getResultList();
            verify(entityManager, times(2)).getTransaction();
        }

        @Test
        public void addTransportService_nameNotUnique_throwsIllegalArgumentException() {
            assertThrows(IllegalArgumentException.class, () -> transportServiceController.addTransportService("bpost", List.of(contactPerson), 10, false, "test", "POST_CODE", true));
            verify(query).getResultList();
        }

        @Test
        public void addTransportService_contactPersonListEmpty_throwsIllegalArgumentException() {
            assertThrows(IllegalArgumentException.class, () -> transportServiceController.addTransportService("test", List.of(), 10, false, "test", "POST_CODE", true));
            verify(query).getResultList();
        }

        @Test
        public void addTransportService_invalidVerificationType_throwsIllegalArgumentException() {
            assertThrows(IllegalArgumentException.class, () -> transportServiceController.addTransportService("test", List.of(), 10, false, "test", "invalid_type", true));
            verify(query).getResultList();
        }
    }

}
