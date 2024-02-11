package de.phillip.models;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bullet extends Actor {
	
	Image image;
	private int speedLevel;

	public Bullet(Image image) {
		super(image.getWidth(), image.getHeight());
		this.image = image;
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.drawImage(image, getDrawPosition().getX(), getDrawPosition().getY(),
				image.getWidth(), image.getHeight());
	}

	@Override
	public void debugOut() {
		// TODO Auto-generated method stub

	}

	public int getSpeedLevel() {
		return speedLevel;
	}

	public void setSpeedLevel(int speedLevel) {
		this.speedLevel = speedLevel;
	}
	
	public void setCurrentThrustVector(double value) {
		Point2D currentThrustVector = calculateNewThrust(value, Math.toRadians(getRotation()));
		setCurrentThrustVector(currentThrustVector);
	}
}
