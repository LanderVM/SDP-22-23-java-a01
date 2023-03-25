package domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.PackagingDao;

import java.math.BigDecimal;

public class PackagingController {

    private final PackagingDao packagingDao;
    private final User user;
    private ObservableList<Packaging> packagingList;

    public PackagingController(PackagingDao packagingDao, User user) {
        this.packagingDao = packagingDao;
        this.user = user;
        this.packagingList = FXCollections.observableList(packagingDao.getAll(user.getSupplier().getSupplierId()));
    }

    public ObservableList<Packaging> getPackagingList() {
        return this.packagingList;
    }

    public void addPackaging(String name, double width, double height, double length, BigDecimal price, String packagingType, boolean active) {
        if (packagingDao.exists(name, user.getSupplier().getSupplierId()))
            throw new IllegalArgumentException("Packaging type with this name already exists!");
        if (packagingType.isBlank() || packagingType.isEmpty())
            throw new IllegalArgumentException("Packaging type must not be empty!");
        if (!PackagingType.exists(packagingType))
            throw new IllegalArgumentException("Packaging type does not exist!");
        Packaging packaging = new Packaging(name, width, height, length, price, PackagingType.valueOf(packagingType), active, user.getSupplier());
        packagingDao.add(packaging);
        packagingList.add(packaging);
    }

}