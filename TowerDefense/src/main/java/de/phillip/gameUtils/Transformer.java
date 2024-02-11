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
	
	public static int getDistance(double x1, double y1, double x2, double y2) {
		double diffX = Math.abs(x1 - x2);
		double diffY = Math.abs(y1 - y2);
		return (int) Math.hypot(diffX, diffY);
	}
	
	public static int getDirection(double targetX, double targetY, double x, double y) {
		double distX = targetX - x;
		double distY = targetY - y;
		double angleRadiant = Math.atan2(distY, distX);
		return (int) Math.toDegrees(angleRadiant);
	}

}
