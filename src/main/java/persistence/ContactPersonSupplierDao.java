package persistence;

import domain.ContactPersonSupplier;

import java.util.List;

public interface ContactPersonSupplierDao extends GenericDao<ContactPersonSupplier> {

    List<ContactPersonSupplier> getAllForSupplier (String email);
}
