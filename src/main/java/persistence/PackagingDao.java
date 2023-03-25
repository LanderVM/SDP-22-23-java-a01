package persistence;

import domain.Packaging;

public interface PackagingDao extends GenericDao<Packaging> {

    void delete(int packagingId);
}
