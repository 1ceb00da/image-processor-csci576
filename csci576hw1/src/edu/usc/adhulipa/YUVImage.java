package edu.usc.adhulipa;

public class YUVImage {
	private float[][] YUV;
	
	public YUVImage(float[][] YUVMatrix) {
		this.YUV = YUVMatrix;
	}
	
	public float[][] getYUV() {
		return YUV;
	}
	
	public void doSubSampling(int Y, int U, int V) {
		
	}
}
