package de.phillip.controllers;

import java.io.IOException;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.ResourcePool;
import de.phillip.models.Enemy;
import de.phillip.models.transferObjects.WaveBlockTO;
import de.phillip.models.transferObjects.WaveTO;
import javafx.scene.image.Image;

public class WaveController {
	
	private int level;
	private WaveTO wave;
	private int blockIndex, enemyIndex;
	private float enemyDelay;
	private float blockDelay;

	public WaveController() {

	}
	
	public void setLevel(int level) {
		this.level = level;
		blockIndex = 0;
		enemyIndex = 0;
		loadLevelResource();
		createBlockSprites();
	}
	
	private void createBlockSprites() {
		wave.getWaveBlocks().forEach(b -> {
			Image image = ResourcePool.getInstance().getEnemySprite(b.getImagePath());
			b.setSprite(image);
		});
	}
	
	private void loadLevelResource() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			wave = objectMapper.readValue(ResourcePool.getInstance().loadLevelResource(level, "ENEMY"), WaveTO.class);
		} catch (StreamReadException e) {
			e.printStackTrace();
		} catch (DatabindException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getLevel() {
		return level;
	}
	
	public boolean hasMoreEnemies() {
		for (int i = blockIndex; i < wave.getWaveCount(); i++) {
			WaveBlockTO waveBlock = wave.getWaveBlocks().get(i);
			if (enemyIndex < waveBlock.getAmount()) {
				return true;
			} 
		}
		return false;
	}
	
	public Enemy getEnemy(float secondsSinceLastFrame) {
		enemyDelay += secondsSinceLastFrame;
		blockDelay += secondsSinceLastFrame;
		if (blockDelay > wave.getBlockDelay()) {
			if (enemyDelay > wave.getEnemyDelay()) {
				if (wave.getWaveBlocks().get(blockIndex).getAmount() > 0) {
					Enemy enemy = new Enemy(Constants.TILESIZE, Constants.TILESIZE, wave.getWaveBlocks().get(blockIndex));
					enemy.setDrawPosition(6*Constants.TILESIZE, 0*Constants.TILESIZE);
					enemy.setIndex(enemyIndex);
					enemyIndex++;
					if (enemyIndex >= wave.getWaveBlocks().get(blockIndex).getAmount()) {
						enemyIndex = 0;
						blockIndex++;
						blockDelay = 0;
					}
					enemyDelay = 0;
					return enemy;
				} else {
					enemyIndex = 0;
					blockIndex++;
					blockDelay = 0;
				}
			}
		}
		
		return null;
	}

}
