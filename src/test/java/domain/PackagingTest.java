package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import persistence.PackagingDao;

import java.math.BigDecimal;


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
    public void addPackaging_happyFlow(String name, double width, double height, double length, String price, String packagingType, boolean active) {
        packagingController.addPackaging(name, width, height, length, new BigDecimal(price), packagingType, active);
    }
}