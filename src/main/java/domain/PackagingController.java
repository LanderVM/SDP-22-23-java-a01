package domain;

import gui.view.PackagingDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.PackagingDao;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PackagingController {

    private final PackagingDao packagingDao;
    private final UserController userController;
    private ObservableList<PackagingDTO> packagingList = FXCollections.emptyObservableList();
    private int supplierId = -1;

    public PackagingController(PackagingDao packagingDao, UserController userController) {
        this.packagingDao = packagingDao;
        this.userController = userController;
    }

    public PackagingController(PackagingDao packagingDao, UserController userController, List<PackagingDTO> packagingList) { // used in Tests
        this.packagingDao = packagingDao;
        this.userController = userController;
        this.packagingList = FXCollections.observableList(packagingList);
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
        if (!PackagingType.exists(packagingType)) throw new IllegalArgumentException("Packaging type does not exist!");
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
        if (packagingId <= 0) throw new IllegalArgumentException("Packaging ID must not be 0 or negative!");
        validatePackagingType(packagingType);

        Packaging packaging = packagingDao.get(packagingId);
        List<Packaging> existingNamePackaging = packagingDao.get(name, userController.supplierIdFromUser());
        if (!existingNamePackaging.isEmpty() && existingNamePackaging.stream().map(Packaging::getPackagingId).noneMatch(potentialMatchingIds -> potentialMatchingIds == packaging.getPackagingId()))
            throw new IllegalArgumentException("Packaging type with this name already exists!");

        packaging.setName(name);
        packaging.setWidth(width);
        packaging.setHeight(height);
        packaging.setLength(length);
        packaging.setPrice(price);
        packaging.setType(PackagingType.valueOf(packagingType));
        packaging.setActive(active);

        packagingDao.update(packaging);
        packagingList.set(getIndex(packaging.getPackagingId()), new PackagingDTO(packaging));
    }

    private int getIndex(int packagingId) {
        List<PackagingDTO> correspondingDTOs = packagingList.stream().filter(dto -> dto.getPackagingId() == packagingId).toList();
        if (correspondingDTOs.isEmpty())
            throw new IllegalArgumentException("There is no PackagingDTO matching this packagingId!");
        if (correspondingDTOs.size() > 1)
            throw new RuntimeException("There were multiple PackagingDTOs found matching this packagingId!");
        return packagingList.indexOf(correspondingDTOs.get(0));
    }


    public static ObservableList<String> getPackagingTypesObservableList() {
        return FXCollections.observableList(Arrays.stream(PackagingType.values()).map(PackagingType::name).collect(Collectors.toList()));
    }
}