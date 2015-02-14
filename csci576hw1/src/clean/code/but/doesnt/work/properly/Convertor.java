package clean.code.but.doesnt.work.properly;
import java.nio.ByteBuffer;


public class Convertor {
	public static float[][] convertFromRGBToYUV(byte[] originalBytes, int width, int height) {
		int totalPixels = width * height;
		float[][] YUVChannels = new float[3][totalPixels];
		byte[][] RGBChannels = Util.seperateChannels(originalBytes, width * height);

		double[][] RGBToYUVFilter = {
				{0.299, 0.587, 0.114},
				{-0.147, -0.289, 0.436},
				{0.615, -0.515, -0.100}
		};
		
		for (int channel = 0; channel < 3; channel++) {
			for (int pixel = 0; pixel < totalPixels; pixel++) {
				for (int k = 0; k < 3; k++) {
					YUVChannels[channel][pixel] += 
							RGBToYUVFilter[channel][k] * ((int)RGBChannels[k][pixel] & 0xff); // call last step of ubyte conversion from Util class
				}
			}
		}
		return YUVChannels;
	}
	
	public static byte[] convertFromYUVToRGB(float[][] YUVChannels, int width, int height) {
		int totalPixels = width * height;
		float[][] RGBChannels = new float[3][totalPixels];

		double[][] YUVToRGBFilter = {
				{0.999, 0.000, 1.140},
				{1.000, -0.395, -0.581},
				{1.000, 2.032, 0.000}
		};

		for (int channel = 0; channel < 3; channel++) {
			for (int pixel = 0; pixel < totalPixels; pixel++) {
				for (int k = 0; k < 3; k++) {
					RGBChannels[channel][pixel] += (YUVToRGBFilter[channel][k] * YUVChannels[k][pixel]);
				}
			}
		}
		
		ByteBuffer b = ByteBuffer.allocate(3 * totalPixels);		
		int idx = 0;
		for (int i = 0; i < totalPixels; i++) {
			b.put((byte) RGBChannels[0][i]);
		}
		for (int i = 0; i < totalPixels; i++) {
			b.put((byte) RGBChannels[1][i]);
		}
		for (int i = 0; i < totalPixels; i++) {
			b.put((byte) RGBChannels[2][i]);
		}
		byte[] processedBytes = b.array();
		
		return processedBytes;

	}

}
