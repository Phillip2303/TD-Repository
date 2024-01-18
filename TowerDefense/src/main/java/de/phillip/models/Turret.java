package de.phillip.models;

import de.phillip.gameUtils.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Turret extends Actor {
	
	private Image turretBaseSprite;
	private Image turretCannonSprite;
	private int ID;

	public Turret(double width, double height, Image turretBaseSprite, Image turretCannonSprite, int ID) {
		super(width, height);
		this.turretBaseSprite = turretBaseSprite;
		this.turretCannonSprite = turretCannonSprite;
		this.ID = ID;
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.drawImage(turretBaseSprite, 0, ID * Constants.TILESIZE, turretBaseSprite.getWidth(), 
				turretBaseSprite.getHeight(), getDrawPosition().getX(), getDrawPosition().getY(), getWidth(), 
				getHeight());
	}

	@Override
	public void debugOut() {

	}

}
