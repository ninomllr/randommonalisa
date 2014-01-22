import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Main extends JPanel {
	
	
	public static int lastDistance = 999999;
	
	public static String binaryStringOriginal = "";
	
	public final static int WIDTH = 620;
	public final static int HEIGHT = 950;
	
	public final static String FILENAME = "monalisa";
	public final static String PATH_REAL = FILENAME+".jpg";
	public final static String PATH_GEN = FILENAME+"_g.png";
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);	
		try {
            BufferedImage bi = ImageIO.read(new java.io.FileInputStream(PATH_GEN));
            g.drawImage(bi, 0,0, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public static void main(String[] args) {
		
		boolean showGui = false;
		
		try {
			lastDistance = findHash();
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		JFrame frame = new JFrame();
		if (showGui) {
			frame.setTitle("Polygon");
			frame.setSize(WIDTH, HEIGHT);
			frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			Container contentPane = frame.getContentPane();
			contentPane.add(new Main());
			frame.setVisible(true);
		} 
		while (lastDistance > 0) {
			try {
				generateNextIteration();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if (showGui)  {
				frame.repaint();
			}
		}
	}
	
	public static void generateNextIteration() throws FileNotFoundException, Exception {
        File imageFile = new File(PATH_GEN);
        Image image = ImageIO.read(imageFile);
        BufferedImage img = (BufferedImage) image;
        Random rand = new Random();
        
        
        Graphics2D graph = img.createGraphics();
        
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        float a = rand.nextFloat();
        graph.setColor(new Color(r, g, b, a));
        
        Polygon p = new Polygon();

        for (int i = 0; i < 3; i++) {
        	p.addPoint((int)(WIDTH*rand.nextFloat()), (int)(HEIGHT*rand.nextFloat()));
        }
        
        graph.fill(p);
        graph.dispose();

        int distance = findHash(img);
        
        if (distance < lastDistance) {
        	ImageIO.write(img, "png", new File(PATH_GEN));
        	System.out.println("Distance: " + distance);
        	lastDistance = distance;
        }

        img.flush();
	}
	
	public static int findHash() throws Exception {
		File imageFile = new File(PATH_GEN);
        Image image = ImageIO.read(imageFile);
        BufferedImage img = (BufferedImage) image;
        return findHash(img);
	}
	
	public static int findHash(BufferedImage img) throws FileNotFoundException, Exception {
		
		File imageFile = new File(PATH_REAL);
        Image image = ImageIO.read(imageFile);
        BufferedImage img2 = (BufferedImage) image;
		
        return ImageComparator.CompareImages(img, img2);
	}
}