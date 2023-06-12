package domain;

import gui.view.PackagingDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import persistence.PackagingDao;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PackagingTest {

    @Mock
    PackagingDao packagingDao;
    @Mock
    UserController userController;
    PackagingController packagingController;

    Supplier supplier;
    Packaging packaging;

    @Nested
    public class AddPackagingTests {
        @BeforeEach
        public void setup() {
            supplier = new Supplier("Tim CO", "tim@mail.com", "Belgie", "Brussel", "1210", "Koningsstraat", 236, "B", "0426343211", "/images/testImg.jpg");
            packagingController = new PackagingController(packagingDao, userController, new ArrayList<>());
        }

        @ParameterizedTest
        @CsvSource({
                "Klein, 2.0, 3.5, 4.0, 6.00, STANDARD, true",
                "Middel, 4.0, 7, 8, 10.00, STANDARD, true",
                "Groot, 6.0, 9, 10, 14.00, STANDARD, true",
                "OudGroot, 29.00, 35.00, 40.00, 30.00, STANDARD, false",
                "Custom, 27, 35, 4, 20.00, CUSTOM, true",
                "OudCustom, 2.0, 3.5, 4.0, 12, CUSTOM, false"
        })
        public void addPackaging_happyFlow(String name, double width, double height, double length, double price, String packagingType, boolean active) {
            when(userController.getSupplier()).thenReturn(supplier);
            packagingController.addPackaging(name, width, height, length, price, packagingType, active);
        }

        @ParameterizedTest
        @CsvSource({
                " , 2.0, 3.5, 4.0, 6.00, STANDARD, true", // invalid name
                ", 2.0, 3.5, 4.0, 6.00, STANDARD, true",
                "Klein, -1.0, 3.5, 4.0, 6.00, STANDARD, true", // invalid dimensions
                "Klein, 2.0, -3.5, 4.0, 6.00, STANDARD, true",
                "Klein, 2.0, 3.5, -4.0, 6.00, STANDARD, true",
                "Klein, 2.0, 3.5, 4.0, -1, STANDARD, true", // invalid price
                "Klein, 2.0, 3.5, 4.0, 0, STANDARD, true",
                "Klein, 2.0, 3.5, 4.0, 0,, true", // invalid PackagingType
                "Klein, 2.0, 3.5, 4.0, 0, , true",
                "Klein, 2.0, 3.5, 4.0, 0, invalid, true",
        })
        public void addPackaging_invalidArguments_throwsIllegalArgumentException(String name, double width, double height, double length, double price, String packagingType, boolean active) {
            assertThrows(IllegalArgumentException.class, () -> packagingController.addPackaging(name, width, height, length, price, packagingType, active));
        }

        @Test
        public void addPackaging_nameAlreadyExists_throwsIllegalArgumentException() {
            when(packagingDao.exists("Klein", userController.supplierIdFromUser())).thenReturn(true);
            assertThrows(IllegalArgumentException.class, () -> packagingController.addPackaging("Klein", 2.0, 3.5, 4.0, 6.00, "STANDARD", true));
        }
    }

    @Nested
    public class UpdatePackagingTests {
        @BeforeEach
        public void setup() {
            supplier = new Supplier("Tim CO", "tim@mail.com", "Belgie", "Brussel", "1210", "Koningsstraat", 236, "B", "0426343211", "/images/testImg.jpg");
            packaging = new Packaging("name", 2.0, 3.0, 4.0, 2.0, PackagingType.STANDARD, true, supplier);
            packagingController = new PackagingController(packagingDao, userController, new ArrayList<>(List.of(new PackagingDTO(packaging))));
        }

        @ParameterizedTest
        @CsvSource({
                "Klein, 2.0, 3.5, 4.0, 6.00, STANDARD, true",
                "Middel, 4.0, 7, 8, 10.00, STANDARD, true",
                "Groot, 6.0, 9, 10, 14.00, STANDARD, true",
                "OudGroot, 29.00, 35.00, 40.00, 30.00, STANDARD, false",
                "Custom, 27, 35, 4, 20.00, CUSTOM, true",
                "OudCustom, 2.0, 3.5, 4.0, 12, CUSTOM, false"
        })
        public void updatePackaging_happyFlow(String name, double width, double height, double length, double price, String packagingType, boolean active) {
            when(packagingDao.get(1)).thenReturn(packaging);
            packagingController.updatePackaging(1, name, width, height, length, price, packagingType, active);
        }

        @ParameterizedTest
        @CsvSource({
                " , 2.0, 3.5, 4.0, 6.00, STANDARD, true", // invalid name
                ", 2.0, 3.5, 4.0, 6.00, STANDARD, true",
                "Klein, -1.0, 3.5, 4.0, 6.00, STANDARD, true", // invalid dimensions
                "Klein, 2.0, -3.5, 4.0, 6.00, STANDARD, true",
                "Klein, 2.0, 3.5, -4.0, 6.00, STANDARD, true",
                "Klein, 2.0, 3.5, 4.0, -1, STANDARD, true", // invalid price
                "Klein, 2.0, 3.5, 4.0, 0, STANDARD, true",
        })
        public void updatePackaging_invalidFields_throwsIllegalArgumentException(String name, double width, double height, double length, double price, String packagingType, boolean active) {
            when(packagingDao.get(1)).thenReturn(packaging);
            assertThrows(IllegalArgumentException.class, () -> packagingController.updatePackaging(1, name, width, height, length, price, packagingType, active));
        }

        @ParameterizedTest
        @CsvSource({
                "Klein, 2.0, 3.5, 4.0, 0,, true",
                "Klein, 2.0, 3.5, 4.0, 0, , true",
                "Klein, 2.0, 3.5, 4.0, 0, invalid, true",
        })
        public void updatePackaging_invalidPackagingType_throwsIllegalArgumentException(String name, double width, double height, double length, double price, String packagingType, boolean active) {
            assertThrows(IllegalArgumentException.class, () -> packagingController.updatePackaging(1, name, width, height, length, price, packagingType, active));
        }


        @Test
        public void updatePackaging_sameNameSameObject_happyFlow() {
            when(packagingDao.get("Klein", userController.supplierIdFromUser())).thenReturn(List.of(packaging));
            when(packagingDao.get(1)).thenReturn(packaging);
            packagingController.updatePackaging(1, "Klein", 2.0, 3.5, 4.0, 6.00, "STANDARD", true);
        }

        @Mock
        Packaging testPackaging;

        @Test
        public void updatePackaging_sameNameDifferentObject_throwsIllegalArgumentException() {
            when(packagingDao.get("Klein", userController.supplierIdFromUser())).thenReturn(List.of(testPackaging));
            when(packagingDao.get(1)).thenReturn(packaging);
            assertThrows(IllegalArgumentException.class, () -> packagingController.updatePackaging(1, "Klein", 2.0, 3.5, 4.0, 6.00, "STANDARD", true));
        }
    }
}