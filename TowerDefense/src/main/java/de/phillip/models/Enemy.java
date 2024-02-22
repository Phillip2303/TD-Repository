package de.phillip.models;

import de.phillip.gameUtils.Constants;
import de.phillip.models.transferObjects.WaveBlockTO;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class Enemy extends Actor {
	
	private int spriteIndex;
	private int tic;
	private static final int ANIMATION = 10; 
	private boolean reachedEnd;
	private boolean isOff;
	private WaveBlockTO waveBlock;
	private int index;
	private int health;
	private boolean alive = true;
	

	public Enemy(double width, double height, WaveBlockTO waveBlock) {
		super(width, height);
		this.waveBlock = waveBlock;
		health = this.waveBlock.getHealth();
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.save();
		double width = (getWidth() / waveBlock.getHealth()) * health;
		if (health > waveBlock.getHealth() / 4) {
			gc.setFill(Color.GREEN);
		} else {
			gc.setFill(Color.RED);
		}
		gc.fillRect(getDrawPosition().getX(), getDrawPosition().getY(), width, 6);
		System.out.println(waveBlock.getHealth());
		transformContext(gc);
		if (reachedEnd) {
			gc.setGlobalAlpha(0.5);
		} else {
			gc.setGlobalAlpha(1.0);
		}
		gc.drawImage(waveBlock.getSprite(), 0, spriteIndex*Constants.TILESIZE, getWidth(), 
				getHeight(), getDrawPosition().getX(), getDrawPosition().getY(), getWidth(), getHeight());
		gc.restore();
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
	
	public boolean isAlive() {
		return alive;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public void reduceHealth() {
		health -= 1;
		if (health == 0) {
			alive = false;
		}
	}
	
	public void debugOut() {
		if (isDebug()) {
			System.out.println("Enemy Debug -> Type: " + getType() + "; Index: " + getIndex() + "; Direction: " + getRotation() + "; Center: " + getCenter().getX() + " | "+ getCenter().getY());
		}
	}
	
	
}
