package de.phillip.ui;

import java.util.ArrayList;
import java.util.List;

import de.phillip.events.FXEventBus;
import de.phillip.events.GameEvent;
import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.ResourcePool;
import de.phillip.gameUtils.Transformer;
import de.phillip.models.CanvasButton;
import de.phillip.models.CanvasLayer;
import de.phillip.models.Drawable;
import de.phillip.models.TurretTile;
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
	
	public enum State {
		OVERLAY,
		OBSERVE;
	}

	public InfoLayer(int tileWidth, int tileHeight, int level) {
		super(tileWidth*Constants.TILESIZE, tileHeight*Constants.TILESIZE);
		this.level = level;
		FXEventBus.getInstance().addEventHandler(MouseEvent.MOUSE_CLICKED, this);
		FXEventBus.getInstance().addEventHandler(MouseEvent.MOUSE_MOVED, this);
		turretSprite = ResourcePool.getInstance().getTurretTileSprite();
		startWave = ResourcePool.getInstance().getStartWave();
		createTurretTiles(this.level);
		createStartWaveButton();
	}
	
	private void createStartWaveButton() {
		startWaveButton = new CanvasButton(startWave, 19 * Constants.TILESIZE - 8, 6 * Constants.TILESIZE, 120, 20);
		drawables.add(startWaveButton);
	}
	
	private void createTurretTiles(int level) {
		int ID = 0, xIndex = 0, yIndex = 0;
		switch (level) {
		case 1:
			turretTiles = new TurretTile[1][2];
			for(int y = 7; y < 8; y++) {
				for (int x = 18; x < 20; x++) {
					TurretTile turretTile = new TurretTile(x, y, ID, Constants.TILESIZE);
					turretTiles[yIndex][xIndex] = turretTile;
					turretTile.setSprite(turretSprite);
					drawables.add(turretTile);
					ID++;
					xIndex++;
				}
				yIndex++;
				xIndex = 0;
			}
			break;
		case 2:
			turretTiles = new TurretTile[1][4];
			for(int y = 7; y < 8; y++) {
				for (int x = 18; x < 22; x++) {
					TurretTile turretTile = new TurretTile(x, y, ID, Constants.TILESIZE);
					turretTiles[yIndex][xIndex] = turretTile;
					turretTile.setSprite(turretSprite);
					drawables.add(turretTile);
					ID++;
					xIndex++;
				}
				yIndex++;
				xIndex = 0;
			}
			break;
		case 3:
			turretTiles = new TurretTile[2][4];
			for(int y = 7; y < 9; y++) {
				if (y == 7) {
					for (int x = 18; x < 22; x++) {
						TurretTile turretTile = new TurretTile(x, y, ID, Constants.TILESIZE);
						turretTiles[yIndex][xIndex] = turretTile;
						turretTile.setSprite(turretSprite);
						drawables.add(turretTile);
						ID++;
						xIndex++;
					}
					yIndex++;
					xIndex = 0;
				} else {
					for (int x = 18; x < 20; x++) {
						TurretTile turretTile = new TurretTile(x, y, ID, Constants.TILESIZE);
						turretTiles[yIndex][xIndex] = turretTile;
						turretTile.setSprite(turretSprite);
						drawables.add(turretTile);
						ID++;
						xIndex++;
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
							//System.out.println("Point X: " + point.getX());
							//System.out.println("Point Y: " + point.getY());
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
			/*System.out.println("Point X: " + eventX);
			System.out.println("Point Y: " + eventY);*/
			System.out.println("Point X: " + overlay.getPosX());
			System.out.println("Point Y: " + overlay.getPosY());
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
			} else {
				Point2D tile = Transformer.transformPixelsCoordinatesToTile(eventX, eventY);
				for(int y = 0; y < turretTiles.length; y++ ) {
					for (int x = 0; x < turretTiles[y].length; x++) {
						if (turretTiles[y][x] != null) {
							if (turretTiles[y][x].equals(tile)) {
								TurretTile tempTurret = turretTiles[y][x];
								System.out.println("ID: " + turretTiles[y][x].getID());
								overlay = new TurretTile(tempTurret.getPosX() - Constants.TILESIZE / 2, tempTurret.getPosY() - Constants.TILESIZE / 2, tempTurret.getID(), Constants.TILESIZE);
								overlay.setSprite(turretSprite);
								overlay.setActive(true);
								drawables.add(overlay);
								currentState = State.OVERLAY;
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
		this.level = level;
		currentState = State.OBSERVE;
		drawables.add(startWaveButton);
		createTurretTiles(this.level);
	}
}
