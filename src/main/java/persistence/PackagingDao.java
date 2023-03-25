package persistence;

import domain.Packaging;

import java.util.List;

public interface PackagingDao extends GenericDao<Packaging> {

    void delete(int packagingId);

    List<Packaging> getAll(int supplierId);

    void add(Packaging packaging);

    boolean exists(String name, int supplierId);
}
