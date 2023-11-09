package de.phillip.ui;

import java.util.ArrayList;
import java.util.List;

import de.phillip.controls.Constants;
import de.phillip.controls.ResourcePool;
import de.phillip.models.Enemy;
import javafx.scene.canvas.Canvas;

public class ActionLayer extends Canvas {
	
	private int layerWidth;
	private int layerHeight;
	Enemy enemy;
	private int level;
	private List<Enemy> enemies;
	private int speedLevel = 85;

	public ActionLayer(double width, double height, int level) {
		super(width, height);
		enemies = new ArrayList<Enemy>();
		this.level = level;
		layerWidth = Constants.TERRAINLAYER_WIDTH;
		layerHeight = Constants.TERRAINLAYER_HEIGHT;
		defineLayer();
		//showLayer();
	}
	
	public void defineLayer() {
		enemy = new Enemy(Constants.TILESIZE, Constants.TILESIZE);
		enemy.setDrawPosition(6*Constants.TILESIZE, 0*Constants.TILESIZE);
		enemies.add(enemy);
	}
	
	/*public void showLayer() {
		enemy.drawToCanvas(getGraphicsContext2D());
	}*/

	public void setLevel(int level) { 
		this.level = level;
	}
	
	public void update(float secondsSinceLastFrame) {
		enemy.setCurrentThrustVector(speedLevel*secondsSinceLastFrame);
		enemy.update();
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}
}
