package de.phillip.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import de.phillip.events.FXEventBus;
import de.phillip.events.GameEvent;
import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.ResourcePool;
import de.phillip.gameUtils.Transformer;
import de.phillip.models.CanvasButton;
import de.phillip.models.CanvasLayer;
import de.phillip.models.Drawable;
import de.phillip.models.TurretTile;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class InfoLayer extends Canvas implements CanvasLayer, EventHandler<MouseEvent> {
	
	private TurretTile[][] turretTiles;
	private Image turretSprite;
	private CanvasButton startWaveButton;
	private Image startWave;
	private List<Drawable> drawables = new ArrayList<>();
	private Consumer<ActionEvent> consumer;
	private TurretTile overlay;
	private boolean hasSelected;
	private State currentState = State.OBSERVE;
	
	public enum State {
		OVERLAY,
		OBSERVE;
	}

	public InfoLayer(int tileWidth, int tileHeight, int level) {
		super(tileWidth*Constants.TILESIZE, tileHeight*Constants.TILESIZE);
		FXEventBus.getInstance().addEventHandler(MouseEvent.MOUSE_CLICKED, this);
		FXEventBus.getInstance().addEventHandler(MouseEvent.MOUSE_MOVED, this);
		turretSprite = ResourcePool.getInstance().getTurretSprite();
		startWave = ResourcePool.getInstance().getStartWave();
		createTurretTiles();
		createStartWaveButton();
	}
	
	private void createStartWaveButton() {
		startWaveButton = new CanvasButton(startWave, 19 * Constants.TILESIZE - 8, 6 * Constants.TILESIZE, 120, 20);
		drawables.add(startWaveButton);
	}
	
	private void createTurretTiles() {
		int ID = 0, xIndex = 0, yIndex = 0;
		turretTiles = new TurretTile[2][4];
		for(int y = 7; y < 9; y++) {
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
	}
	
	private void mouseMoved(double eventX, double eventY) {
		switch (currentState) {
		case OBSERVE:
			Point2D tile = Transformer.transformPixelsCoordinatesToTile(eventX, eventY);
			for(int y = 0; y < turretTiles.length; y++ ) {
				for (int x = 0; x < turretTiles[y].length; x++) {
					if (turretTiles[y][x].equals(tile)) {
						turretTiles[y][x].setActive(true);
						//System.out.println("Point X: " + point.getX());
						//System.out.println("Point Y: " + point.getY());
					} else {
						turretTiles[y][x].setActive(false);
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
				for(int y = 0; y < turretTiles.length; y++ ) {
					for (int x = 0; x < turretTiles[y].length; x++) {
						if (turretTiles[y][x].isActive()) {
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
			break;
		case OVERLAY:
			ActionEvent overlayEvent = new ActionEvent(overlay, null);
			consumer.accept(overlayEvent);
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
			drawables.remove(overlay);
			overlay = null;
			currentState = State.OBSERVE;
			break;
		default: 
			break;
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
	
	public void setOnAction(Consumer<ActionEvent> consumer) {
		this.consumer = consumer;
	}

	@Override
	public void handle(MouseEvent mouseEvent) {
		switch (mouseEvent.getEventType().getName()) {
		case "MOUSE_MOVED":
			mouseMoved(mouseEvent.getX(), mouseEvent.getY());
			break;
		case "MOUSE_CLICKED":
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
}
