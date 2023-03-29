package logoMapper;

import application.StartUp;
import domain.Logo;
import domain.Supplier;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Objects;

public class LogoMapper {

    public static Logo makeLogo(String location, Supplier supplier) {
        return new Logo(fileToByteArray(location), supplier);
    }

    private static byte[] fileToByteArray(String location) {
        byte[] fileContent = null;
        try {
            File fi = new File(Objects.requireNonNull(StartUp.class.getResource(location)).toURI());
            fileContent = Files.readAllBytes(fi.toPath());
        } catch (IOException | URISyntaxException exception) {
            exception.printStackTrace();
        }
        return fileContent;
    }
}
