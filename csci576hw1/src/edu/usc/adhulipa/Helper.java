package edu.usc.adhulipa;

import java.util.Arrays;

public class Helper {
	
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
