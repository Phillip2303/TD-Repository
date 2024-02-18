package de.phillip.models;

import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.Transformer;
import de.phillip.models.transferObjects.TurretTO;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bullet extends Actor {
	
	private Image image;
	private int speedLevel;
	private Point2D startPosition;
	private int range;
	private boolean isInRange = true;

	public Bullet(Image image, int range) {
		super(image.getWidth(), image.getHeight());
		this.image = image;
		this.range = range;
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.save();
		transformContext(gc);
		gc.drawImage(image, getDrawPosition().getX(), getDrawPosition().getY(),
				image.getWidth(), image.getHeight());
		gc.restore();
	}

	@Override
	public void debugOut() {

	}

	public int getSpeedLevel() {
		return speedLevel;
	}

	public boolean isInRange() {
		return isInRange;
	}

	public void setSpeedLevel(int speedLevel) {
		this.speedLevel = speedLevel;
	}
	
	public void setCurrentThrustVector(double value) {
		Point2D currentThrustVector = calculateNewThrust(value, Math.toRadians(-getRotation()));
		setCurrentThrustVector(currentThrustVector);
	}
	
	public void setStartPosition(double x, double y) {
		setDrawPosition(x, y);
		startPosition = new Point2D(x, y);
	}
	
	public void update() {
		checkRange();
		if (isInRange) {
			super.update();
		}
	}
	
	private void checkRange() {
		if (Transformer.getDistance(startPosition.getX(), startPosition.getY(), getCenter().getX(),
			getCenter().getY()) <= range * Constants.TILESIZE) {
			isInRange = true;
		} else {
			isInRange = false;
		}
		System.out.println(isInRange);
	}
}
