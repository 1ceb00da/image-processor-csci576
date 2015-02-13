package edu.usc.adhulipa;

import java.awt.image.BufferedImage;
import java.io.IOException;
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
					YUVMatrix[row][pixel] += RGBToYUVFilter[row][k] * ((int)RGBMatrix[k][pixel] & 0xff); // before multiplying RGB value, we need to convert to unsigned byte
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
	
	public static void main(String args[])  {
		
		String filename = args[0];
		int Y = Integer.parseInt(args[1]);
		int U = Integer.parseInt(args[2]);
		int V = Integer.parseInt(args[3]);

		int Q = Integer.parseInt(args[4]);

		Y = 1;
		U = 2;
		V = 2;
		filename = "Image2.rgb";

		byte[] originalBytes = ImageReader.getImageAsBytes(filename);

		int pixelArea = width * height;

		byte[][] RGBMatrix = getMatrixFromContiguosBytes(originalBytes, pixelArea);
		float[][] YUVMatrix = convertFromRGBToYUV(RGBMatrix);
		YUVImage yuvImage = new YUVImage(YUVMatrix);

		// TODO: complete subsampling  
		YUVArrays yuvArray = yuvImage.doSubSampling(Y, U, V);
		
		// ----------------------- //

		// TODO: adjust for upsampling
		float[][] upSampledYUV = yuvImage.doUpSampling2(yuvArray, Y,  U, V);
		
		// ----------------------- //
		
		// TODO: convert to rgb space
		float[][] RGBFloat = convertFromYUVToRGBFloat(upSampledYUV);

		// ----------------------- //
		
		// TODO: quantize rgb channels accoring input param Q
		//quantizeImage(RGBFloat, Q);
		// ----------------------- //
		
		// Test YUV to RGB conversion
		//
		BufferedImage originalImage = ImageReader.covertToImageFromBytes(width, height, BufferedImage.TYPE_INT_RGB, originalBytes);
		
		// Need to toncvert into bytes (for RGB) from floats (YUV)
		ByteBuffer b = ByteBuffer.allocate(3 * pixelArea);		
		int idx = 0;
		for (int i = 0; i < pixelArea; i++) {
			b.put((byte) RGBFloat[0][i]);
		}
		for (int i = 0; i < pixelArea; i++) {
			b.put((byte) RGBFloat[1][i]);
		}
		for (int i = 0; i < pixelArea; i++) {
			b.put((byte) RGBFloat[2][i]);
		}
		byte[] postProcessRGBBytes = b.array();

		// TODO: quantize rgb channels accoring input param Q
		quantizeImage(postProcessRGBBytes, Q);
		
		// ----------------------- //
		BufferedImage img2 = ImageReader.covertToImageFromBytes(width, height, BufferedImage.TYPE_INT_RGB, postProcessRGBBytes);
		// end of test
		
		
		ViewFrame frame = new ViewFrame("Display Images");
		frame.addImage(new JLabel(new ImageIcon(originalImage)));
		frame.addImage(new JLabel(new ImageIcon(img2)));
		frame.setVisible(true);

	}
	private static void quantizeImage(byte[] rgbBytes, int q) {
		
		for (int i = 0; i < 101000; i+= 1000) {
			int byteVal = (int)rgbBytes[i] & 0x000000FF;;
			//System.out.println(byteVal);
		}
	}
	private static void quantizeImage(float[][] rgb, int q) {
		System.out.println("Quatizing....");
		for (int i = 0; i < 100; i++) {
			System.out.println(rgb[0][i]);
		}
	}
}
