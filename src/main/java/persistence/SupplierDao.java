package persistence;

import domain.Supplier;

public interface SupplierDao extends GenericDao<Supplier> {

    Supplier get(String email);
}
