package de.phillip.models;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;

public class CanvasButton implements Drawable {
	
	private Image image;
	private Rectangle2D rectangle;
	private boolean isActive;
	private boolean isVisible;
	

	public CanvasButton(Image image, int posX, int posY, int width, int height) {
		this.image = image;
		rectangle = new Rectangle2D(posX, posY, width, height);
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.save();
		if (isVisible) {
			if (isActive) {
				gc.setGlobalAlpha(1);
				Bloom bloom = new Bloom();
				bloom.setThreshold(0.1);
				gc.setEffect(bloom);
				gc.drawImage(image, rectangle.getMinX(), rectangle.getMinY());
			} else {
				gc.setEffect(null);
				gc.drawImage(image, rectangle.getMinX(), rectangle.getMinY());
			}
		} else {
			gc.setGlobalAlpha(0.5);
			gc.setEffect(null);
			gc.drawImage(image, rectangle.getMinX(), rectangle.getMinY());
		}
		gc.restore();
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public boolean contains(Point2D point) {
		return rectangle.contains(point);
	}
	
	public void setPosition(int posX, int posY) {
		rectangle = new Rectangle2D(posX, posY, rectangle.getWidth(), rectangle.getHeight());
	}
	
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

}
