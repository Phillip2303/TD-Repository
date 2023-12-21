package de.phillip.models;

import de.phillip.gameUtils.Constants;
import de.phillip.models.transferObjects.WaveBlockTO;
import javafx.scene.canvas.GraphicsContext;

public class Enemy extends Actor {
	
	private int spriteIndex;
	private int tic;
	private static final int ANIMATION = 10; 
	private boolean reachedEnd;
	private boolean isOff;
	private WaveBlockTO waveBlock;
	private int index;

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
		debugOut();
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
	
	public String getType() {
		return waveBlock.getType();
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public void debugOut() {
		if (isDebug()) {
			System.out.println("Enemy Debug -> Type: " + getType() + "; Index: " + getIndex() + "; Direction: " + getRotation() + "; Center: " + getCenter().getX() + " | "+ getCenter().getY());
		}
	}
}
