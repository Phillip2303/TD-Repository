package de.phillip.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.phillip.controllers.WaveController;
import de.phillip.events.FXEventBus;
import de.phillip.events.GameEvent;
import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.ResourcePool;
import de.phillip.gameUtils.Transformer;
import de.phillip.models.Actor;
import de.phillip.models.CanvasLayer;
import de.phillip.models.Drawable;
import de.phillip.models.Enemy;
import de.phillip.models.Tile;
import de.phillip.models.Turret;
import de.phillip.models.TurretTile;
import de.phillip.ui.InfoLayer.State;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class ActionLayer extends Canvas implements CanvasLayer, EventHandler<Event> {
	
	private int layerWidth;
	private int layerHeight;
	private int level;
	private int speedLevel = 85;
	private Tile[][] paths;
	private WaveController waveController;
	private List<Actor> actors = new ArrayList<>();
	private boolean waveStarted;
	private Image turretSprite;
	private Image turretCannonSprite;

	public ActionLayer(double tileWidth, double tileHeight, int level) {
		super(tileWidth*Constants.TILESIZE, tileHeight*Constants.TILESIZE);
		turretSprite = ResourcePool.getInstance().getTurretSprite();
		turretCannonSprite = ResourcePool.getInstance().getTurretCannonSprite();
		FXEventBus.getInstance().addEventHandler(GameEvent.TD_STARTWAVE, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.TD_PLACETURRET, this);
		FXEventBus.getInstance().addEventHandler(MouseEvent.MOUSE_CLICKED, this);
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
		if (waveStarted) {
			checkForNewEnemies(secondsSinceLastFrame);
			updateActors(secondsSinceLastFrame);
			//updateEnemies(secondsSinceLastFrame);
		}
	}
	
	private void updateActors(float secondsSinceLastFrame) {
		actors.forEach(actor -> {
			switch (actor.getClass().getName()) {
			case "de.phillip.models.Enemy":
				updateEnemy(secondsSinceLastFrame, (Enemy) actor);
				break;
			case "de.phillip.models.Turret":
				updateTurret(secondsSinceLastFrame, (Turret) actor);
				break;
			default: 
				break;
			}
		});
		actors.removeIf(actor -> actor instanceof de.phillip.models.Enemy && ((Enemy)actor).getIsOff());
	}
	
	private void updateTurret(float secondsSinceLastFrame, Turret turret) {
		turret.setRotation(turret.getRotation() + 3);
	}
	
	private void updateEnemy(float secondsSinceLastFrame, Enemy enemy) {
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
				}
				enemy.setCurrentThrustVector(speed);
				enemy.update();
			} else {
				enemy.leavePath(speed);
			}
		}
	}
	
	private void updateEnemies(float secondsSinceLastFrame) {
		actors.forEach(actor -> {
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
		actors.removeIf(b -> ((Enemy)b).getIsOff());
	}
	
	private void checkForNewEnemies(float secondsSinceLastFrame) {
		if (waveController.hasMoreEnemies()) {
			Enemy newEnemy = waveController.getEnemy(secondsSinceLastFrame);
			if (newEnemy != null) {
				actors.add(newEnemy);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Drawable> getDrawables() {
		return (List<Drawable>) (List<?>) actors;
	}

	@Override
	public void prepareLayer() {
		getGraphicsContext2D().clearRect(0, 0, Constants.TERRAINLAYER_WIDTH*Constants.TILESIZE, Constants.TERRAINLAYER_HEIGHT*Constants.TILESIZE);
	}

	@Override
	public void handle(Event event) {
		switch (event.getEventType().getName()) {
		case "TD_STARTWAVE":
			waveStarted = true;
			break;
		case "TD_PLACETURRET":
			GameEvent gameEvent = (GameEvent) event;
			TurretTile overlay = (TurretTile) gameEvent.getData();
			if (checkValidTilePosition(overlay)) {
				placeTurret(overlay);
			}
		case "MOUSE_CLICKED":
			MouseEvent mouseEvent = (MouseEvent) event;
			switch (mouseEvent.getButton()) {
				case PRIMARY:
					mouseLeftClicked(mouseEvent.getX(), mouseEvent.getY());
					break;
				case SECONDARY:
					mouseRightClicked(mouseEvent.getX(), mouseEvent.getY());
					break;
				default: 
					break;
			}
			break;
		default: 
			break;
		}
	}
	
	private void mouseLeftClicked(double eventX, double eventY) {
		List<Actor> turrets = actors.stream().filter(actor -> actor instanceof Turret).collect(Collectors.toList());
		Point2D selectedTileCoor = Transformer.transformPixelsCoordinatesToTile(eventX, eventY);
		double x = selectedTileCoor.getX() * Constants.TILESIZE;
		double y = selectedTileCoor.getY() * Constants.TILESIZE;
		for (Actor turret: turrets) {
			if (turret.getDrawPosition().getX() == x)  {
				if (turret.getDrawPosition().getY() == y) {
					((Turret) turret).select(true);
				}
			}
		}
	}
	
	private void mouseRightClicked(double x, double y) {
		
	}
	
	private void placeTurret(TurretTile overlay) {
		 Turret turret = new Turret(Constants.TILESIZE, Constants.TILESIZE, turretSprite, turretCannonSprite, overlay.getID());
		 Point2D overlayCenter = overlay.getCenter();
		 Point2D selectedTileCoor = Transformer.transformPixelsCoordinatesToTile(overlayCenter.getX(), overlayCenter.getY());
		 turret.setDrawPosition(selectedTileCoor.getX() * Constants.TILESIZE, selectedTileCoor.getY() * Constants.TILESIZE);
		 actors.add(turret);
	}
	
	private boolean checkValidTilePosition(TurretTile overlay) {
		Point2D overlayCenter = overlay.getCenter();
		if (overlayCenter.getX() < layerWidth * Constants.TILESIZE) {
			Point2D selectedTileCoor = Transformer.transformPixelsCoordinatesToTile(overlayCenter.getX(), overlayCenter.getY());
			Tile[][] terrainTiles = ResourcePool.getInstance().getTerrainTiles(level);
			Tile selectedTile = terrainTiles[(int) selectedTileCoor.getY()][(int) selectedTileCoor.getX()];
			if (selectedTile.getID() == 8) {
				return true;
			}
		}
		return false;
	}
}
