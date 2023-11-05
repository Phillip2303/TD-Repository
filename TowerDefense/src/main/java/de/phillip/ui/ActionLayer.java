package de.phillip.ui;

import de.phillip.controls.Constants;
import de.phillip.models.Enemy;
import javafx.scene.canvas.Canvas;

public class ActionLayer extends Canvas {
	
	private int layerWidth;
	private int layerHeight;
	Enemy enemy;

	public ActionLayer(double width, double height, int level) {
		super(width, height);
		layerWidth = Constants.TERRAINLAYER_WIDTH;
		layerHeight = Constants.TERRAINLAYER_HEIGHT;
		defineLayer();
		showLayer();
	}
	
	public void defineLayer() {
		enemy = new Enemy(Constants.TILESIZE, Constants.TILESIZE);
		enemy.setDrawPosition(6*Constants.TILESIZE, 0*Constants.TILESIZE);
	}
	
	public void showLayer() {
		enemy.drawToCanvas(getGraphicsContext2D());
	}

}
