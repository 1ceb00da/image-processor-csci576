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
}
