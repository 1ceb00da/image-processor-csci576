

import java.util.Arrays;

public class Helper {
	
	static final double a = 2;

	public static double sinc(double x) {
		return Math.sin(Math.PI * x) / (Math.PI * x);
	}
	public static float lanczosKernel(float x) {
		return new Double(sinc(x)/sinc(x/a)).floatValue();
	}
	
	public static float lanczosInterpolate(float[] S, float x, int samplingFactor) {
		x = x/samplingFactor;
		int start = new Double(Math.floor(x) - a + 1).intValue();
		int end = new Double(Math.floor(x) + a).intValue();
		
		if (start < 0) 
			start = 0;
		
		float sum = 0.0f;
		for (int i = start; i <= end; i++)
			sum += S[i] * lanczosKernel(x - i);
		
		return sum;
	}

	// TODO try implementing brahmnagupta's interpolation formula
	// http://www.slideshare.net/PlusOrMinusZero/on-finite-differences-interpolation-methods-and-power-series-expansions-in-indian-mathematics
	public static void lagrangeInterpolant(int x) {
		// TODO Implement this
		// http://homepage.math.uiowa.edu/~atkinson/ftp/ENA_Materials/Overheads/sec_4-1.pdf
	}
	
	public static float interpolate(
			float g,
			float d1, float g1,
			float d2, float g2
			
			) {
		
		float diff = d2-d1;
		float frac = (g-g1)/(g2-g1);
		float op = frac * diff;
		float d = d1 + op;
		
		return d;
	}
	
	public static float[][] reshapeMatrix(float[] vector, int width, int height) {
		float[][] mat = new float[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0 ; j < width; j++) {
				mat[i][j] = vector[(j*height) + i];
			}
		}
				
		return mat;
	}
	
	public static float[] flattenMatrix(float[][] mat, int width, int height) {
		
		float[] vec = new float[height*width];
		
		for (int i = 0 ; i < height; i++) {
			for (int j = 0; j < width; j++) {
				vec[(i*height) + j] = mat[i][j];
			}
		}
				
		return vec;
	}
}
