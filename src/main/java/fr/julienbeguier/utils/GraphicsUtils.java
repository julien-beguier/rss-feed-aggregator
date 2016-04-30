package fr.julienbeguier.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

public class GraphicsUtils {

	private static GraphicsUtils instance = null;
	
	private GraphicsUtils() {
		
	}
	
	public static GraphicsUtils getInstance() {
		if (instance == null) {
			instance = new GraphicsUtils();
		}
		return instance;
	}
	
	public Dimension getScreenDimension() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
}
