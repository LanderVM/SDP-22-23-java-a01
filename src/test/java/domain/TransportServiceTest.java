package domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
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
        List<TransportService> transportServiceList =
                List.of(new TransportService("bpost", List.of(), new TrackingCodeDetails(13, true, "32", VerificationType.POST_CODE), true),
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



    // TODO: test addTransportService, viewTransportService, deactiveTransportService, activeTransportService && all validations

    @Test
    public void add_happyFlow() {
        contactPerson = new ContactPerson("test@mail.com", "0477982037");
        when(entityManager.getTransaction()).thenReturn(entityTransaction);

        List<TransportService> transportServiceList =
                List.of(new TransportService("bpost", List.of(), new TrackingCodeDetails(13, true, "32", VerificationType.POST_CODE), true),
                        new TransportService("postnl", List.of(), new TrackingCodeDetails(9, false, "postnlprefix", VerificationType.ORDER_ID), false)
                );

        when(entityManager.createNamedQuery("TransportService.findAll", TransportService.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(transportServiceList);

        transportServiceJPADao = new TransportServiceJPADao(entityManager);
        transportServiceController = new TransportServiceController(transportServiceJPADao);

        transportServiceController.addTransportService("test", List.of(contactPerson), 10, false, "test", "POST_CODE", true);
        verify(query).getResultList();
        verify(entityManager, times(2)).getTransaction();
    }
}
