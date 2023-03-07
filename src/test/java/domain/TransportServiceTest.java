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
    TypedQuery<NewTransportService> query;
    @Mock
    EntityTransaction entityTransaction;

    private TransportServiceJPADao transportServiceJPADao;

    private NewTransportService transportService;

    private void mockFindAllPosted() {
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(transportService);
    }

    @Test
    public void getById_happyFlow() {
        transportService = new NewTransportService(List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), true);

        when(entityManager.createNamedQuery("TransportService.findById", NewTransportService.class)).thenReturn(query);
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(transportService);

        transportServiceJPADao = new TransportServiceJPADao(entityManager);

        assertEquals(transportService, transportServiceJPADao.get(1));
        verify(query).setParameter(1, 1);
    }

    @Test
    public void getById_invalidID_throwsNoResultException() {
        when(entityManager.createNamedQuery("TransportService.findById", NewTransportService.class)).thenReturn(query);
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenThrow(NoResultException.class);

        transportServiceJPADao = new TransportServiceJPADao(entityManager);

        assertThrows(NoResultException.class, () -> transportServiceJPADao.get(1));
        verify(query).setParameter(1, 1);
    }

    @Test
    public void getAll_happyFlow() {
        List<NewTransportService> transportServiceList =
                List.of(new NewTransportService(List.of(), new TrackingCodeDetails(13, true, "32", VerificationType.POST_CODE), true),
                        new NewTransportService(List.of(), new TrackingCodeDetails(9, false, "postnlprefix", VerificationType.ORDER_ID), false)
                );

        when(entityManager.createNamedQuery("TransportService.findAll", NewTransportService.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(transportServiceList);

        transportServiceJPADao = new TransportServiceJPADao(entityManager);

        assertEquals(transportServiceList, transportServiceJPADao.getAll());
        verify(query).getResultList();
    }

    @Test
    public void getAllActive_happyFlow() {
        List<NewTransportService> transportServiceList =
                List.of(new NewTransportService(List.of(), new TrackingCodeDetails(13, true, "32", VerificationType.POST_CODE), true),
                        new NewTransportService(List.of(), new TrackingCodeDetails(9, false, "postnlprefix", VerificationType.ORDER_ID), true)
                );
        when(entityManager.createNamedQuery("TransportService.findAllActive", NewTransportService.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(transportServiceList);

        transportServiceJPADao = new TransportServiceJPADao(entityManager);

        assertEquals(transportServiceList, transportServiceJPADao.getAllActive());
        verify(query).getResultList();
    }

}
