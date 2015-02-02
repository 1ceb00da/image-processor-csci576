package edu.usc.adhulipa;

import java.util.Arrays;

public class ADhulipa {
	private static final int width = 352;
	private static final int height = 288;

	public static void main(String args[]) {

		String filename = args[0];

		int Y = Integer.parseInt(args[1]);
		int U = Integer.parseInt(args[2]);
		int V = Integer.parseInt(args[3]);

		int Q = Integer.parseInt(args[4]);

		byte[] bytes = ImageReader.readImageFromFile(filename);

		int offset = width * height;
		
		byte r[] = Arrays.copyOfRange(bytes, 0, offset);
		byte g[] = Arrays.copyOfRange(bytes, offset, offset * 2);
		byte b[] = Arrays.copyOfRange(bytes, offset * 2, offset * 3);
		
				
		System.out.println(r.length + " r length");
		System.out.println(g.length + " g length");
		System.out.println(b.length + " b length");
		
		
		String[] arg = { "Image1.rgb", "352", "288" };
		ImageReader.run(arg);

	}
}
