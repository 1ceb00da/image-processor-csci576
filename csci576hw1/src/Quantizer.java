

public class Quantizer {

	public static double quantizer(int x, int q, int o) {
		double delta = (double) o / (double)q;
		double qx = delta * Math.floor( Math.floor(x/delta) + (1.0/2.0));
		
		return qx;
	}
}
