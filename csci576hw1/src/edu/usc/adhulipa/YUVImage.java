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
	
	public YUVArrays doSubSampling(int ySubsamplingFactor, int uSubsamplingFactor, int vSubsamplingFactor) {
		// subsampling y

		ArrayList<Float> ySamps = new ArrayList<Float>();
		ArrayList<Float> uSamps = new ArrayList<Float>();
		ArrayList<Float> vSamps = new ArrayList<Float>();
		
		for (int i = 0 ; i < YUV3RowMatrix[0].length; i += ySubsamplingFactor) {
			ySamps.add(new Float(YUV3RowMatrix[0][i]));
		}
		for (int i = 0 ; i < YUV3RowMatrix[1].length; i += uSubsamplingFactor) {
			uSamps.add(new Float(YUV3RowMatrix[1][i]));
		}
		
		for (int i = 0 ; i < YUV3RowMatrix[2].length; i += vSubsamplingFactor) {
			vSamps.add(new Float(YUV3RowMatrix[2][i]));
		}

		YUVArrays yuv = new YUVArrays(
				ySamps.toArray().length, 
				uSamps.toArray().length, 
				vSamps.toArray().length);
		ySamps.toArray(yuv.y);
		uSamps.toArray(yuv.u);
		vSamps.toArray(yuv.v);
		
		return yuv;
	}
}
