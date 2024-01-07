package de.phillip.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.ResourcePool;
import de.phillip.gameUtils.Transformer;
import de.phillip.models.CanvasButton;
import de.phillip.models.CanvasLayer;
import de.phillip.models.Drawable;
import de.phillip.models.TurretTile;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class InfoLayer extends Canvas implements CanvasLayer {
	
	private TurretTile[][] turretTiles;
	private Image turretSprite;
	private CanvasButton startWaveButton;
	private Image startWave;
	private List<Drawable> drawables = new ArrayList<>();
	private Consumer<ActionEvent> consumer;
	private TurretTile overlay;
	private boolean hasSelected;

	public InfoLayer(int tileWidth, int tileHeight, int level) {
		super(tileWidth*Constants.TILESIZE, tileHeight*Constants.TILESIZE);
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
		if (hasSelected) {
			overlay.setDrawPosition(eventX, eventY);
			System.out.println("Point X: " + eventX);
			System.out.println("Point Y: " + eventY);
		} else {
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
		}
	}
	
	private void mouseLeftClicked(double eventX, double eventY) {
		if (startWaveButton.isActive()) {
			ActionEvent startWave = new ActionEvent(startWaveButton, null);
			consumer.accept(startWave);
			drawables.remove(startWaveButton);
		} else {
			for(int y = 0; y < turretTiles.length; y++ ) {
				for (int x = 0; x < turretTiles[y].length; x++) {
					if (turretTiles[y][x].isActive()) {
						TurretTile tempTurret = turretTiles[y][x];
						System.out.println("ID: " + turretTiles[y][x].getID());
						overlay = new TurretTile(tempTurret.getPosX(), tempTurret.getPosY(), tempTurret.getID(), Constants.TILESIZE);
						overlay.setSprite(turretSprite);
						overlay.setActive(true);
						drawables.add(overlay);
						hasSelected = true;
					}
				}
			}
		}
	}
	
	private void mouseRightClicked(double x, double y) {
		if (overlay != null) {
			drawables.remove(overlay);
			overlay = null;
			hasSelected = false;
		}
	}
	
	public void handleMouseEvent(MouseEvent mouseEvent) {
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

	@Override
	public List<Drawable> getDrawables() {
		return drawables;
	}

	@Override
	public void prepareLayer() {
		getGraphicsContext2D().drawImage(ResourcePool.getInstance().getBackground(), 0, 0);
	}
	
	public void setOnAction(Consumer<ActionEvent> consumer) {
		this.consumer = consumer;
	}
}
