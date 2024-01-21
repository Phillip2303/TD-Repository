package de.phillip.models;

import de.phillip.gameUtils.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Turret extends Actor {
	
	private Image turretBaseSprite;
	private Image turretCannonSprite;
	private int ID;
	private boolean isSelected;

	public Turret(double width, double height, Image turretBaseSprite, Image turretCannonSprite, int ID) {
		super(width, height);
		this.turretBaseSprite = turretBaseSprite;
		this.turretCannonSprite = turretCannonSprite;
		this.ID = ID;
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.save();
		gc.drawImage(turretBaseSprite, 0, ID * Constants.TILESIZE, Constants.TILESIZE, 
				Constants.TILESIZE, getDrawPosition().getX(), getDrawPosition().getY(), getWidth(), 
				getHeight());
		transformContext(gc);
		gc.drawImage(turretCannonSprite, 0, ID * Constants.TILESIZE, Constants.TILESIZE, 
				Constants.TILESIZE, getDrawPosition().getX(), getDrawPosition().getY(), getWidth(), 
				getHeight());
		if (isSelected) {
			gc.rect(0, ID * Constants.TILESIZE, Constants.TILESIZE, Constants.TILESIZE);
			gc.setStroke(Color.RED);
	        gc.setLineWidth(5);
	        gc.stroke();
		}
		gc.restore();
	}

	@Override
	public void debugOut() {

	}
	
	public void select(boolean value) {
		isSelected = value;
	}
	
	public boolean isSelected() {
		return isSelected;
	}

}
