package domain;

import java.util.Arrays;
import java.util.stream.Collectors;

import gui.view.PackagingDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.PackagingDao;

public class PackagingController {

    private final PackagingDao packagingDao;
    private final UserController userController;
    private ObservableList<PackagingDTO> packagingList = FXCollections.emptyObservableList();
    private int supplierId = -1;

    public PackagingController(PackagingDao packagingDao, UserController userController) {
        this.packagingDao = packagingDao;
        this.userController = userController;
    }

    public ObservableList<PackagingDTO> getPackagingList() {
        if (packagingList.isEmpty() || supplierId != userController.supplierIdFromUser()) {
            supplierId = userController.supplierIdFromUser();
            this.packagingList = FXCollections.observableList(packagingDao.getAll(userController.supplierIdFromUser()).stream().map(PackagingDTO::new).collect(Collectors.toList()));
        }
        return packagingList;
    }

    private void validatePackagingType(String packagingType) {
        if (packagingType == null || packagingType.isBlank() || packagingType.isEmpty())
            throw new IllegalArgumentException("Packaging type must not be empty!");
        if (!PackagingType.exists(packagingType))
            throw new IllegalArgumentException("Packaging type does not exist!");
    }

    public void addPackaging(String name, double width, double height, double length, double price, String packagingType, boolean active) {
        validatePackagingType(packagingType);
        if (packagingDao.exists(name, userController.supplierIdFromUser()))
            throw new IllegalArgumentException("Packaging type with this name already exists!");

        Packaging packaging = new Packaging(name, width, height, length, price, PackagingType.valueOf(packagingType), active, userController.getSupplier());
        packagingDao.add(packaging);
        packagingList.add(new PackagingDTO(packaging));
    }

    public void updatePackaging(int packagingId, String name, double width, double height, double length, double price, String packagingType, boolean active) {
        if (packagingId <= 0)
            throw new IllegalArgumentException("Packaging ID must not be 0 or negative!");
        validatePackagingType(packagingType);

        Packaging packaging = packagingDao.get(packagingId);
        Packaging existingNamePackaging = packagingDao.get(name, userController.supplierIdFromUser());
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
        int index = packagingList.indexOf(packagingList.stream().filter(dto -> dto.getPackagingId() == packaging.getPackagingId()).toList().get(0));
        packagingList.set(index, new PackagingDTO(packaging)); // TODO
    }

    public void deletePackaging(int packagingId) {
        if (packagingId <= 0)
            throw new IllegalArgumentException("Packaging ID must not be 0 or negative!");
        if (!packagingDao.exists(packagingId))
            throw new IllegalArgumentException("Packaging with provided ID does not exist!");
        packagingDao.delete(packagingId);
    }
    public static ObservableList<String> getPackagingTypesObservableList () {
    	return FXCollections.observableList(Arrays.stream(PackagingType.values()).map(PackagingType::name)
				.collect(Collectors.toList()));
    }
}