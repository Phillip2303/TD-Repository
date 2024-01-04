package de.phillip.models;

import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.ResourcePool;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TurretTile extends Tile {
	
	private boolean isActive;
	private Image turretSprite;

	public TurretTile(int posX, int posY, int iD, int size) {
		super(posX, posY, iD, size);
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		if (isActive) {
			switch (getID()) {
			case 0:
				gc.drawImage(turretSprite, 0, 0, getWidth(), getHeight(), 
						getPosX(), getPosY(), getWidth(), 
						getHeight());
				break;
			case 1:
				gc.drawImage(turretSprite, 0, 40, getWidth(), getHeight(), 
						getPosX(), getPosY(), getWidth(), 
						getHeight());
				break;
			case 2:
				gc.drawImage(turretSprite, 0, 80, getWidth(), getHeight(), 
						getPosX(), getPosY(), getWidth(), 
						getHeight());
				break;
			default:
				gc.drawImage(ResourcePool.getInstance().getTurretBase(), getPosX(), getPosY(), getWidth(), getHeight());
				break;
			}
		} else {
			gc.drawImage(ResourcePool.getInstance().getTurretBase(), getPosX(), getPosY(), getWidth(), getHeight());
		}
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public void setSprite(Image turretSprite) {
		 this.turretSprite = turretSprite;
	 }
}
