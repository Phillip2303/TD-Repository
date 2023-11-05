package de.phillip.ui;

import de.phillip.controls.Constants;
import de.phillip.controls.ResourcePool;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TerrainTile extends Tile {

	public TerrainTile(int posX, int posY, int iD, int size) {
		super(posX, posY, iD, size);
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.drawImage(ResourcePool.getInstance().getTerrain(), 0, getID()*getHeight(), getWidth(), getHeight(), getPosX(), getPosY(), getWidth(), getHeight());
	}

}
