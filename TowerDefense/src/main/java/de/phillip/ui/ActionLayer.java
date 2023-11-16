package de.phillip.ui;

import java.util.ArrayList;
import java.util.List;

import de.phillip.controls.Constants;
import de.phillip.controls.ResourcePool;
import de.phillip.models.Enemy;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;

public class ActionLayer extends Canvas {
	
	private int layerWidth;
	private int layerHeight;
	Enemy enemy;
	private int level;
	private List<Enemy> enemies;
	private int speedLevel = 85;
	private Tile[][] paths;

	public ActionLayer(double width, double height, int level) {
		super(width, height);
		enemies = new ArrayList<Enemy>();
		this.level = level;
		layerWidth = Constants.TERRAINLAYER_WIDTH;
		layerHeight = Constants.TERRAINLAYER_HEIGHT;
		paths = ResourcePool.getInstance().getPaths(level);
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
		paths = ResourcePool.getInstance().getPaths(level);
	}
	
	public void update(float secondsSinceLastFrame) {
		double speed = speedLevel*secondsSinceLastFrame;
		//check for path
		if (isPath(enemy, speed)) {
			enemy.setRotation(enemy.getRotation());
		} else {
			enemy.setRotation(180);
		}
		enemy.setCurrentThrustVector(speed);
		enemy.update();
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}
	
	public boolean isPath(Enemy enemy, double speed) {
		Point2D newPosition = enemy.getCenter().
				add(enemy.calculateNewThrust(speed, Math.toRadians(-enemy.getRotation())));
		for(int y = 0; y < paths.length; y++) {
			for (int x = 0; x < paths[y].length; x++) {
				int posX = paths[y][x].getPosX() + Constants.TILESIZE / 2;
				int posY = paths[y][x].getPosY() + Constants.TILESIZE / 2;
				if (posX == newPosition.getX() && posY == newPosition.getY()) {
					int ID = paths[y][x].getID();
					System.out.println("PosX: " + posX + " PosY: " + posY + " ID: " + ID);
					if (ID == 1) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
