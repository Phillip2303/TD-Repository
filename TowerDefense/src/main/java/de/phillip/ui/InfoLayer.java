package de.phillip.ui;

import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.ResourcePool;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class InfoLayer extends Canvas {
	
	private TurretTile[][] turretTiles;
	private Image turretSprite;

	public InfoLayer(int tileWidth, int tileHeight, int level) {
		super(tileWidth*Constants.TILESIZE, tileHeight*Constants.TILESIZE);
		turretSprite = ResourcePool.getInstance().getTurretSprite();
		createTurretTiles();
		showBackground();
		showTurrets();
	}
	
	private void createTurretTiles() {
		int ID = 0, xIndex = 0, yIndex = 0;
		turretTiles = new TurretTile[2][4];
		for(int y = 7; y < 9; y++) {
			for (int x = 18; x < 22; x++) {
				turretTiles[yIndex][xIndex] = new TurretTile(x, y, ID, Constants.TILESIZE);
				turretTiles[yIndex][xIndex].setSprite(turretSprite);
				ID++;
				xIndex++;
			}
			yIndex++;
			xIndex = 0;
		}
	}
	
	private void showTurrets() {
		for(int y = 0; y < turretTiles.length; y++) {
			for (int x = 0; x < turretTiles[y].length; x++) {
				turretTiles[y][x].drawToCanvas(getGraphicsContext2D());
			}
		}
	}
	
	private void showBackground() {
		getGraphicsContext2D().drawImage(ResourcePool.getInstance().getBackground(), 0, 0);
	}
	
	public void checkHoverTile(Point2D point) {
		for(int y = 0; y < turretTiles.length; y++ ) {
			for (int x = 0; x < turretTiles[y].length; x++) {
				if (turretTiles[y][x].equals(point)) {
					turretTiles[y][x].setActive(true);
					System.out.println("Point X: " + point.getX());
					System.out.println("Point Y: " + point.getY());
				} else {
					turretTiles[y][x].setActive(false);
				}
			}
		}
	}
}
