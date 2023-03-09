package logoMapper;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import domain.Logo;
import domain.Supplier;
import javafx.scene.image.Image;

public class LogoMapper {
	
	public static Logo makeLogo (byte[] array,Supplier supplier) {
		Logo logo = new Logo(array,supplier);
		return logo;
	}
	
	public static byte[] extractBytesFromImg (File img) throws IOException {
		BufferedImage image = ImageIO.read(img);
		ByteArrayOutputStream outStreamObj = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", outStreamObj);
		return outStreamObj.toByteArray();
	}
	
	public static BufferedImage makeImageFromBytes (byte[] array) throws IOException {
		ByteArrayInputStream inStreambj = new ByteArrayInputStream(array);
		return ImageIO.read(inStreambj);
		
	}
}
