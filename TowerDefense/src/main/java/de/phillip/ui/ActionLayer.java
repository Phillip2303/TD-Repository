package de.phillip.ui;

import de.phillip.controllers.WaveController;
import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.ResourcePool;
import de.phillip.gameUtils.Transformer;
import de.phillip.models.Enemy;
import de.phillip.rendering.Renderer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;

public class ActionLayer extends Canvas {
	
	private int layerWidth;
	private int layerHeight;
	private int level;
	private int speedLevel = 85;
	private Tile[][] paths;
	private WaveController waveController;
	private Renderer renderer;

	public ActionLayer(double tileWidth, double tileHeight, int level) {
		super(tileWidth*Constants.TILESIZE, tileHeight*Constants.TILESIZE);
		renderer = new Renderer(getGraphicsContext2D());
		this.level = level;
		layerWidth = Constants.TERRAINLAYER_WIDTH;
		layerHeight = Constants.TERRAINLAYER_HEIGHT;
		paths = ResourcePool.getInstance().getPaths(level);
		waveController = new WaveController();
		waveController.setLevel(level);
	}

	public void setLevel(int level) { 
		this.level = level;
		paths = ResourcePool.getInstance().getPaths(level);
		waveController.setLevel(level);
	}
	
	public void update(float secondsSinceLastFrame) {
		renderer.prepare();
		checkForNewEnemies(secondsSinceLastFrame);
		updateEnemies(secondsSinceLastFrame);
		renderer.render();
	}
	
	private void updateEnemies(float secondsSinceLastFrame) {
		renderer.getActors().forEach(actor -> {
			double speed = speedLevel*secondsSinceLastFrame;
			Enemy enemy = (Enemy) actor;
			Point2D currentPosition = calculateTilePosition(enemy.getCenter(), enemy.getRotation());
			if (!enemy.hasReachedEnd() && paths[(int) currentPosition.getY()][(int) currentPosition.getX()].getID() == 9) {
				enemy.setReachedEnd();
				enemy.leavePath(speed);
			} else {
				if (!enemy.hasReachedEnd()) {
					//check for path
					if (!isPath(enemy, speed)) {
						enemy.setRotation(getNewRotation(enemy));
					}
					enemy.setCurrentThrustVector(speed);
					enemy.update();
				} else {
					enemy.leavePath(speed);
				}
			}
		});
		renderer.getActors().removeIf(b -> ((Enemy)b).getIsOff());
	}
	
	private void checkForNewEnemies(float secondsSinceLastFrame) {
		if (waveController.hasMoreEnemies()) {
			Enemy newEnemy = waveController.getEnemy(secondsSinceLastFrame);
			if (newEnemy != null) {
				renderer.getActors().add(newEnemy);
			}
		}
	}
	
	private boolean isPath(Enemy enemy, double speed) {
		Point2D futurePosition = enemy.getCenter().
				add(enemy.calculateNewThrust(speed, Math.toRadians(-enemy.getRotation())));
		Point2D tilePosition = calculateTilePosition(futurePosition, enemy.getRotation());
		if (paths[(int) tilePosition.getY()][(int) tilePosition.getX()].getID() == 1 || paths[(int) tilePosition.getY()][(int) tilePosition.getX()].getID() == 9) {
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
		default:
			System.out.println("Default");
			break;
		}
		return rotation;
	}
	
	private Point2D calculateTilePosition(Point2D point, double rotation) {
		Point2D tile = new Point2D(0, 0);
		switch ((int) rotation) {
		case 0: 
			tile = Transformer.transformPixelsCoordinatesToTile(point.getX(), point.getY() + Constants.TILESIZE/2);
			break;
		case 90:
			tile = Transformer.transformPixelsCoordinatesToTile(point.getX() - Constants.TILESIZE/2, point.getY());
			break;
		case 180:
			tile = Transformer.transformPixelsCoordinatesToTile(point.getX(), point.getY() - Constants.TILESIZE/2);
			break;
		case 270:
			tile = Transformer.transformPixelsCoordinatesToTile(point.getX() + Constants.TILESIZE/2, point.getY());
			break;
		default:
			System.out.println("Calculate Tile Position");
			break;
		}
		return tile;
	}
}
