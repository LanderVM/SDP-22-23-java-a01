package persistence;

import java.util.List;

import domain.Carrier;


public interface CarrierDao extends GenericDao<Carrier> {

    void insert(Carrier carrier);

    boolean exists(String name);
    
    boolean existsForSupplier (String name, int supplierId);

    Carrier get(String name);
    
    Carrier getForSupplier(String name, int supplierId);

	List<String> getAllNamesForSupplier(int supplierId);
	
	List<Carrier> getAllForSupplier (int supplierId);
}
