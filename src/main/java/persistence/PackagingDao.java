package persistence;

import domain.Packaging;

import java.util.List;

public interface PackagingDao extends GenericDao<Packaging> {

    void delete(int packagingId);

    List<Packaging> getAll(int supplierId);
}
