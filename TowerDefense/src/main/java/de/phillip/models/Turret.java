package de.phillip.models;

import de.phillip.events.FXEventBus;
import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.Transformer;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Turret extends Actor implements EventHandler<MouseEvent>{
	
	private Image turretBaseSprite;
	private Image turretCannonSprite;
	private int ID;
	private boolean isSelected;
	private boolean isNew = true;
	private boolean isDeleted;
	private int range = 5;
	private Color rangeColour = new Color(0.49803922f, 1.0f, 0.83137256f, 0.25);

	public Turret(double width, double height, Image turretBaseSprite, Image turretCannonSprite, int ID) {
		super(width, height);
		FXEventBus.getInstance().addEventHandler(MouseEvent.MOUSE_CLICKED, this);
		this.turretBaseSprite = turretBaseSprite;
		this.turretCannonSprite = turretCannonSprite;
		this.ID = ID;
	}
	
	public void unregisterHandler() {
		FXEventBus.getInstance().removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.save();
		if (isSelected) {
			//gc.rect(0, ID * Constants.TILESIZE, Constants.TILESIZE, Constants.TILESIZE);
			gc.setStroke(Color.WHITE);
	        gc.setLineWidth(1);
	        gc.strokeRect(getDrawPosition().getX(), getDrawPosition().getY(), Constants.TILESIZE, Constants.TILESIZE);
	        int turretRange = range * Constants.TILESIZE;
	        gc.setFill(rangeColour);
	        gc.fillOval(getCenter().getX() - turretRange / 2, getCenter().getY() - turretRange / 2, turretRange, turretRange);
		}
		gc.drawImage(turretBaseSprite, 0, ID * Constants.TILESIZE, Constants.TILESIZE, 
				Constants.TILESIZE, getDrawPosition().getX(), getDrawPosition().getY(), getWidth(), 
				getHeight());
		transformContext(gc);
		gc.drawImage(turretCannonSprite, 0, ID * Constants.TILESIZE, Constants.TILESIZE, 
				Constants.TILESIZE, getDrawPosition().getX(), getDrawPosition().getY(), getWidth(), 
				getHeight());
		gc.restore();
	}

	@Override
	public void debugOut() {

	}
	
	public void select(boolean value) {
		if (isNew) {
			isNew = false;
		} else {
			isSelected = value;
		}
	}
	
	public boolean isSelected() {
		return isSelected;
	}

	@Override
	public void handle(MouseEvent mouseEvent) {
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
	}

	private void mouseRightClicked(double eventX, double eventY) {
		if (isSelected) {
			isDeleted = true;
		}
	}

	private void mouseLeftClicked(double eventX, double eventY) {
		Point2D selectedTileCoor = Transformer.transformPixelsCoordinatesToTile(eventX, eventY);
		double x = selectedTileCoor.getX() * Constants.TILESIZE;
		double y = selectedTileCoor.getY() * Constants.TILESIZE;
		if (getDrawPosition().getX() == x)  {
			if (getDrawPosition().getY() == y) {
				select(!isSelected);
				return;
			}
		}
		select(false);
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}

}
