package clean.code.but.doesnt.work.properly;
import java.util.ArrayList;

public class Sampler {
	private static int width = 288;
	private static int height = 352;
	
	
	public static float[] subSampler(float[] samples, int factor) {
		ArrayList<Float> downSamps = new ArrayList<Float>();
				
		for (int i = 0 ; i < samples.length; i += factor) {
			downSamps.add(new Float(samples[i]));
		}
		
		Float[] subSamplesIntermediateRepresentation = new Float[downSamps.size()];
		downSamps.toArray(subSamplesIntermediateRepresentation);
		
		float[] subSamples = Util.toPrimitive(subSamplesIntermediateRepresentation);
		
		return subSamples;
	}
	
	public static float[][] doSubSampling(float[][] yuvSamples, int ySubsamplingFactor, int uSubsamplingFactor, int vSubsamplingFactor) {
		
		System.out.println("Subsampling...");
		
		float[] ySamples = yuvSamples[0];
		float[] uSamples = yuvSamples[1];
		float[] vSamples = yuvSamples[2];
		
		float[] yDownSamples = subSampler(ySamples, ySubsamplingFactor);
		float[] uDownSamples = subSampler(uSamples, uSubsamplingFactor);
		float[] vDownSamples = subSampler(vSamples, vSubsamplingFactor);
		
		
		float[][] downSamples = new float[3][]; 
		downSamples[0] = yDownSamples;
		downSamples[1] = uDownSamples;
		downSamples[2] = vDownSamples;
		
		System.out.println(downSamples[2].length);
		
		return downSamples;
	}
	
	public static float[] upSampler(float[] downSamples, int factor) {
		
		float[] upSamps = new float[width*height];
		
		// 1st pass
		for (int i = 0; i < downSamples.length; i++) {
			upSamps[factor * i] = downSamples[i];
		}
		// 2nd pass
		for (int i = 0; i < downSamples.length - 2; i++) {
			if (i % factor != 0) {
				upSamps[i] = interpolator(i, upSamps[i - 1], i - 1,
						upSamps[i + 1], i + 1);
			}
		}
		return upSamps;
	}
	
	public static float[][] doUpSampling(float[][] yuvSubSamples, int yFactor,
			int uFactor, int vFactor) {
		System.out.println("Upsampling...");

		int pxArea = width * height;
		float[] yUpSamps = new float[pxArea];
		float[] uUpSamps = new float[pxArea];
		float[] vUpSamps = new float[pxArea];

		float[] yDownSamps = yuvSubSamples[0];
		float[] uDownSamps = yuvSubSamples[1];
		float[] vDownSamps = yuvSubSamples[2];
		
		yUpSamps = upSampler(yDownSamps, yFactor);
		uUpSamps = upSampler(uDownSamps, uFactor);
		vUpSamps = upSampler(vDownSamps, vFactor);
		
		float upSampled[][] = new float[3][pxArea];
		upSampled[0] = yUpSamps;
		upSampled[1] = uUpSamps;
		upSampled[2] = vUpSamps;

		System.out.println("Done upsampling...");
		return upSampled;
	}
	
	
	private static float interpolator(float g, float d1, float g1, float d2, float g2) {

		float diff = d2 - d1;
		float frac = (g - g1) / (g2 - g1);
		float op = frac * diff;
		float d = d1 + op;

		return d;
	}

}
