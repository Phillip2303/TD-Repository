package de.phillip.models;

import de.phillip.controls.Constants;
import de.phillip.controls.ResourcePool;
import de.phillip.models.transferObjects.WaveBlockTO;
import javafx.animation.FadeTransition;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Enemy extends Actor {
	
	private int spriteIndex;
	private int tic;
	private static final int ANIMATION = 10; 
	private boolean reachedEnd;
	private boolean isOff;
	private WaveBlockTO waveBlock;

	public Enemy(double width, double height, WaveBlockTO waveBlock) {
		super(width, height);
		this.waveBlock = waveBlock;
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		if (reachedEnd) {
			gc.setGlobalAlpha(0.5);
		} else {
			gc.setGlobalAlpha(1.0);
		}
		gc.drawImage(waveBlock.getSprite(), 0, spriteIndex*Constants.TILESIZE, getWidth(), 
				getHeight(), getDrawPosition().getX(), getDrawPosition().getY(), getWidth(), getHeight());
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
	
	public void setReachedEnd() {
		reachedEnd = true;
	}
	
	public boolean hasReachedEnd() {
		return reachedEnd;
	}
	
	public void leavePath(double speed) {
		setCurrentThrustVector(speed);
		update();
		if (getDrawPosition().getX() > Constants.TILESIZE * Constants.TERRAINLAYER_WIDTH + Constants.TILESIZE) {
			isOff = true;
		}
	}
	
	public boolean getIsOff() {
		return isOff;
	}
}
