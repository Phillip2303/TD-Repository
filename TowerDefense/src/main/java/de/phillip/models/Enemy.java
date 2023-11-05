package de.phillip.models;

import de.phillip.controls.ResourcePool;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Enemy extends Actor {
	
	int enemyType;

	public Enemy(double width, double height) {
		super(width, height);
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.drawImage(ResourcePool.getInstance().getEnemy(1), 0, 0, getWidth(), getHeight(), getDrawPosition().getX(), getDrawPosition().getY(), getWidth(), getHeight());
	}

}
