package de.phillip.controllers;

import java.io.IOException;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.ResourcePool;
import de.phillip.models.Turret;
import de.phillip.models.transferObjects.TurretTO;
import javafx.scene.image.Image;

public class TurretController {
	
	private int level;
	private TurretTO turret;

	public TurretController() {
		
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
		loadLevelResource();
		createTurretSprites();
	}
	
	private void loadLevelResource() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			turret = objectMapper.readValue(ResourcePool.getInstance().loadLevelResource(level, "TURRET"), TurretTO.class);
		} catch (StreamReadException e) {
			e.printStackTrace();
		} catch (DatabindException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createTurretSprites() {
		Image turretSprite = ResourcePool.getInstance().getTurretSprite(turret.getTurretSpritePath());
		turret.setTurretSprite(turretSprite);
		Image cannonSprite = ResourcePool.getInstance().getTurretSprite(turret.getCannonSpritePath());
		turret.setCannonSprite(cannonSprite);
		Image bulletImage = ResourcePool.getInstance().getBullet(level);
		turret.setBulletImage(bulletImage);
	}
	
	public Turret createTurret(int ID) {
		return new Turret(Constants.TILESIZE, Constants.TILESIZE, ID, turret);
	}

}
