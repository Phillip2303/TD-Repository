package de.phillip.models;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class Tile implements Drawable {
	
	private int posX;
	private int posY;
	private int ID;
	private int width;
	private int height;
	private Point2D tileCoor;
	
	public Tile(int x, int y, int iD, int size) {
		super();
		tileCoor = new Point2D(x, y);
		this.posX = x * size;
		this.posY = y * size;
		ID = iD;
		this.width = size;
		this.height = size;
	}
	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
	}
	public int getID() {
		return ID;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	public Point2D getTileCoor() {
		return tileCoor;
	}
	
	public boolean equals(Point2D point) {
		if (tileCoor.getX() == point.getX() && tileCoor.getY() == point.getY()) {
			return true;
		} else {
			return false;
		}
	}
	
	public abstract void drawToCanvas(GraphicsContext gc);
}
