package de.phillip.models;

import de.phillip.controls.Constants;
import de.phillip.controls.ResourcePool;
import javafx.scene.canvas.GraphicsContext;

public class Enemy extends Actor {
	
	private int spriteIndex;
	private int tic;
	private static final int ANIMATION = 10; 

	public Enemy(double width, double height) {
		super(width, height);
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.drawImage(ResourcePool.getInstance().getEnemy(1), 0, spriteIndex*Constants.TILESIZE, getWidth(), getHeight(), getDrawPosition().getX(), getDrawPosition().getY(), getWidth(), getHeight());
	}
	
	private void updateSpriteIndex() {
		tic++;
		if (tic > ANIMATION) {
			spriteIndex++;
			tic = 0;
		}
		if (spriteIndex > 5) {
			spriteIndex = 0;
		}
	}
	
	public void update() {
		super.update();
		updateSpriteIndex();
	}
}
