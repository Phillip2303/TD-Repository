package de.phillip.models;

import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.ResourcePool;
import de.phillip.models.transferObjects.TurretTO;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;

public class TurretTile extends Tile {
	
	private boolean isActive;
	private boolean isVisible;
	private Image turretSprite;
	private TurretTO turretTO;

	public TurretTile(int posX, int posY, int iD, int size) {
		super(posX, posY, iD, size);
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.save();
		Bloom bloom = new Bloom(0.1);
			switch (getID()) {
			case 0:
				if (isActive) {
					if (isVisible) {
						gc.setGlobalAlpha(1.0);
						gc.setEffect(bloom);
						gc.drawImage(turretSprite, 0, 0, getWidth(), getHeight(), 
								getPosX(), getPosY(), getWidth(), 
								getHeight());
					} else {
						gc.setGlobalAlpha(0.5);
						gc.setEffect(null);
						gc.drawImage(turretSprite, 0, 0, getWidth(), getHeight(), 
								getPosX(), getPosY(), getWidth(), 
								getHeight());
					}
				} else {
					gc.setGlobalAlpha(1.0);
					gc.setEffect(null);
					gc.drawImage(turretSprite, 0, 0, getWidth(), getHeight(), 
							getPosX(), getPosY(), getWidth(), 
							getHeight());
				}
				break;
			case 1:
				if (isActive && isVisible) {
					gc.setEffect(bloom);
					gc.drawImage(turretSprite, 0, 46, getWidth(), getHeight(), 
							getPosX(), getPosY(), getWidth(), 
							getHeight());
				} else {
					gc.setEffect(null);
					gc.drawImage(turretSprite, 0, 46, getWidth(), getHeight(), 
							getPosX(), getPosY(), getWidth(), 
							getHeight());
				}
				break;
			case 2:
				if (isActive && isVisible) {
					gc.setEffect(bloom);
					gc.drawImage(turretSprite, 0, 92, getWidth(), getHeight(), 
							getPosX(), getPosY(), getWidth(), 
							getHeight());
				} else {
					gc.setEffect(null);
					gc.drawImage(turretSprite, 0, 92, getWidth(), getHeight(), 
							getPosX(), getPosY(), getWidth(), 
							getHeight());
				}
				break;
			case 3:
				if (isActive && isVisible) {
					gc.setEffect(bloom);
					gc.drawImage(turretSprite, 0, 138, getWidth(), getHeight(), 
						getPosX(), getPosY(), getWidth(), 
						getHeight());
				} else {
					gc.setEffect(null);
					gc.drawImage(turretSprite, 0, 138, getWidth(), getHeight(), 
						getPosX(), getPosY(), getWidth(), 
						getHeight());
				}
				break;
			case 4:
				if (isActive && isVisible) {
					gc.setEffect(bloom);
					gc.drawImage(turretSprite, 0, 184, getWidth(), getHeight(), 
							getPosX(), getPosY(), getWidth(), 
							getHeight());
				} else {
					gc.setEffect(null);
					gc.drawImage(turretSprite, 0, 184, getWidth(), getHeight(), 
							getPosX(), getPosY(), getWidth(), 
							getHeight());
				}
				break;
			case 5:
				if (isActive && isVisible) {
					gc.setEffect(bloom);
					gc.drawImage(turretSprite, 0, 230, getWidth(), getHeight(), 
							getPosX(), getPosY(), getWidth(), 
							getHeight());
				} else {
					gc.setEffect(null);
					gc.drawImage(turretSprite, 0, 230, getWidth(), getHeight(), 
							getPosX(), getPosY(), getWidth(), 
							getHeight());
				}
				break;
			case 6:
				if (isActive && isVisible) {
					gc.setEffect(bloom);
					gc.drawImage(turretSprite, 0, 276, getWidth(), getHeight(), 
							getPosX(), getPosY(), getWidth(), 
							getHeight());
				} else {
					gc.setEffect(null);
					gc.drawImage(turretSprite, 0, 276, getWidth(), getHeight(), 
							getPosX(), getPosY(), getWidth(), 
							getHeight());
				}
				break;
			default:
				gc.setEffect(null);
				gc.drawImage(ResourcePool.getInstance().getTurretBase(), getPosX(), getPosY(), getWidth(), getHeight());
				break;
			}
		gc.restore();
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void setSprite(Image turretSprite) {
		 this.turretSprite = turretSprite;
	 }
	
	public void setTurretTO(TurretTO turretTO) {
		this.turretTO = turretTO;
	}

	public int getCost() {
		return turretTO.getCost();
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}
