package de.phillip.ui;

import java.util.ArrayList;
import java.util.List;

import de.phillip.controllers.TurretController;
import de.phillip.controllers.WaveController;
import de.phillip.events.FXEventBus;
import de.phillip.events.GameEvent;
import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.ResourcePool;
import de.phillip.gameUtils.Transformer;
import de.phillip.models.CanvasButton;
import de.phillip.models.CanvasLayer;
import de.phillip.models.Drawable;
import de.phillip.models.GameInfo;
import de.phillip.models.TurretTile;
import de.phillip.models.transferObjects.TurretTO;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class InfoLayer extends Canvas implements CanvasLayer, EventHandler<Event> {
	
	private TurretTile[][] turretTiles;
	private Image turretSprite;
	private CanvasButton startWaveButton;
	private Image startWave;
	private List<Drawable> drawables = new ArrayList<>();
	private TurretTile overlay;
	private boolean hasSelected;
	private State currentState = State.OBSERVE;
	private int level;
	private GameInfo gameInfo;
	private TurretController turretController;
	private WaveController waveController;
	private boolean newLevel; 
	
	public enum State {
		OVERLAY,
		OBSERVE;
	}

	public InfoLayer(int tileWidth, int tileHeight, int level, TurretController turretController, WaveController waveController) {
		super(tileWidth*Constants.TILESIZE, tileHeight*Constants.TILESIZE);
		this.level = level;
		this.turretController = turretController;
		this.waveController = waveController;
		gameInfo = GameInfo.getInstance();
		FXEventBus.getInstance().addEventHandler(MouseEvent.MOUSE_CLICKED, this);
		FXEventBus.getInstance().addEventHandler(MouseEvent.MOUSE_MOVED, this);
		turretSprite = ResourcePool.getInstance().getTurretTileSprite();
		startWave = ResourcePool.getInstance().getStartWave();
		createTurretTiles(turretController.getTurrets().size());
		createStartWaveButton();
		drawables.add(gameInfo);
		gameInfo.setWaveCount(waveController.getWaveCount());
	}
	
	private void createStartWaveButton() {
		startWaveButton = new CanvasButton(startWave, 19 * Constants.TILESIZE - 8, 6 * Constants.TILESIZE - 16, 120, 20);
		drawables.add(startWaveButton);
	}
	
	private void createTurretTiles(int length) {
		int xIndex = 0, yIndex = 0, listIndex = 0;
		switch (length) {
		case 2:
			turretTiles = new TurretTile[1][2];
			for(int y = 7; y < 8; y++) {
				for (int x = 18; x < 20; x++) {
					int ID = turretController.getTurrets().get(listIndex).getId();
					TurretTile turretTile = new TurretTile(x, y, ID, Constants.TILESIZE);
					turretTiles[yIndex][xIndex] = turretTile;
					turretTile.setSprite(turretSprite);
					turretTile.setTurretTO(turretController.getTurrets().get(listIndex));
					drawables.add(turretTile);
					xIndex++;
					listIndex++;
				}
			}
			break;
		case 4:
			turretTiles = new TurretTile[1][4];
			for(int y = 7; y < 8; y++) {
				for (int x = 18; x < 22; x++) {
					int ID = turretController.getTurrets().get(listIndex).getId();
					TurretTile turretTile = new TurretTile(x, y, ID, Constants.TILESIZE);
					turretTiles[yIndex][xIndex] = turretTile;
					turretTile.setSprite(turretSprite);
					turretTile.setTurretTO(turretController.getTurrets().get(listIndex));
					drawables.add(turretTile);
					xIndex++;
					listIndex++;
				}
			}
			break;
		case 6:
			turretTiles = new TurretTile[2][4];
			for(int y = 7; y < 9; y++) {
				if (y == 7) {
					for (int x = 18; x < 22; x++) {
						int ID = turretController.getTurrets().get(listIndex).getId();
						TurretTile turretTile = new TurretTile(x, y, ID, Constants.TILESIZE);
						turretTiles[yIndex][xIndex] = turretTile;
						turretTile.setSprite(turretSprite);
						turretTile.setTurretTO(turretController.getTurrets().get(listIndex));
						drawables.add(turretTile);
						xIndex++;
						listIndex++;
					}
					yIndex++;
					xIndex = 0;
				} else {
					for (int x = 18; x < 20; x++) {
						int ID = turretController.getTurrets().get(listIndex).getId();
						TurretTile turretTile = new TurretTile(x, y, ID, Constants.TILESIZE);
						turretTiles[yIndex][xIndex] = turretTile;
						turretTile.setSprite(turretSprite);
						turretTile.setTurretTO(turretController.getTurrets().get(listIndex));
						drawables.add(turretTile);
						xIndex++;
						listIndex++;
					}
					yIndex++;
					xIndex = 0;
				}
			}
		default: 
			break;
		}
	}
	
	private void mouseMoved(double eventX, double eventY) {
		switch (currentState) {
		case OBSERVE:
			Point2D tile = Transformer.transformPixelsCoordinatesToTile(eventX, eventY);
			for(int y = 0; y < turretTiles.length; y++ ) {
				for (int x = 0; x < turretTiles[y].length; x++) {
					if (turretTiles[y][x] != null) {
						if (turretTiles[y][x].equals(tile)) {
							turretTiles[y][x].setActive(true);
						} else {
							turretTiles[y][x].setActive(false);
						}
					}
				}
			}
			if (startWaveButton.contains(new Point2D(eventX, eventY))) {
				startWaveButton.setActive(true);
			} else {
				startWaveButton.setActive(false);
			}
			break;
		case OVERLAY:
			overlay.setDrawPosition(eventX - Constants.TILESIZE / 2, eventY - Constants.TILESIZE / 2);
			break;
		default: 
			break;
		}
	}
	
	private void mouseLeftClicked(double eventX, double eventY) {
		switch (currentState) {
		case OBSERVE:
			if (startWaveButton.isActive()) {
				FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.TD_STARTWAVE, null));
				drawables.remove(startWaveButton);
				gameInfo.setDrawWaveCount(true);
			} else {
				Point2D tile = Transformer.transformPixelsCoordinatesToTile(eventX, eventY);
				for(int y = 0; y < turretTiles.length; y++ ) {
					for (int x = 0; x < turretTiles[y].length; x++) {
						if (turretTiles[y][x] != null) {
							if (turretTiles[y][x].equals(tile) ) {
								TurretTile tempTurret = turretTiles[y][x];
								if (tempTurret.isVisible()) {
									overlay = new TurretTile(tempTurret.getPosX() - Constants.TILESIZE / 2, tempTurret.getPosY() - Constants.TILESIZE / 2, tempTurret.getID(), Constants.TILESIZE);
									overlay.setOverlay(true);
									overlay.setSprite(turretSprite);
									overlay.setActive(true);
									overlay.setVisible(true);
									drawables.add(overlay);
									currentState = State.OVERLAY;
								}
							}	
						}
					}
				}
			}
			break;
		case OVERLAY:
			FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.TD_PLACETURRET, overlay));
			drawables.remove(overlay);
			overlay = null;
			currentState = State.OBSERVE;
			
			break;
		default: 
			break;
		}
	}
	
	private void mouseRightClicked(double x, double y) {
		switch (currentState) {
		case OBSERVE:
			break;
		case OVERLAY:
			invalidateTiles();
			drawables.remove(overlay);
			overlay = null;
			currentState = State.OBSERVE;
			break;
		default: 
			break;
		}
	}
	
	private void invalidateTiles() {
		for(int y = 0; y < turretTiles.length; y++ ) {
			for (int x = 0; x < turretTiles[y].length; x++) {
				if (turretTiles[y][x] != null) {
					turretTiles[y][x].setActive(false);
				}
			}
		}
	}

	@Override
	public List<Drawable> getDrawables() {
		return drawables;
	}

	@Override
	public void prepareLayer() {
		getGraphicsContext2D().drawImage(ResourcePool.getInstance().getBackground(), 0, 0);
		getGraphicsContext2D().clearRect(0, 0, Constants.TERRAINLAYER_WIDTH*Constants.TILESIZE, Constants.TERRAINLAYER_HEIGHT*Constants.TILESIZE);
	}
	
	public void updateLayer(float secondsSinceLastFrame) {
		if (newLevel) {
			createTurretTiles(turretController.getTurrets().size());
			newLevel = false;
		}
		checkTurretVisibility();
		gameInfo.setCurrentWave(waveController.getCurrentWave());
	}
	
	public void checkTurretVisibility() {
		try {
			for(int y = 0; y < turretTiles.length; y++) {
				for (int x = 0; x < turretTiles[y].length; x++) {
						if (turretTiles[y][x] != null) {
							if (turretTiles[y][x].getCost() <= gameInfo.getMoney()) {
								turretTiles[y][x].setVisible(true);
							} else {
								turretTiles[y][x].setVisible(false);
							}
						}
				}
			}
		} catch (NullPointerException e){
			e.printStackTrace();
		}
	}

	@Override
	public void handle(Event event) {
		switch (event.getEventType().getName()) {
		case "MOUSE_MOVED":
			MouseEvent mouseMoveEvent = (MouseEvent) event;
			mouseMoved(mouseMoveEvent.getX(), mouseMoveEvent.getY());
			break;
		case "MOUSE_CLICKED":
			MouseEvent mouseClickEvent = (MouseEvent) event;
			switch (mouseClickEvent.getButton()) {
				case PRIMARY:
					mouseLeftClicked(mouseClickEvent.getX(), mouseClickEvent.getY());
					break;
				case SECONDARY:
					mouseRightClicked(mouseClickEvent.getX(), mouseClickEvent.getY());
					break;
				default: 
					break;
			}
			break;
		default: 
			break;
		}
	}
	
	public void setLevel(int level) {
		drawables.clear();
		this.level = level;
		currentState = State.OBSERVE;
		gameInfo.setDrawWaveCount(false);
		gameInfo.setCurrentWave(1);
		gameInfo.setWaveCount(waveController.getWaveCount());
		drawables.add(startWaveButton);
		drawables.add(gameInfo);
		newLevel = true;
	}

	@Override
	public void resetGame() {
		setLevel(1);
	}
}
