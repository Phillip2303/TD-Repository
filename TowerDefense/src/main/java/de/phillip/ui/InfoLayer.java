package de.phillip.ui;

import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.ResourcePool;
import javafx.scene.canvas.Canvas;

public class InfoLayer extends Canvas {
	
	private Tile[][] turretTiles;

	public InfoLayer(int tileWidth, int tileHeight, int level) {
		super(tileWidth*Constants.TILESIZE, tileHeight*Constants.TILESIZE);
		createTurretTiles();
		showBackground();
		showTurrets();
	}
	
	private void createTurretTiles() {
		int ID = 0, xIndex = 0, yIndex = 0;
		turretTiles = new Tile[2][4];
		for(int y = 7; y < 9; y++) {
			for (int x = 18; x < 22; x++) {
				turretTiles[yIndex][xIndex] = new TurretTile(x*Constants.TILESIZE, y*Constants.TILESIZE, ID, Constants.TILESIZE);
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

}
