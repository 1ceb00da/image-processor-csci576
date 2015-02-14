package clean.code.but.doesnt.work.properly;
import java.util.Arrays;


public class Util {
	
	public static Float[] toObject(final float[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return new Float[0];
		}
		final Float[] result = new Float[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = Float.valueOf(array[i]);
		}
		return result;
	}

	public static float[] toPrimitive(final Float[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return new float[0];
		}
		final float[] result = new float[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i].floatValue();
		}
		return result;
	}

	public static byte[][] seperateChannels(byte[] bytes, int pixelArea) {
		// convert to unsigned bytes
		int[] ubytes = new int[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			ubytes[i] = 0xff0000 & bytes[i];
		}
		// seperate channels
		byte[][] RGBMatrix = new byte[3][pixelArea];
		RGBMatrix[0] = Arrays.copyOfRange(bytes, 0, pixelArea);
		RGBMatrix[1] = Arrays.copyOfRange(bytes, pixelArea, pixelArea * 2);
		RGBMatrix[2] = Arrays.copyOfRange(bytes, pixelArea * 2, pixelArea * 3);
		return RGBMatrix;
	}
}
