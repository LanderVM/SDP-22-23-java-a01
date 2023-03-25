package domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.PackagingDao;

public class PackagingController {

    private final PackagingDao packagingDao;
    private final User user;
    private final ObservableList<Packaging> packagingList;

    public PackagingController(PackagingDao packagingDao, User user) {
        this.packagingDao = packagingDao;
        this.user = user;
        this.packagingList = FXCollections.observableList(packagingDao.getAll(user.getSupplier().getSupplierId()));
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
        if (packagingDao.exists(name, user.getSupplier().getSupplierId()))
            throw new IllegalArgumentException("Packaging type with this name already exists!");

        Packaging packaging = new Packaging(name, width, height, length, price, PackagingType.valueOf(packagingType), active, user.getSupplier());
        packagingDao.add(packaging);
        packagingList.add(packaging);
    }

    public void updatePackaging(int packagingId, String name, double width, double height, double length, double price, String packagingType, boolean active) {
        if (packagingId <= 0)
            throw new IllegalArgumentException("Packaging ID must not be 0 or negative!");
        validatePackagingType(packagingType);

        Packaging packaging = packagingDao.get(packagingId);
        Packaging existingNamePackaging = packagingDao.get(name, user.getSupplier().getSupplierId());
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
//        packagingList.set(index, packaging);
    }
}