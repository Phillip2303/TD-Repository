package de.phillip.ui;

import java.util.ArrayList;
import java.util.List;

import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.ResourcePool;
import de.phillip.gameUtils.Transformer;
import de.phillip.models.CanvasButton;
import de.phillip.models.CanvasLayer;
import de.phillip.models.Drawable;
import de.phillip.models.TurretTile;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class InfoLayer extends Canvas implements CanvasLayer {
	
	private TurretTile[][] turretTiles;
	private Image turretSprite;
	private CanvasButton startWaveButton;
	private Image startWave;
	private List<Drawable> drawables = new ArrayList<>();

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
	
	public void mouseMoved(double eventX, double eventY) {
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
	
	public void mouseClicked(double x, double y) {
		
	}

	@Override
	public List<Drawable> getDrawables() {
		return drawables;
	}

	@Override
	public void prepareLayer() {
		getGraphicsContext2D().drawImage(ResourcePool.getInstance().getBackground(), 0, 0);
	}
}
