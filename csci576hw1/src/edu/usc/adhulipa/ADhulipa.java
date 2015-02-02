package edu.usc.adhulipa;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import edu.usc.adhulipa.view.ViewFrame;

public class ADhulipa {
	private static final int width = 352;
	private static final int height = 288;

	public static void main(String args[]) throws InterruptedException {

		String filename = args[0];
		filename = "Image1.rgb";
		int Y = Integer.parseInt(args[1]);
		int U = Integer.parseInt(args[2]);
		int V = Integer.parseInt(args[3]);

		int Q = Integer.parseInt(args[4]);

		byte[] bytes = ImageReader.getImageAsBytes(filename);

		int offset = width * height;
		
		double[][] RGBToYUVFilter = {
				{0.299, 0.587, 0.114},
				{-0.147, -0.289, 0.436},
				{0.615, -0.515, -0.100}
				};

		double[][] YUVToRGBFilter = {
				{0.999, 0.000, 1.140},
				{1.000, -0.395, -0.581},
				{1.000, 2.032, 0.000}
		};
		
		byte[][] RGBMatrix = new byte[3][width * height];
		RGBMatrix[0] = Arrays.copyOfRange(bytes, 0, offset);;
		RGBMatrix[1] = Arrays.copyOfRange(bytes, offset, offset * 2);;
		RGBMatrix[2] = Arrays.copyOfRange(bytes, offset * 2, offset * 3);;
				
		
		double[][] YUVMatrix = new double[3][width * height];
		
		BufferedImage img = ImageReader.covertToImageFromBytes(width, height, BufferedImage.TYPE_INT_RGB, bytes);
		

		int pixels = width * height;
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < pixels; col++) {
				for (int k = 0; k < 3; k++) {
					YUVMatrix[row][col] += RGBToYUVFilter[row][k] * RGBMatrix[k][col];
				}
			}
		}
		
		
		// Test rgb to yuv con
		byte[][] RGB = new byte[3][width * height];
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < pixels; col++) {
				for (int k = 0; k < 3; k++) {
					RGB[row][col] += YUVToRGBFilter[row][k] * YUVMatrix[k][col];
				}
				if (RGBMatrix[row][col] != RGB[row][col]) {
					RGB[row][col] -= 1;
				}
			}
		}
		
		
		
		ByteBuffer b = ByteBuffer.allocate(3 * pixels);		
		int idx = 0;
		for (int i = 0; i < pixels; i++) {
			b.put(RGB[0][i]);
		}
		for (int i = 0; i < pixels; i++) {
			b.put(RGB[1][i]);
		}
		for (int i = 0; i < pixels; i++) {
			b.put(RGB[2][i]);
		}
		byte[] rgbbytes = b.array();
		

		BufferedImage img2 = ImageReader.covertToImageFromBytes(width, height, BufferedImage.TYPE_INT_RGB, rgbbytes);
		// end of test
		
		
		ViewFrame frame = new ViewFrame("Display Images");
		frame.addImage(new JLabel(new ImageIcon(img)));
		frame.addImage(new JLabel(new ImageIcon(img2)));
		frame.setVisible(true);

	}
}
