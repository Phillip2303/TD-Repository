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
	private Point2D position;
	
	public Tile(int x, int y, int iD, int size) {
		super();
		tileCoor = new Point2D(x, y);
		this.posX = x * size;
		this.posY = y * size;
		position = new Point2D(this.posX, this.posY);
		ID = iD;
		this.width = size;
		this.height = size;
	}
	public void setDrawPosition(double x, double y) {
		position = new Point2D(x, y);
	}

	public Point2D getDrawPosition() {
		return position;
	}
	public int getPosX() {
		return (int) position.getX();
	}
	public int getPosY() {
		return (int) position.getY();
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
	
	public Point2D getCenter() {
		Point2D pos = getDrawPosition();
		return new Point2D(pos.getX() + width / 2, pos.getY() + height / 2);
	}
	
	public abstract void drawToCanvas(GraphicsContext gc);
}
