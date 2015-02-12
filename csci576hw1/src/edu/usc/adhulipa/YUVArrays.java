package edu.usc.adhulipa;

public class YUVArrays {
	public Float[] y;
	public Float[] u;
	public Float[] v;
	
	public YUVArrays(int yLength, int uLength, int vLength) {
		y = new Float[yLength];
		u = new Float[uLength];
		v = new Float[vLength];
	}
	
	
	
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

}
