package edu.usc.adhulipa;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.swing.*;

public class ImageReader {

	public static byte[] getImageAsBytes(String filename) {
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

	public static void run(String[] args) throws InterruptedException {

		// Read Image
		String fileName = args[0];
		try {
			File file = new File(args[0]);
			InputStream is = new FileInputStream(file);

			long len = file.length();
			byte[] bytes = new byte[(int) len];

			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
			
			System.out.println(bytes.length + " bytes in originla image");
			System.out.println((bytes.length / 3) + " of r,g,b bytes in originla image");

			// Create Image
			int width = Integer.parseInt(args[1]);
			int height = Integer.parseInt(args[2]);

			BufferedImage img = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);

			int ind = 0;
			for (int y = 0; y < height; y++) {

				for (int x = 0; x < width; x++) {

					byte a = 0;
					byte r = bytes[ind];
					byte g = bytes[ind + height * width];
					byte b = bytes[ind + height * width * 2];

					int pix = 0xff000000 | ((r & 0xff) << 16)
							| ((g & 0xff) << 8) | (b & 0xff);

					// int pix = ((a << 24) + (r << 16) + (g << 8) + b);
					img.setRGB(x, y, pix);
					ind++;
				}
			}
			
			// Use a panel and label to display the image
			JPanel panel = new JPanel();
			panel.add(new JLabel(new ImageIcon(img)));

			JFrame frame = new JFrame("Display images");
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			frame.setLocation(dim.width / 2 - frame.getSize().width / 2,
					dim.height / 2 - frame.getSize().height / 2);

			frame.getContentPane().add(panel);
			frame.setVisible(true);
			frame.pack();

			panel.add(new JLabel(new ImageIcon(img)));
			frame.pack();
			panel.repaint();

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static BufferedImage covertToImageFromBytes(int width, int height, int imageType, byte[] bytes) {
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