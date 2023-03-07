package domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import persistence.SupplierJPADao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Objects;

@ExtendWith(MockitoExtension.class)
public class SupplierTest {

    @Mock
    EntityManager entityManager;

    @Mock
    TypedQuery<Supplier> query;

    private SupplierJPADao supplierDao;
    private SupplierController supplierController;

    private Supplier supplier;

    private byte[] getFile() {
        byte[] fileContent = null;
		try {
            File fi = new File(Objects.requireNonNull(getClass().getResource("/gui/HomeAdmin.fxml")).toURI());
			fileContent = Files.readAllBytes(fi.toPath());
        } catch (IOException | URISyntaxException exception) {
			exception.printStackTrace();
		}
        return fileContent;
    }


    @Test
    public void getByEmail_happyFlow() {
        supplier = new Supplier("Test supplier", "testSupplier@mail.com", "testStraat 123", 123456789, getFile());

        when(entityManager.createNamedQuery("Supplier.findByEmail", Supplier.class)).thenReturn(query);
        when(query.setParameter(1, "testSupplier@mail.com")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(supplier);

        supplierDao = new SupplierJPADao(entityManager);

        assertEquals(supplier, supplierDao.getByMail("testSupplier@mail.com"));
        verify(query).setParameter(1, "testSupplier@mail.com");
    }

    @Test
    public void getByEmail_invalidEmail_throwsNoResultException() {
        when(entityManager.createNamedQuery("Supplier.findByEmail", Supplier.class)).thenReturn(query);
        when(query.setParameter(1, "testMail@mail.com")).thenReturn(query);
        when(query.getSingleResult()).thenThrow(NoResultException.class);

        supplierDao = new SupplierJPADao(entityManager);

        assertThrows(NoResultException.class, () -> supplierDao.getByMail("testMail@mail.com"));
        verify(query).setParameter(1, "testMail@mail.com");
    }

    @Test
    public void getById_happyFlow() {
    	supplier = new Supplier("Test supplier", "testSupplier@mail.com", "testStraat 123", 123456789, getFile());

        when(entityManager.createNamedQuery("Supplier.findById", Supplier.class)).thenReturn(query);
        when(query.setParameter(1, "1")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(supplier);

        supplierDao = new SupplierJPADao(entityManager);

        assertEquals(supplier, supplierDao.get("1"));
        verify(query).setParameter(1, "1");
    }

    @Test
    public void getById_invalidId_throwsNoResultException() {
        when(entityManager.createNamedQuery("Supplier.findById", Supplier.class)).thenReturn(query);
        when(query.setParameter(1, "1")).thenReturn(query);
        when(query.getSingleResult()).thenThrow(NoResultException.class);

        supplierDao = new SupplierJPADao(entityManager);

        assertThrows(NoResultException.class, () -> supplierDao.get("1"));
        verify(query).setParameter(1, "1");
    }

}
