package persistence;

import java.util.List;

import domain.TransportService;


public interface TransportServiceDao extends GenericDao<TransportService> {

    void insert(TransportService transportService);

    boolean exists(String name);

    TransportService get(String name);

	List<String> getAllNames();
}
