package clean.code.but.doesnt.work.properly;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class IOHelper {
	public static byte[] readBytesFromImage(String filename) {
		try {
			File file = new File(filename);
			InputStream is = new FileInputStream(file);

			long len = file.length();
			byte[] bytes = new byte[(int) len];

			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			return bytes;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage writeImageFromBytes(int width, int height,
			int imageType, byte[] bytes) {
			BufferedImage img = new BufferedImage(width, height, imageType);
			int ind = 0;
			for (int y = 0; y < height; y++) {

				for (int x = 0; x < width; x++) {

					byte a = 0;
					byte red = bytes[ind];
					byte gr = bytes[ind + height * width];
					byte bb = bytes[ind + height * width * 2];

					int pix = 0xff000000 | ((red & 0xff) << 16)
							| ((gr & 0xff) << 8) | (bb & 0xff);

					// int pix = ((a << 24) + (r << 16) + (g << 8) + b);
					img.setRGB(x, y, pix);
					ind++;
				}
			}
			return img;
			

	}

}
