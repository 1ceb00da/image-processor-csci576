package edu.usc.adhulipa;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import edu.usc.adhulipa.view.ViewFrame;

public class Main {
	private static final int width = 352;
	private static final int height = 288;
	
	
	public static int[] convertBytesToUnsignedBytes(byte[] bytes) {
		int[] ubytes = new int[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			ubytes[i] = 0xff0000 & bytes[i];
		}
		return ubytes;
	}
	public static byte[][] getMatrixFromContiguosBytes(byte[] bytes, int pixelArea) {
		// convert to unsigned bytes
		int[] ubytes = convertBytesToUnsignedBytes(bytes);
		
		
		byte[][] RGBMatrix = new byte[3][pixelArea];
		RGBMatrix[0] = Arrays.copyOfRange(bytes, 0, pixelArea);
		RGBMatrix[1] = Arrays.copyOfRange(bytes, pixelArea, pixelArea * 2);
		RGBMatrix[2] = Arrays.copyOfRange(bytes, pixelArea * 2, pixelArea * 3);
		return RGBMatrix;
	}

	public static float[][] convertFromRGBToYUV(byte[][] RGBMatrix) {
		int pixelArea = width * height;
		float[][] YUVMatrix = new float[3][pixelArea];

		double[][] RGBToYUVFilter = {
				{0.299, 0.587, 0.114},
				{-0.147, -0.289, 0.436},
				{0.615, -0.515, -0.100}
		};
		
		int pixels = width * height;
		for (int row = 0; row < 3; row++) {
			for (int pixel = 0; pixel < pixelArea; pixel++) {
				for (int k = 0; k < 3; k++) {
//					YUVMatrix[row][pixel] += RGBToYUVFilter[row][k] * RGBMatrix[k][pixel];
					YUVMatrix[row][pixel] += RGBToYUVFilter[row][k] * ((int)RGBMatrix[k][pixel] & 0xff);
				}
			}
		}
		return YUVMatrix;


	}
	
	public static float[][] convertFromYUVToRGBFloat(float[][] YUVMatrix) {
		double[][] YUVToRGBFilter = {
				{0.999, 0.000, 1.140},
				{1.000, -0.395, -0.581},
				{1.000, 2.032, 0.000}
		};

		int pixelArea = width  *height;
		float[][] RGB = new float[3][width * height];
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < pixelArea; col++) {
				for (int k = 0; k < 3; k++) {
					RGB[row][col] += (YUVToRGBFilter[row][k] * YUVMatrix[k][col]);
				}
			}
		}
		return RGB;

	}
	
	public static void main(String args[]) throws InterruptedException {
		
		String filename = args[0];
		filename = "Image3.rgb";
		int Y = Integer.parseInt(args[1]);
		int U = Integer.parseInt(args[2]);
		int V = Integer.parseInt(args[3]);

		int Q = Integer.parseInt(args[4]);

		byte[] bytes = ImageReader.getImageAsBytes(filename);

		int pixelArea = width * height;

		byte[][] RGBMatrix = getMatrixFromContiguosBytes(bytes, pixelArea);
		float[][] YUVMatrix = convertFromRGBToYUV(RGBMatrix);
		float[][] RGB = convertFromYUVToRGBFloat(YUVMatrix);
		
		BufferedImage img = ImageReader.covertToImageFromBytes(width, height, BufferedImage.TYPE_INT_RGB, bytes);
		
		
		ByteBuffer b = ByteBuffer.allocate(3 * pixelArea);		
		int idx = 0;
		for (int i = 0; i < pixelArea; i++) {
			b.put((byte) RGB[0][i]);
		}
		for (int i = 0; i < pixelArea; i++) {
			b.put((byte) RGB[1][i]);
		}
		for (int i = 0; i < pixelArea; i++) {
			b.put((byte) RGB[2][i]);
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
