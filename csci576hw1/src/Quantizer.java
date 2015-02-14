

import java.util.Set;
import java.util.TreeSet;

public class Quantizer {

	public static double quantizer(int x, int q, int o) {
		double delta = (double) o / (double)q;
		double qx = delta * Math.floor( Math.floor(x/delta) + (1.0/2.0));
		
		return qx;
	}

	public static byte[] quantizeImage(byte[] rgbBytes, int q) {

		// idea
		// 1 singed quantizer
		// delta = 256/q; 256 orignal possible vals, Q is quantFac form cmd line
		// for each byte x
		//  Qfn(x) = sgn(x) * delta * 
		//			(floor((abs(x)/delta)) + (1/2))
		
		// 2 mid-riser quantizer
		// delta = 256/q; 256 orignal possible vals, Q is quantFac form cmd line
		// for each byte x
		//  Qfn(x) = delta * 
		//			(floor((abs(x)/delta)) + (1/2))
		
		Set<Integer> testSet = new TreeSet<Integer>();
		
		System.out.println("Quatizing rgbByteVals....");		
		for (int i = 0; i < rgbBytes.length; i++) {
			int byteVal = (int)rgbBytes[i] & 0x000000FF;
			double newbyteval = Quantizer.quantizer(byteVal, q, 256);
			testSet.add((new Integer((byte)(int)newbyteval & 0x000000ff)));
			/*System.out.println(
					"oldVal = " + ""
					+ Byte.toUnsignedInt(rgbBytes[i]) + " vs. newVal = " + ""
					+ ((int)newbyteval & 0x000000ff)
				);
			*/
			// replace rgbByte with new val
			
			rgbBytes[i] = (byte)((int)newbyteval & 0x000000ff);
		}
		
		//System.out.println(Arrays.toString(testSet.toArray()));
		System.out.println("Done quatization!");
		return rgbBytes;
	}
}
