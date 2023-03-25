package domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.PackagingDao;

public class PackagingController {

    private final PackagingDao packagingDao;
    private final User user;
    private ObservableList<Packaging> packagingList;

    public PackagingController(PackagingDao packagingDao, User user) {
        this.packagingDao = packagingDao;
        this.user = user;
        this.packagingList = FXCollections.observableList(packagingDao.getAll(user.getSupplier().getSupplierId()));
    }

}