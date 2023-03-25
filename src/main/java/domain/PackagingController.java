package domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.PackagingDao;

public class PackagingController {

    private final PackagingDao packagingDao;
    private final Supplier supplier;
    private final ObservableList<Packaging> packagingList;

    public PackagingController(PackagingDao packagingDao, Supplier supplier) {
        this.packagingDao = packagingDao;
        this.supplier = supplier;
        this.packagingList = FXCollections.observableList(packagingDao.getAll());
    }

    public ObservableList<Packaging> getPackagingList() {
        return this.packagingList;
    }

    private void validatePackagingType(String packagingType) {
        if (packagingType == null || packagingType.isBlank() || packagingType.isEmpty())
            throw new IllegalArgumentException("Packaging type must not be empty!");
        if (!PackagingType.exists(packagingType))
            throw new IllegalArgumentException("Packaging type does not exist!");
    }

    public void addPackaging(String name, double width, double height, double length, double price, String packagingType, boolean active) {
        validatePackagingType(packagingType);
        if (packagingDao.exists(name))
            throw new IllegalArgumentException("Packaging type with this name already exists!");

        Packaging packaging = new Packaging(name, width, height, length, price, PackagingType.valueOf(packagingType), active, supplier);
        packagingDao.add(packaging);
        packagingList.add(packaging);
    }

    public void updatePackaging(int packagingId, String name, double width, double height, double length, double price, String packagingType, boolean active) {
        if (packagingId <= 0)
            throw new IllegalArgumentException("Packaging ID must not be 0 or negative!");
        validatePackagingType(packagingType);

        Packaging packaging = packagingDao.get(packagingId);
        Packaging existingNamePackaging = packagingDao.get(name);
        if (existingNamePackaging != null && !existingNamePackaging.equals(packaging))
            throw new IllegalArgumentException("Packaging type with this name already exists!");

        packaging.setName(name);
        packaging.setWidth(width);
        packaging.setHeight(height);
        packaging.setLength(length);
        packaging.setPrice(price);
        packaging.setType(PackagingType.valueOf(packagingType));
        packaging.setActive(active);

        packagingDao.update(packaging);
    }

    public void deletePackaging(int packagingId) {
        if (packagingId <= 0)
            throw new IllegalArgumentException("Packaging ID must not be 0 or negative!");
        if (!packagingDao.exists(packagingId))
            throw new IllegalArgumentException("Packaging with provided ID does not exist!");
        packagingDao.delete(packagingId);
    }
}