import java.awt.Color;
import java.awt.image.BufferedImage;


public class ImageComparator {

	public static int CompareImages(BufferedImage img1, BufferedImage img2) {
		
		float value = 0;
		
		for (int x = 0; x < img1.getWidth(); x++) {
			for (int y = 0; y < img1.getHeight(); y++) {
				Color c1=new Color(img1.getRGB(x, y));
				Color c2=new Color(img2.getRGB(x, y));
				
				value+=Math.abs(c1.getRed() - c2.getRed());
				value+=Math.abs(c1.getGreen() - c2.getGreen());
				value+=Math.abs(c1.getBlue() - c2.getBlue());
			}
		}
		
		return (int) value;
		//return (int) (value/(3*(img1.getHeight()*img1.getWidth())));
	}

}
