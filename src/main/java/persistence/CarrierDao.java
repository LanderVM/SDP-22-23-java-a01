package persistence;

import domain.Carrier;

import java.util.List;


public interface CarrierDao extends GenericDao<Carrier> {

    void insert(Carrier carrier);

    boolean exists(String name);

    boolean existsForSupplier(String name, int supplierId);

    Carrier get(String name);

    Carrier getForSupplier(String name, int supplierId);

    List<String> getAllNamesForSupplier(int supplierId);

    List<Carrier> getAllForSupplier(int supplierId);
}
