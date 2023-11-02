package de.phillip.ui;

import javafx.scene.canvas.GraphicsContext;

public abstract class Tile {
	
	private int posX;
	private int posY;
	private int ID;
	private int width;
	private int height;
	
	public Tile(int posX, int posY, int iD, int size) {
		super();
		this.posX = posX;
		this.posY = posY;
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
	
	public abstract void drawToCanvas(GraphicsContext gc);
}
