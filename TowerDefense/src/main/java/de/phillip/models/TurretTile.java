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
				drawImage(bloom, gc, 0);
				break;
			case 1:
				drawImage(bloom, gc, 46);
				break;
			case 2:
				drawImage(bloom, gc, 92);
				break;
			case 3:
				drawImage(bloom, gc, 138);
				break;
			case 4:
				drawImage(bloom, gc, 184);
				break;
			case 5:
				drawImage(bloom, gc, 230);
				break;
			case 6:
				drawImage(bloom, gc, 276);
				break;
			default:
				gc.setEffect(null);
				gc.drawImage(ResourcePool.getInstance().getTurretBase(), getPosX(), getPosY(), getWidth(), getHeight());
				break;
			}
		gc.restore();
	}
	
	public void drawImage(Bloom bloom, GraphicsContext gc, double sy) {
		gc.save();
		if (isVisible) {
			gc.setGlobalAlpha(1);
			if (isActive) {
				gc.setEffect(bloom);
			} else {
				gc.setEffect(null);
			}
			gc.drawImage(turretSprite, 0, sy, getWidth(), getHeight(), 
					getPosX(), getPosY(), getWidth(), 
					getHeight());
		} else {
			gc.setGlobalAlpha(0.5);
			gc.setEffect(null);
			gc.drawImage(turretSprite, 0, sy, getWidth(), getHeight(), 
					getPosX(), getPosY(), getWidth(), 
					getHeight());
			gc.setGlobalAlpha(1);
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
