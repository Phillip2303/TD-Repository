package de.phillip.models.transferObjects;

import java.util.List;

public class WaveTO {
	
	private List <WaveBlockTO> waveBlocks;
	private int waveCount;
	private int blockDelay;
	private float enemyDelay;
	private int speed;
	
	public WaveTO() {

	}

	public List<WaveBlockTO> getWaveBlocks() {
		return waveBlocks;
	}

	public void setWaveBlocks(List<WaveBlockTO> waveBlocks) {
		this.waveBlocks = waveBlocks;
	}

	public int getWaveCount() {
		return waveCount;
	}

	public void setWaveCount(int waveCount) {
		this.waveCount = waveCount;
	}
	
	public int getBlockDelay() {
		return blockDelay;
	}

	public void setBlockDelay(int blockDelay) {
		this.blockDelay = blockDelay;
	}

	public float getEnemyDelay() {
		return enemyDelay;
	}

	public void setEnemyDelay(float enemyDelay) {
		this.enemyDelay = enemyDelay;
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
