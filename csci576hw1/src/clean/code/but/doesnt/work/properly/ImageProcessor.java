package clean.code.but.doesnt.work.properly;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;




public class ImageProcessor {
	private static final int width = 352;
	private static final int height = 288;

	public static void main(String[] args) {
		
		String filename = args[0];
		int Y = Integer.parseInt(args[1]);
		int U = Integer.parseInt(args[2]);
		int V = Integer.parseInt(args[3]);
		int Q = Integer.parseInt(args[4]);

		System.out.println("file " + filename);
		System.out.println("Y " + Y);
		System.out.println("U " + U);
		System.out.println("V " + V);
		System.out.println("Q " + Q);
		
		byte[] originalBytes = IOHelper.readBytesFromImage(filename);
		float[][] YUVChannels = Convertor.convertFromRGBToYUV(originalBytes, width, height);
		
		// 1. Subsampling
		YUVChannels = Sampler.doSubSampling(YUVChannels, Y, U, V);
		
		// 2. UpSampling
		YUVChannels = Sampler.doUpSampling(YUVChannels, Y, U, V);
		
		// 3. Convert back to RGB space
		byte[] processedBytes= Convertor.convertFromYUVToRGB(YUVChannels, width, height);
		
		// 3. Quantization
		processedBytes = Quantizer.quantizeImage(processedBytes, Q);

		BufferedImage originalImage = IOHelper.writeImageFromBytes(width, height, BufferedImage.TYPE_INT_RGB, originalBytes);
		BufferedImage processedImage = IOHelper.writeImageFromBytes(width, height, BufferedImage.TYPE_INT_RGB, processedBytes);

		ViewFrame frame = new ViewFrame("Display Images");
		frame.addImage(new JLabel(new ImageIcon(originalImage)));
		frame.addImage(new JLabel(new ImageIcon(processedImage)));
		frame.setVisible(true);

	}

}
