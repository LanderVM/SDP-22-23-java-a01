package persistence;

import java.util.List;

import domain.TransportService;


public interface TransportServiceDao extends GenericDao<TransportService> {

    void insert(TransportService transportService);

    boolean exists(String name);
    
    boolean existsForSupplier (String name, int supplierId);

    TransportService get(String name);
    
    TransportService getForSupplier(String name,int supplierId);

	List<String> getAllNames();
	
	List<String> getAllNamesForSupplier(int supplierId);
	
	List<TransportService> getAllForSupplier (int supplierId);
}
