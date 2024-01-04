package de.phillip.ui;

import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.ResourcePool;
import de.phillip.models.Tile;
import javafx.scene.canvas.Canvas;

public class TerrainLayer extends Canvas {
	
	private int layerWidth;
	private int layerHeight;
	private Tile[][] terrainTiles;
	private int level;
	
	public TerrainLayer(int tileWidth, int tileHeight, int level) {
		super(tileWidth*Constants.TILESIZE, tileHeight*Constants.TILESIZE);
		this.level = level;
		terrainTiles = ResourcePool.getInstance().getTerrainTiles(level);
		layerWidth = Constants.TERRAINLAYER_WIDTH;
		layerHeight = Constants.TERRAINLAYER_HEIGHT;
		showLayer();
	}
	
	private void showLayer() {
		for(int y = 0; y < terrainTiles.length; y++) {
			for (int x = 0; x < terrainTiles[y].length; x++) {
				terrainTiles[y][x].drawToCanvas(getGraphicsContext2D());
			}
		}
	}
	
	public void setLevel(int level) { 
		this.level = level;
		terrainTiles = ResourcePool.getInstance().getTerrainTiles(level);
		showLayer();
	}
}
