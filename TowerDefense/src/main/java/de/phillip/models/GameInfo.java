package de.phillip.models;

import de.phillip.events.FXEventBus;
import de.phillip.events.GameEvent;
import de.phillip.gameUtils.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameInfo implements Drawable {
	
	private static GameInfo gameInfo;
	private int money = 500;
	private int health = 100;
	private int level;
	private int currentWave = 1;
	private int waveCount;
	private boolean drawWaveCount;

	private GameInfo() {
		
	}
	
	public static GameInfo getInstance() {
		if (gameInfo == null) {
			gameInfo = new GameInfo();
		}
		return gameInfo;
	}
	
	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.setFill(Color.AQUAMARINE);
		gc.setFont(new Font(20));
		gc.fillText("Money: " + money + "\nLevel: " + level, 19 * Constants.TILESIZE - 8, 3.5 * Constants.TILESIZE);
		gc.fillText("Health: " + health, 19 * Constants.TILESIZE, 18.5 * Constants.TILESIZE);
		 //startWaveButton = new CanvasButton(startWave, 19 * Constants.TILESIZE - 8, 3 * Constants.TILESIZE, 120, 20);
		if (drawWaveCount) {
			gc.fillText("Wave: " + currentWave + " / " + waveCount, 19 * Constants.TILESIZE - 8 , 6 * Constants.TILESIZE);
		}
	}

	public int getMoney() {
		return money;
	}

	public void increaseMoney(int money) {
		this.money += money;
	}
	
	public void decreaseMoney(int money) {
		this.money -= money;
	}

	public int getHealth() {
		return health;
	}
	
	public void resetHealth() {
		health = 100;
	}

	public void decreaseHealth(int damage) {
		health -= damage;
		if (health <= 0) {
			health = 0;
			FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.TD_LOST, null));
		}
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
		switch (level) {
		case 1:
			money = 500;
			break;
		case 2:
			money += 200;
			break;
		case 3:
			money += 400;
			break;
		default:
			break;
		}
	}
	
	public int getCurrentWave() {
		return currentWave;
	}

	public void setCurrentWave(int currentWave) {
		this.currentWave = currentWave;
	}

	public int getWaveCount() {
		return waveCount;
	}

	public void setWaveCount(int waveCount) {
		this.waveCount = waveCount;
	}

	public void setDrawWaveCount(boolean drawWaveCount) {
		this.drawWaveCount = drawWaveCount;
	}
}
