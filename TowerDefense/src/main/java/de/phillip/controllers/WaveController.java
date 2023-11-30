package de.phillip.controllers;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.phillip.controls.Constants;
import de.phillip.controls.ResourcePool;
import de.phillip.models.Enemy;
import de.phillip.models.transferObjects.WaveBlockTO;
import de.phillip.models.transferObjects.WaveTO;
import javafx.scene.image.Image;

public class WaveController {
	
	private int level;
	private WaveBlockTO waveBlock;
	private WaveTO wave;

	public WaveController() {

	}
	
	public void setLevel(int level) {
		this.level = level;
		loadLevelResource();
		createBlockEnemies();
	}
	
	private void createBlockEnemies() {
		wave.getWaveBlocks().forEach(b -> {
			Image image = ResourcePool.getInstance().getEnemySprite(b.getImagePath());
			b.setSprite(image);
		});
		WaveBlockTO firstWaveBlock = wave.getWaveBlocks().get(0);
		for (int x = 0; x < firstWaveBlock.getAmount(); x++) {
			Enemy enemy = new Enemy(Constants.TILESIZE, Constants.TILESIZE);
		}
	}
	
	private void loadLevelResource() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			wave = objectMapper.readValue(ResourcePool.getInstance().loadLevelResource(level), WaveTO.class);
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

}
