package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import persistence.PackagingDao;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PackagingTest {

    @Mock
    PackagingDao packagingDao;
    PackagingController packagingController;

    Supplier supplier;
    User user;
    Packaging packaging;

    @BeforeEach
    public void setup() {
        supplier = new Supplier("Tim CO", "tim@mail.com", "Timlaan 24 1000 Brussel", "0426343211", "/images/testImg.jpg");
        user = new User("testAdmin@mail.com", "testAdmin", true, "Test", "Admin", supplier, "0479008653", "Delaware HQ");

        packagingController = new PackagingController(packagingDao, user);
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
        when(packagingDao.exists("Klein", supplier.getSupplierId())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> packagingController.addPackaging("Klein", 2.0, 3.5, 4.0, 6.00, "STANDARD", true));
    }
}