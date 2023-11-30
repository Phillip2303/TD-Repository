package de.phillip.ui;

import java.util.ArrayList;
import java.util.List;

import de.phillip.controllers.WaveController;
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
	private WaveController waveController;

	public ActionLayer(double width, double height, int level) {
		super(width, height);
		enemies = new ArrayList<Enemy>();
		this.level = level;
		layerWidth = Constants.TERRAINLAYER_WIDTH;
		layerHeight = Constants.TERRAINLAYER_HEIGHT;
		paths = ResourcePool.getInstance().getPaths(level);
		defineLayer();
		waveController = new WaveController();
	}
	
	public void defineLayer() {
		enemy = new Enemy(Constants.TILESIZE, Constants.TILESIZE);
		enemy.setDrawPosition(6*Constants.TILESIZE, 0*Constants.TILESIZE);
		enemies.add(enemy);
	}

	public void setLevel(int level) { 
		this.level = level;
		paths = ResourcePool.getInstance().getPaths(level);
		waveController.setLevel(level);
	}
	
	public void update(float secondsSinceLastFrame) {
		if (enemy == null) {
			return;
		}
		double speed = speedLevel*secondsSinceLastFrame;
		Point2D currentPosition = calculateTilePosition(enemy.getCenter(), enemy.getRotation());
		if (!enemy.hasReachedEnd() && paths[(int) currentPosition.getY()][(int) currentPosition.getX()].getID() == 9) {
			enemy.setReachedEnd();
			enemy.leavePath(speed);
		} else {
			if (!enemy.hasReachedEnd()) {
				//check for path
				if (!isPath(enemy, speed)) {
					enemy.setRotation(getNewRotation(enemy));
					//enemy.setRotation(enemy.getRotation());
				}
				enemy.setCurrentThrustVector(speed);
				enemy.update();
			} else {
				enemy.leavePath(speed);
			}
		}
		if (enemy.getIsOff()) {
			enemies.remove(enemy);
			enemy = null;
		}
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}
	
	private boolean isPath(Enemy enemy, double speed) {
		Point2D futurePosition = enemy.getCenter().
				add(enemy.calculateNewThrust(speed, Math.toRadians(-enemy.getRotation())));
		Point2D tilePosition = calculateTilePosition(futurePosition, enemy.getRotation());
		if (paths[(int) tilePosition.getY()][(int) tilePosition.getX()].getID() == 1) {
			return true;
		}
		return false;
	}
	
	private double getNewRotation(Enemy enemy) {
		Point2D tilePosition = calculateTilePosition(enemy.getCenter(), enemy.getRotation());
		double rotation = 0;
		switch ((int) enemy.getRotation()) {
		case 0, 180:
			if (paths[(int) tilePosition.getY()][(int) tilePosition.getX() - 1].getID() == 1) {
				rotation = 90;
			} else {
				rotation = 270;
			}
			break;
		case 90, 270:
			if (paths[(int) tilePosition.getY() - 1][(int) tilePosition.getX()].getID() == 1) {
				rotation = 180;
			} else {
				rotation = 0;
			}
			break;
		}
		return rotation;
	}
	
	private Point2D calculateTilePosition(Point2D point, double rotation) {
		int xIndex = 0, yIndex = 0;
		int x = (int) point.getX();
		int y = (int) point.getY();
		switch ((int) rotation) {
		case 0: 
			yIndex = (y + Constants.TILESIZE/2)/Constants.TILESIZE;
			xIndex = x/Constants.TILESIZE;
			break;
		case 90:
			xIndex = (x - Constants.TILESIZE/2)/Constants.TILESIZE;
			yIndex = y/Constants.TILESIZE;
			break;
		case 180:
			yIndex = (y - Constants.TILESIZE/2)/Constants.TILESIZE;
			xIndex = x/Constants.TILESIZE;
			break;
		case 270:
			xIndex = (x + Constants.TILESIZE/2)/Constants.TILESIZE;
			yIndex = y/Constants.TILESIZE;
			break;
		}
		return new Point2D(xIndex, yIndex);
	}
}
