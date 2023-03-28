package persistence;

import domain.Packaging;

import java.util.List;

public interface PackagingDao extends GenericDao<Packaging> {

    List<Packaging> getAll(int supplierId);

    void add(Packaging packaging);

    boolean exists(String name, int supplierId);

    List<Packaging> get(String name, int supplierId);
}
