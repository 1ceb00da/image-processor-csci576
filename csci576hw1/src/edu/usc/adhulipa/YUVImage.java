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
	
	public static float interpolate(
			// inputs g, g1, g2 are given
			// d1 d2 are desired values for g1 g2
			// output desired value d for given g
			float g,
			float d1, float g1,
			float d2, float g2
			){
		float d = d1 + (((g-g1)/(g2-g1))*(d2-d1));
		return d;
		
	}
	
	public float[][] doUpSampling2(YUVArrays subSampledYUV, int yFactor, int uFactor, int vFactor) {
		System.out.println("Upsampling...");
		
		int pxArea = width * height;
		float[] yUpSamps = new float[pxArea];
		float[] uUpSamps = new float[pxArea];
		float[] vUpSamps = new float[pxArea];
				
		// think about 
		// u11 = (uu_left + u_right) / 2
		// u11 = u		
		
		// y upSamp method 2
		for (int i = 0; i < subSampledYUV.y.length; i++) {
			yUpSamps[yFactor * i] = subSampledYUV.y[i];
		}
		
		
		
		// wokrs fine with all images... makes them pixelated but that's ok
		for (int i= 0; i < yUpSamps.length; i++) {
			if (i%vFactor != 0) {
				yUpSamps[i] = yUpSamps[i-1];
			}
		}
		for (int i= 0; i < yUpSamps.length-2; i++) {
			if (i%vFactor != 0) {
				yUpSamps[i] = (yUpSamps[i-1]+yUpSamps[i+1])/2;
			}
		}
//			// fill neighbors form i - uFact + 1 to i + uFact - 1 with ith value
//			int idx = yFactor * i;
//			for (int j = (idx-yFactor+1); j <= (idx+yFactor-1); j++) {
//				if (j >= 0 && j <= yUpSamps.length-1){
//					yUpSamps[j] = subSampledYUV.y[i];
//				}
//			}
//			
//		}
//		for (int i = 0; i < yUpSamps.length; i++) {
//			if ((i%yFactor) != 0) {
//				yUpSamps[i] /= yFactor;
//			}
//		}
//		
		// upSamp U - method 2
		
		for (int i = 0; i < subSampledYUV.u.length; i++) {
			uUpSamps[uFactor * i] = subSampledYUV.u[i];
		}
		for (int i= 0; i < uUpSamps.length-2; i++) {
			if (i%vFactor != 0) {
				uUpSamps[i] = (uUpSamps[i-1] + uUpSamps[i+1])/2;
			}
		}		
//			// fill neighbors form i - uFact + 1 to i + uFact - 1 with ith value
//			int idx = uFactor * i;
//			for (int j = (idx-uFactor+1); j <= (idx+uFactor-1); j++) {
//				if (j >= 0 && j <= uUpSamps.length-1){
//					uUpSamps[j] = subSampledYUV.u[i];
//				}
//			}
//			
//		}
//		for (int i = 0; i < uUpSamps.length; i++) {
//			if ((i%uFactor) != 0) {
//				uUpSamps[i] /= uFactor;
//			}
//		}
//		
		// upSamp V - method 2
		for (int i = 0; i < subSampledYUV.v.length; i++) {
			vUpSamps[vFactor * i] = subSampledYUV.v[i];
		}
		for (int i= 0; i < vUpSamps.length-2; i++) {
			if (i%vFactor != 0) {
				vUpSamps[i] = (vUpSamps[i-1] + vUpSamps[i+1]) /2;
			}
		}
//			// fill neighbors form i - vFact + 1 to i + vFact - 1 with ith value
//			int idx = vFactor * i;
//			for (int j = (idx-vFactor+1); j <= (idx+vFactor-1); j++) {
//				if (j >= 0 && j <= vUpSamps.length-1){
//					vUpSamps[j] = subSampledYUV.v[i];
//				}
//			}
//			
//		}
//		for (int i = 0; i < vUpSamps.length; i++) {
//			if ((i%vFactor) != 0) {
//				vUpSamps[i] /= vFactor;
//			}
//		}
				
		
		
		YUVArrays upSampledYUV = new YUVArrays(yUpSamps.length, uUpSamps.length, vUpSamps.length);
		upSampledYUV.y = YUVArrays.toObject(yUpSamps);
		upSampledYUV.u = YUVArrays.toObject(uUpSamps);
		upSampledYUV.v = YUVArrays.toObject(vUpSamps);
		
		float upSampled[][] = new float[3][pxArea];
		upSampled[0] = yUpSamps;
		upSampled[1] = uUpSamps;
		upSampled[2] = vUpSamps;
		
		System.out.println("Done upsampling...");
		//System.out.println(Arrays.toString(Arrays.copyOfRange(yUpSamps, 0, 100)));
		return upSampled;
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
					index = subSampledYUV.y.length - 2;
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
		//System.out.println(Arrays.toString(Arrays.copyOfRange(yUpSamps, 0, 100)));
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
		//System.out.println(Arrays.toString(Arrays.copyOfRange(subSampledYUV.y, 0, 100)));
		System.out.println("Done Subsampling...");

		return subSampledYUV;
	}

    
}
