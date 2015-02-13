package edu.usc.adhulipa;

import java.util.Arrays;

public class Sampler {

	static int width = 352;
	static int height = 288;
	
	public static float[][] doDownSampling(float originalMatrix[][], int samplingFactor) {
		
		float[][] downSampledMatrix = new float[height/samplingFactor][width];
		
		for (int i = 0 ; i < height; i++) {
			for (int j = 0; j < width; j += samplingFactor) {
				downSampledMatrix[i][j] = originalMatrix[i][j];
			}
		}
		
		return downSampledMatrix;
	}
	
	public static float[][] doUpSampling(float downSampledMatrix[][], int samplingFactor) {
		
		float[][] upSampledMatrix = new float[height][width];
		
		for (int i = 0; i < height; i++) {
			float[] row = new float[width];
			
			for (int j = 0; j < downSampledMatrix[i].length; j++) {
				row[j * samplingFactor] = downSampledMatrix[i][j];
			}
			for (int k = 0; k < width; k++) {
				// for each elem in row, interpolate in-between vals
				// find left and right nearest neighbors in downSampled
				int leftIndex = k / samplingFactor;
				int rightIndex = leftIndex + 1;
				
				if (rightIndex >= downSampledMatrix[i].length) {
					rightIndex -= 1;
				}
				
				float left = downSampledMatrix[i][leftIndex];
				float right = downSampledMatrix[i][rightIndex];
				
				row[k] = (left + right) / samplingFactor;
			}
			upSampledMatrix[i] = row;
		}
		
//		return downSampledMatrix;
		return upSampledMatrix;
	}
}
