package de.phillip.ui;

import de.phillip.controls.Constants;
import de.phillip.controls.ResourcePool;
import javafx.scene.canvas.Canvas;

public class TerrainLayer extends Canvas {
	
	private int layerWidth;
	private int layerHeight;
	private Tile[][] terrainTiles;
	private Tile[][] paths;
	
	public TerrainLayer(int width, int height, int level) {
		super(width, height);
		terrainTiles = ResourcePool.getInstance().getTerrainTiles(level);
		paths = ResourcePool.getInstance().getPaths(level);
		layerWidth = Constants.TERRAINLAYER_WIDTH;
		layerHeight = Constants.TERRAINLAYER_HEIGHT;
		//defineLayer();
		showLayer();
	}
	
	private void defineLayer() {
		terrainTiles = new Tile[layerHeight][layerWidth];
		for(int y = 0; y < terrainTiles.length; y++) {
			for (int x = 0; x < terrainTiles[y].length; x++) {
				terrainTiles[y][x] = new TerrainTile(x*Constants.TILESIZE, y*Constants.TILESIZE, 0 ,Constants.TILESIZE);
			}
		}
	}
	
	private void showLayer() {
		for(int y = 0; y < terrainTiles.length; y++) {
			for (int x = 0; x < terrainTiles[y].length; x++) {
				terrainTiles[y][x].drawToCanvas(getGraphicsContext2D());
			}
		}
	}
}
