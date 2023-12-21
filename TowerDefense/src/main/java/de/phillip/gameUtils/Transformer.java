package de.phillip.gameUtils;

import javafx.geometry.Point2D;

public final class Transformer {

	private Transformer() {
		
	}
	
	public static Point2D transformPixelsCoordinatesToTile(double x, double y) {
		x = x / Constants.TILESIZE;
		y = y / Constants.TILESIZE;
		return new Point2D((int) x, (int) y);
	}

}
