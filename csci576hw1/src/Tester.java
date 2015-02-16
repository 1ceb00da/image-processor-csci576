import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Tester {

	public static void main(String a[]) {
		
		String[] args = {"", "", "", "", ""}; 
		
		List<String[]> paramList = new ArrayList<String[]>();
		paramList.add(new String[]{"Image4.rgb", "1", "1", "1", "256"});
		//paramList.add(new String[]{"Image2.rgb", "1", "15", "15", "256"});

		for (int i = 1; i <= 15; i += 5) {
			//	paramList.add(new String[]{"Image2.rgb", Integer.toString(i), "1", "1", "256"});
			//	paramList.add(new String[]{"Image4.rgb", "1", Integer.toString(i), "1", "256"});
			//	paramList.add(new String[]{"Image3.rgb", "1", "1", Integer.toString(i), "256"});
				paramList.add(new String[]{"Image4.rgb", "1", Integer.toString(i), Integer.toString(i), "8"});
		}
		
		//for (int i = 256; i > 1; i -= 100) paramList.add(new String[]{"Image4.rgb", "1", "1", "1", Integer.toString(i)});

//		l.add(new String[]{"Image1.rgb", "1", "1", "1", "256"});
//		l.add(new String[]{"Image1.rgb", "1", "2", "1", "256"});
//		l.add(new String[]{"Image1.rgb", "1", "3", "1", "256"});
//		l.add(new String[]{"Image1.rgb", "1", "4", "1", "256"});
//		l.add(new String[]{"Image1.rgb", "1", "5", "1", "256"});
//		l.add(new String[]{"Image1.rgb", "1", "6", "1", "256"});
		
		
		ViewFrame frame = new ViewFrame("Display Images");
		frame.setVisible(true);

		for (String[] each : paramList) {
			Main m = new Main();
			BufferedImage img = m.doImageprocessing(each);
			frame.addImage(new JLabel(new ImageIcon(img)));

		}
		frame.setVisible(true);
	
	}
}
