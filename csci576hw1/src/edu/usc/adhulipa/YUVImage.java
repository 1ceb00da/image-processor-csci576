package edu.usc.adhulipa;

import java.util.ArrayList;
import java.util.Arrays;

public class YUVImage {
	class YUVComponent {
		float y, u, v;
	}
	
	class RGBComponent {
		int r,g,b;
	}
	
	private static int width = 288;
	private static int height = 352;
	
	// TODO remove uncessary vars
	
	private float[][] YUV3RowMatrix;
	private YUVComponent[][] YUV;
	
	public YUVImage(float[][] YUVMatrix) {
		this.YUV3RowMatrix = YUVMatrix;
		YUV = new YUVComponent[width][height];
		YUVComponent c = new YUVComponent();
		for (int i = 0 ; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int pixel = i * j;
				c.y = YUV3RowMatrix[0][pixel];
				c.u = YUV3RowMatrix[1][pixel];
				c.v = YUV3RowMatrix[2][pixel];
				YUV[i][j] = c;
			}
		}

	}
	
	public YUVComponent[][] getYUVComponentMatrix() {
		return YUV;
	}
	
	public float[][] getYUV3RowMatrix() {
		return YUV3RowMatrix;
	}
	
	public float[][] doUpSampling(YUVArrays subSampledYUV, int yFactor, int uFactor, int vFactor) {
		System.out.println("Upsampling...");
		
		int pxArea = width * height;
		float[] yUpSamps = new float[pxArea];
		float[] uUpSamps = new float[pxArea];
		float[] vUpSamps = new float[pxArea];
		
		int index;
		float prev = 0, next = 0;
		
		// think about 
		// u11 = (uu_left + u_right) / 2
		// u11 = u
		
		// upSamp Y
		for (int i = 0; i < pxArea; i += 1) {
			if ((i % yFactor) == 0) {
				index = i/yFactor;
				yUpSamps[i] = subSampledYUV.y[index];
				prev = subSampledYUV.y[index];
				if (index >= subSampledYUV.y.length) {
					index = subSampledYUV.y.length - 1;
					next = subSampledYUV.y[index];
				}
			}
			else {
				yUpSamps[i] = (prev + next) /(float)yFactor;
			}
		}
		
		// upSamp U
		for (int i = 0; i < pxArea; i += 1) {
			if ((i % uFactor) == 0) {
				index = i/uFactor;
				uUpSamps[i] = subSampledYUV.u[index];
				prev = subSampledYUV.u[index];
				if (index >= subSampledYUV.u.length) {
					index = subSampledYUV.u.length - 1;
					next = subSampledYUV.u[index];
				}
			}
			else {
				uUpSamps[i] = (prev + next) /(float)uFactor;
			}
		}
		// upSamp V
		for (int i = 0; i < pxArea; i += 1) {
			if ((i % vFactor) == 0) {
				index = i / vFactor;
				vUpSamps[i] = subSampledYUV.v[index];
				prev = subSampledYUV.v[index];
				if (index >= subSampledYUV.v.length) {
					index = subSampledYUV.v.length - 1;
					next = subSampledYUV.v[index];
				}
			} else {
				vUpSamps[i] = (prev + next) / (float) vFactor;
			}
		}
		
		YUVArrays upSampledYUV = new YUVArrays(yUpSamps.length, uUpSamps.length, vUpSamps.length);
		upSampledYUV.y = YUVArrays.toObject(yUpSamps);
		upSampledYUV.u = YUVArrays.toObject(uUpSamps);
		upSampledYUV.v = YUVArrays.toObject(vUpSamps);
		
		float upSampled[][] = new float[3][pxArea];
		upSampled[0] = yUpSamps;
		upSampled[1] = uUpSamps;
		upSampled[2] = vUpSamps;
		
		System.out.println("Done upsampling...");
		System.out.println(Arrays.toString(Arrays.copyOfRange(yUpSamps, 0, 100)));
		return upSampled;
	}
	
	public YUVArrays doSubSampling(int ySubsamplingFactor, int uSubsamplingFactor, int vSubsamplingFactor) {
		// subsampling y
		System.out.println("Subsampling...");
		ArrayList<Float> yDownSamps = new ArrayList<Float>();
		ArrayList<Float> uDownSamps = new ArrayList<Float>();
		ArrayList<Float> vDownSamps = new ArrayList<Float>();
		
		
		
		for (int i = 0 ; i < YUV3RowMatrix[0].length; i += ySubsamplingFactor) {
			yDownSamps.add(new Float(YUV3RowMatrix[0][i]));
		}
		for (int i = 0 ; i < YUV3RowMatrix[1].length; i += uSubsamplingFactor) {
			uDownSamps.add(new Float(YUV3RowMatrix[1][i]));
		}
		
		for (int i = 0 ; i < YUV3RowMatrix[2].length; i += vSubsamplingFactor) {
			vDownSamps.add(new Float(YUV3RowMatrix[2][i]));
		}

		YUVArrays subSampledYUV = new YUVArrays(
				yDownSamps.toArray().length, 
				uDownSamps.toArray().length, 
				vDownSamps.toArray().length);
		yDownSamps.toArray(subSampledYUV.y);
		uDownSamps.toArray(subSampledYUV.u);
		vDownSamps.toArray(subSampledYUV.v);
		System.out.println(Arrays.toString(Arrays.copyOfRange(subSampledYUV.y, 0, 100)));
		System.out.println("Done Subsampling...");

		return subSampledYUV;
	}
	

    
}
