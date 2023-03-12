package logoMapper;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Objects;

import javax.imageio.ImageIO;

import application.StartUp;
import domain.Logo;
import domain.Supplier;

public class LogoMapper {

	public static Logo makeLogo(String location, Supplier supplier) {
		Logo logo = new Logo(fileToByteArray(location), supplier);
		return logo;
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

	public static byte[] extractBytesFromImg(File img) throws IOException {
		BufferedImage image = ImageIO.read(img);
		ByteArrayOutputStream outStreamObj = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", outStreamObj);
		return outStreamObj.toByteArray();
	}

	public static BufferedImage makeImageFromBytes(byte[] array) throws IOException {
		ByteArrayInputStream inStreambj = new ByteArrayInputStream(array);
		return ImageIO.read(inStreambj);

	}
}
