package de.phillip.ui;

import de.phillip.gameUtils.ResourcePool;
import javafx.scene.canvas.GraphicsContext;

public class TurretTile extends Tile {

	public TurretTile(int posX, int posY, int iD, int size) {
		super(posX, posY, iD, size);
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.drawImage(ResourcePool.getInstance().getTurretBase(), getPosX(), getPosY(), getWidth(), getHeight());
	}
}
